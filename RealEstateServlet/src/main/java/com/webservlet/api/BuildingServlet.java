package com.webservlet.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservlet.api.input.BuildingInput;
import com.webservlet.api.output.BuildingTypeOutput;
import com.webservlet.builder.BuildingSearchBuilder;
import com.webservlet.dto.BuildingDTO;
import com.webservlet.service.BuildingService;
import com.webservlet.service.impl.BuildingServiceImpl;
import com.webservlet.utils.FormUtil;
import com.webservlet.utils.HttpUtil;

@WebServlet("/building")
public class BuildingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ObjectMapper objectMapper = new ObjectMapper();
    BuildingService buildingService = new BuildingServiceImpl();
    public BuildingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if (action != null && action.equals("SEARCH_BUILDING")) {
			BuildingInput buildingInput = FormUtil.toModel(BuildingInput.class, request);
			BuildingSearchBuilder builder = new BuildingSearchBuilder.Builder()
					.setName(buildingInput.getName())
					.setDistrict(buildingInput.getDistrict())
					.setFloorArea(buildingInput.getFloorArea())
					.setNumberOfBasement(buildingInput.getNumberOfBasement())
					.setRentAreaFrom(buildingInput.getRentAreaFrom())
					.setRentAreaTo(buildingInput.getRentAreaTo())
					.setRentCostFrom(buildingInput.getRentCostFrom())
					.setRentCostTo(buildingInput.getRentCostTo())
					.setTypes(buildingInput.getTypes())
					.setStaffId(buildingInput.getStaffId())
					.build();
			List<BuildingDTO> results = buildingService.findByBuilder(builder);
			objectMapper.writeValue(response.getOutputStream(), results);
		} else if(action != null && action.equals("BUILDING_TYPE")){
			List<BuildingTypeOutput> results = buildingService.getBuildingType();
			objectMapper.writeValue(response.getOutputStream(), results);
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//add data
		BuildingDTO dto = HttpUtil.of(request.getReader()).toModel(BuildingDTO.class);
		buildingService.saveBuilding(dto);
	}
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//update data
	}
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
	}
}
