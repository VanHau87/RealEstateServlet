package com.webservlet.service;

import java.util.List;
import java.util.Map;

import com.webservlet.api.output.BuildingTypeOutput;
import com.webservlet.builder.BuildingSearchBuilder;
import com.webservlet.dto.BuildingDTO;

public interface BuildingService {
	List<BuildingDTO> findByBuilder(BuildingSearchBuilder builder);
	BuildingDTO saveBuilding(BuildingDTO dto);
	List<BuildingTypeOutput> getBuildingType();
	Map<String, String> getMapBuildingType();
}
