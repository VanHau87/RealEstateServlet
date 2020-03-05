package com.webservlet.service;

import java.util.List;

import com.webservlet.dto.UserDTO;

public interface UserService{
	List<UserDTO> findByRole(String roleCode);
}
