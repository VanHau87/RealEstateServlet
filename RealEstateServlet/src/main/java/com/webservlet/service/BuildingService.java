package com.webservlet.service;

import java.util.List;

import com.webservlet.builder.BuildingSearchBuilder;
import com.webservlet.dto.BuildingDTO;

public interface BuildingService {
	List<BuildingDTO> findByBuilder(BuildingSearchBuilder builder);
	Integer saveBuilding(BuildingDTO dto);
}
