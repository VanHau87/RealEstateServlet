package com.webservlet.repository;

import java.util.List;

import com.webservlet.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity>{
	List<UserEntity> findByRole(String roleCode);
}
