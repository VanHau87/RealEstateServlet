package com.webservlet.entity;

import java.sql.Timestamp;

import com.webservlet.annotation.Column;

public class BaseEntity {
	@Column(name = "createddate")
	private Timestamp createdDate;
	
	@Column(name = "modifieddate")
	private Timestamp modifiedDate;
	
	@Column(name = "createdby")
	private String createdBy;
	
	@Column(name = "modifiedby")
	private String modifiedBy;

	public Timestamp getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Timestamp createdDate) {
		this.createdDate = createdDate;
	}

	public Timestamp getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Timestamp modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	
}
