package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum ConnexionManager{
	
	INSTANCE;
	
	private Connection conn;
		
	private ConnexionManager () {
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String url = "jdbc:mysql://localhost:3306/computer-database-db";
		
		try {
			conn = DriverManager.getConnection(url, "admincdb", "qwerty1234");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the conn
	 */
	public Connection getConn() {
		return conn;
	}
}
