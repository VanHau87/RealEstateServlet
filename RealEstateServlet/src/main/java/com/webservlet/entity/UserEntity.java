package com.webservlet.entity;

import com.webservlet.annotation.Column;
import com.webservlet.annotation.Entity;
import com.webservlet.annotation.Table;

@Entity
@Table(name = "users")
public class UserEntity extends BaseEntity{
	@Column(name = "userId")
	private Integer userId;
	@Column(name = "username")
	private String userName;
	@Column(name = "fullname")
	private String fullName;
	@Column(name = "password")
	private String password;
	
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
