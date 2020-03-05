package com.webservlet.repository.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringUtils;

import com.webservlet.builder.BuildingSearchBuilder;
import com.webservlet.entity.BuildingEntity;
import com.webservlet.repository.BuildingRepository;

public class BuildingRepositoryImpl extends JpaRepositoryImpl<BuildingEntity> implements BuildingRepository {

	@Override
	public List<BuildingEntity> findBuildingByBuilder(Map<String, Object> params, BuildingSearchBuilder builder) {
		StringBuilder sqlBuilder = new StringBuilder("SELECT * FROM building A");
		if (builder.getStaffId() != null && builder.getStaffId() > 0) {
			sqlBuilder.append(" INNER JOIN assignedbuilding AB ON A.buildingid = AB.buildingid");
		}
		sqlBuilder.append(" WHERE 1 = 1");
		sqlBuilder = super.createSQLFindAllCommon(sqlBuilder, params);
		sqlBuilder = createSQLSpecial(sqlBuilder, builder);
		return super.findAll(sqlBuilder.toString());
	}
/*
	private StringBuilder createSQLFindAllCommon(StringBuilder sqlBuilder, Map<String, Object> params) {
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
					sqlBuilder.append(" AND b.").append(keys[i]);
					sqlBuilder.append(" LIKE '%").append(values[i]).append("%'");
				} else {
					sqlBuilder.append(" AND b.").append(keys[i]);
					sqlBuilder.append(" = ").append(values[i]);
				}
			}
		}
		return sqlBuilder;
	}
*/

	private StringBuilder createSQLSpecial(StringBuilder sqlBuilder, BuildingSearchBuilder builder) {
		if (StringUtils.isNotBlank(builder.getRentAreaFrom()) || StringUtils.isNotBlank(builder.getRentAreaTo())) {
			sqlBuilder.append(" AND EXISTS (SELECT * FROM rentarea ra WHERE (A.buildingid = ra.buildingid");
			if (StringUtils.isNotBlank(builder.getRentAreaFrom())) {
				sqlBuilder.append(" AND ra.value >= ").append(builder.getRentAreaFrom());
			}
			if (StringUtils.isNotBlank(builder.getRentAreaTo())) {
				sqlBuilder.append(" AND ra.value <= ").append(builder.getRentAreaTo());
			}
			sqlBuilder.append("))");
		}
		String[] types = builder.getTypes();
		if (types.length > 0) {
			sqlBuilder.append(" AND (");
			/*
			for (String type : types) {
				if (type.equals(types[0])) {
					sqlBuilder.append("A.type LIKE '%").append(type).append("%'");
				} else {
					sqlBuilder.append(" OR A.type LIKE '%").append(type).append("%'");
				}
			}
			*/
			String sqlType = Arrays.stream(types).map(item -> "A.type LIKE '%"+item+"%'")
					.collect(Collectors.joining(" OR "));
			sqlBuilder.append(sqlType);
			sqlBuilder.append(")");
		}
		if (StringUtils.isNotBlank(builder.getRentCostFrom())) {
			sqlBuilder.append(" AND A.rentcost >= ").append(builder.getRentCostFrom());
		}
		if (StringUtils.isNotBlank(builder.getRentCostTo())) {
			sqlBuilder.append(" AND A.rentcost <= ").append(builder.getRentCostTo());
		}
		if (builder.getStaffId() != null && builder.getStaffId() > 0) {
			sqlBuilder.append(" AND AB.staffid =").append(builder.getStaffId());
		}
		return sqlBuilder;
	}
}
