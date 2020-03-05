package com.webservlet.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webservlet.dto.UserDTO;
import com.webservlet.service.UserService;
import com.webservlet.service.impl.UserServiceImpl;


@WebServlet(name = "user", urlPatterns = { "/user" })
public class UserAPI extends HttpServlet {
	private static final long serialVersionUID = 1L;
	UserService userService = new UserServiceImpl();
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String roleCode = req.getParameter("roleCode");
		if (roleCode != null) {
			List<UserDTO> results = userService.findByRole(roleCode);
			objectMapper.writeValue(resp.getOutputStream(), results);
		}
	}
}
