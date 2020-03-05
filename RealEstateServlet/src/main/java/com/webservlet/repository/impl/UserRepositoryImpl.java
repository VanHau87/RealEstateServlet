package com.webservlet.repository.impl;

import java.util.List;

import com.webservlet.entity.UserEntity;
import com.webservlet.repository.UserRepository;

public class UserRepositoryImpl extends JpaRepositoryImpl<UserEntity> implements UserRepository {

	@Override
	public List<UserEntity> findByRole(String roleCode) {
		StringBuilder sql = new StringBuilder("SELECT u.* FROM users u");
		sql.append(" INNER JOIN user_role ur ON u.userid = ur.userid");
		sql.append(" INNER JOIN role r ON ur.roleid = r.roleid");
		sql.append(" WHERE r.code ='").append(roleCode).append("'");
		return super.findAll(sql.toString());
	}

}
