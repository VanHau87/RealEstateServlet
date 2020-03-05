package com.webservlet.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class EntityManagerFactory {
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String url = "jdbc:mysql://localhost:3308/real_estate";
			String password = "";
			String user = "root";
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
}
