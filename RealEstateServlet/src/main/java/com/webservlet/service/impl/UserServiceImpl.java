package com.webservlet.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.webservlet.converter.UserConverter;
import com.webservlet.dto.UserDTO;
import com.webservlet.entity.UserEntity;
import com.webservlet.repository.UserRepository;
import com.webservlet.repository.impl.UserRepositoryImpl;
import com.webservlet.service.UserService;

public class UserServiceImpl implements UserService {
	
	UserRepository userRepository = new UserRepositoryImpl();
	@Override
	public List<UserDTO> findByRole(String roleCode) {
		List<UserEntity> entities = userRepository.findByRole(roleCode);
		return entities.stream().map(item ->UserConverter.entity2DTO(item)).collect(Collectors.toList());
	}

}
