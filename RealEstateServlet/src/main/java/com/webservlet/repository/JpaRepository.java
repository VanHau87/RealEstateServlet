package com.webservlet.repository;

import java.util.List;
import java.util.Map;

public interface JpaRepository<T> {
	List<T> findAll(Map<String, Object> params, Object ...where);
	List<T> findAll(String sql);
	void insert(String sql, Object ...objects);
	Integer insert(Object object);
}
