package com.webservlet.repository.impl;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.webservlet.annotation.Column;
import com.webservlet.annotation.Table;
import com.webservlet.mapper.ResultSetMapper;
import com.webservlet.repository.EntityManagerFactory;
import com.webservlet.repository.JpaRepository;

public class JpaRepositoryImpl<T> implements JpaRepository<T> {
	
	private Class<T> zClass;
	
	@SuppressWarnings("unchecked")
	public JpaRepositoryImpl() {
		Type type = this.getClass().getGenericSuperclass();
		ParameterizedType parameterizedType = (ParameterizedType) type;
		zClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];
	}
	
	@Override
	public List<T> findAll(Map<String, Object> params, Object ...where) {
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		Connection connection = EntityManagerFactory.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String tableName = "";
		if (connection != null) {
			try {
				if (zClass.isAnnotationPresent(Table.class)) {
					tableName = zClass.getAnnotation(Table.class).name();
				}
				StringBuilder sql = new StringBuilder("SELECT * FROM ");
				sql.append(tableName).append(" A WHERE 1=1");
				StringBuilder sqlBuilder = createSQLFindAllCommon(sql, params);
				statement = connection.prepareStatement(sqlBuilder.toString());
				resultSet = statement.executeQuery();
				return resultSetMapper.mapRow(resultSet, zClass);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ArrayList<T>();
			} finally {
				try {
					if (resultSet != null) {
						resultSet.close();
					}
					if (statement != null) {
						statement.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
					return new ArrayList<T>();
				}
				
			}
		}
		return null;
	}

	protected StringBuilder createSQLFindAllCommon(StringBuilder sqlBuilder, Map<String, Object> params) {
		if (params != null && params.size() > 0) {
			String[] keys = new String[params.size()];
			Object[] values = new Object[params.size()];
			int index = 0;
			for (Map.Entry<String, Object> item : params.entrySet()) {
				keys[index] = item.getKey();
				values[index] = item.getValue();
				index++;
			}
			for (int i = 0; i < keys.length; i++) {
				if (values[i] instanceof String) {
					sqlBuilder.append(" AND A.").append(keys[i]);
					sqlBuilder.append(" LIKE '%").append(values[i]).append("%'");
				} else {
					sqlBuilder.append(" AND A.").append(keys[i]);
					sqlBuilder.append(" = ").append(values[i]);
				}
			}
		}
		return sqlBuilder;
	}

	@Override
	public void insert(String sql, Object... objects) {
		Connection connection = EntityManagerFactory.getConnection();
		PreparedStatement statement = null;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql);
			int index = 1;
			for (Object object : objects) {
				statement.setObject(index, object);
				index++;
			}
			statement.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		}	
	}

	@Override
	public Integer insert(Object object) {
		StringBuilder sql = createSQLInsert();
		Connection connection = EntityManagerFactory.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			connection.setAutoCommit(false);
			statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			//convert object => object class
			Class<?> aClass = object.getClass();
			int index = 1;
			Field[] fields = aClass.getDeclaredFields();
			for (Field field : fields) {
				field.setAccessible(true);
				statement.setObject(index, field.get(object));
				index++;
			}
			Class<?> parentClass = aClass.getSuperclass();
			int indexParent = fields.length + 1;
			Field[] fieldsOfParent = parentClass.getDeclaredFields();
			while (parentClass != null && parentClass != Object.class) {
				for (Field field : fieldsOfParent) {
					field.setAccessible(true);
					statement.setObject(indexParent, field.get(object));
					indexParent++;
				}
				parentClass = parentClass.getSuperclass();
			}
			Integer result = statement.executeUpdate();
			resultSet = statement.getGeneratedKeys();
			connection.commit();
			if (result > 0) {
				while (resultSet.next()) {
					Integer id = resultSet.getInt(1);
					return id;
				}
			}
		} catch (SQLException | IllegalArgumentException | IllegalAccessException e) {
			try {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if (resultSet != null) {
					resultSet.close();
				}
				if (statement != null) {
					statement.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private StringBuilder createSQLInsert() {
		StringBuilder sql = new StringBuilder("INSERT INTO ");
		String tableName = "";
		StringBuilder fields = new StringBuilder();
		StringBuilder params = new StringBuilder();
		if (zClass.isAnnotationPresent(Table.class)) {
			tableName = zClass.getAnnotation(Table.class).name();
		}
		Field[] fieldsOfClass = zClass.getDeclaredFields();
		for (Field field : fieldsOfClass) {
			String columnName = field.getAnnotation(Column.class).name();
			if (fields.length() > 0) {
				fields.append(",");
				params.append(",");
			}
			fields.append(columnName);
			params.append("?");
		}
		sql.append(tableName);
		Class<?> parentClass = zClass.getSuperclass();
		while (parentClass != null && parentClass != Object.class) {
			Field[] fieldsOfParentClass = parentClass.getDeclaredFields();
			for (Field field : fieldsOfParentClass) {
				String columnName = field.getAnnotation(Column.class).name();
				if (fields.length() > 0) {
					fields.append(",");
					params.append(",");
				}
				fields.append(columnName);
				params.append("?");
			}
			parentClass = parentClass.getSuperclass();
		}
		sql.append("(").append(fields.toString()).append(")");
		sql.append(" VALUES(").append(params.toString()).append(")");
		return sql;
	}

	@Override
	public List<T> findAll(String sql) {
		ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
		Connection connection = EntityManagerFactory.getConnection();
		//PreparedStatement statement = null;
		Statement statement = null;
		ResultSet resultSet = null;
		if (connection != null) {
			try {
				//statement = connection.prepareStatement(sql.toString());
				statement = connection.createStatement();
				//resultSet = statement.executeQuery();
				resultSet = statement.executeQuery(sql);
				return resultSetMapper.mapRow(resultSet, zClass);
			} catch (SQLException e) {
				e.printStackTrace();
				return new ArrayList<T>();
			} finally {
				try {
					if (resultSet != null) {
						resultSet.close();
					}
					if (statement != null) {
						statement.close();
					}
					if (connection != null) {
						connection.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
					return new ArrayList<T>();
				}
				
			}
		}
		return null;
	}
}
