package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.cdb.model.Company;

public class CompanyDB {

	Connection conn = ConnexionManager.INSTANCE.getConn();
	ArrayList<Company> companyList = new ArrayList<>(); 
	
	
	public void list () throws SQLException {
			
		Statement s = conn.createStatement();
		ResultSet res = s
				.executeQuery("SELECT * FROM COMPUTER");
		
		while(res.next()) {
		    int idComputer = res.getInt( "id" );
		    String nameComputer = res.getString( "name" );
		    
		    companyList.add(new Company(	idComputer, 
		    								nameComputer));
		}
	}
}
