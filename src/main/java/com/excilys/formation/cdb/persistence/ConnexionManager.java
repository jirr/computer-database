package com.excilys.formation.cdb.persistence;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public enum ConnexionManager{
	
	INSTANCE;
	
	private Connection conn;
	private Properties props;
	private FileInputStream file;
	
	/**
	 * @return the conn
	 */
	public Connection getConn() {
		props = new Properties();
		try {
			file = new FileInputStream("config/db/db.properties");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			props.load(file);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			Class.forName(props.getProperty("jdbc.driver"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection(	props.getProperty ("jdbc.url"), 
												props.getProperty ("jdbc.username"),
												props.getProperty ("jdbc.password"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
