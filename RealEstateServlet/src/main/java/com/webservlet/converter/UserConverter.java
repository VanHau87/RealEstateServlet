package com.webservlet.converter;

import org.modelmapper.ModelMapper;

import com.webservlet.dto.UserDTO;
import com.webservlet.entity.UserEntity;

public class UserConverter {
	private static ModelMapper modelMapper = new ModelMapper();
	
	public static UserDTO entity2DTO(UserEntity entity) {
		return modelMapper.map(entity, UserDTO.class);
	}
	public static UserEntity dto2Entity(UserDTO dto) {
		return modelMapper.map(dto, UserEntity.class);
	}
}
