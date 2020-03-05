package com.webservlet.api.input;

public class BuildingInput {
	private String name;
	private String district;
	private String numberOfBasement;
	private String floorArea;
	private String rentAreaFrom;
	private String rentCostFrom;
	private String rentAreaTo;
	private String rentCostTo;
	private String[] types = new String[] {};
	private Integer staffId;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDistrict() {
		return district;
	}
	public void setDistrict(String district) {
		this.district = district;
	}
	public String getNumberOfBasement() {
		return numberOfBasement;
	}
	public void setNumberOfBasement(String numberOfBasement) {
		this.numberOfBasement = numberOfBasement;
	}
	public String getFloorArea() {
		return floorArea;
	}
	public void setFloorArea(String floorArea) {
		this.floorArea = floorArea;
	}
	public String getRentAreaFrom() {
		return rentAreaFrom;
	}
	public void setRentAreaFrom(String rentAreaFrom) {
		this.rentAreaFrom = rentAreaFrom;
	}
	public String getRentCostFrom() {
		return rentCostFrom;
	}
	public void setRentCostFrom(String rentCostFrom) {
		this.rentCostFrom = rentCostFrom;
	}
	public String getRentAreaTo() {
		return rentAreaTo;
	}
	public void setRentAreaTo(String rentAreaTo) {
		this.rentAreaTo = rentAreaTo;
	}
	public String getRentCostTo() {
		return rentCostTo;
	}
	public void setRentCostTo(String rentCostTo) {
		this.rentCostTo = rentCostTo;
	}
	public String[] getTypes() {
		return types;
	}
	public void setTypes(String[] types) {
		this.types = types;
	}
	public Integer getStaffId() {
		return staffId;
	}
	public void setStaffId(Integer staffId) {
		this.staffId = staffId;
	}
}
