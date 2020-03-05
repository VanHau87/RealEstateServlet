package com.webservlet.repository;

import java.util.List;
import java.util.Map;

import com.webservlet.builder.BuildingSearchBuilder;
import com.webservlet.entity.BuildingEntity;

public interface BuildingRepository extends JpaRepository<BuildingEntity> {
	List<BuildingEntity> findBuildingByBuilder(Map<String, Object> params, BuildingSearchBuilder builder);
}
