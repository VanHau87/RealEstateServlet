package com.webservlet.mapper;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

import com.webservlet.annotation.Column;
import com.webservlet.annotation.Entity;

public class ResultSetMapper<T> {
	/* ResultSetMetaData includes information about data
	 * ResultSet includes values of data
	 * Logic:
	 * 	- check Annotation @Entity of the entity
	 * 	- get properties of zClass
	 * 	- get name and value of columns in ResultSet
	 * 	- loop through properties
	 * 	- compare name of column in RS to name of property of zClass
	 * 		- if they are the same, set value to property.
	 * 		- break to exist 
	 * */
	public List<T> mapRow(ResultSet rs, Class<T> zClass){
		List<T> results = new ArrayList<>();
		try {
			if (zClass.isAnnotationPresent(Entity.class)) {
				ResultSetMetaData resultSetMetaData = rs.getMetaData();
				//get properties of class
				Field[] fields = zClass.getDeclaredFields();
				ConvertUtils.register(new SqlTimestampConverter(null), Timestamp.class);    
				while (rs.next()) {
					T object = zClass.newInstance();
					Integer numberOfColumn = resultSetMetaData.getColumnCount();
					for (int i = 1; i <= numberOfColumn; i++) {
						//get name of column in ResultSet
						String columnName = resultSetMetaData.getColumnName(i);
						Object columnValue = rs.getObject(i);
						for (Field field : fields) {
							if (field.isAnnotationPresent(Column.class)) {
								String propertyOfObject = field.getName();
								if (columnName.equalsIgnoreCase(propertyOfObject)) {
									BeanUtils.setProperty(object, propertyOfObject, columnValue);
									break;
								}
							}
						}
						Class<?> parentClass = zClass.getSuperclass();
						Field[] fieldsOfParent = parentClass.getDeclaredFields();
						while (parentClass != null && parentClass != Object.class) {
							for (Field field : fieldsOfParent) {
								if (field.isAnnotationPresent(Column.class)) {
									String propertyOfObject = field.getName();
									if (columnName.equalsIgnoreCase(propertyOfObject)) {
										BeanUtils.setProperty(object, propertyOfObject, columnValue);
										break;
									}
								}
							}
							parentClass = parentClass.getSuperclass();
						}
					}
					results.add(object);
				}
			}
		} catch (SQLException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
			return new ArrayList<>();
		}
		return results;
	}
}
