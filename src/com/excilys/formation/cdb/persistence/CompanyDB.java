package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.cdb.model.Company;

public class CompanyDB {

	Connection conn = ConnexionManager.INSTANCE.getConn();
	
	public ArrayList<Company> list () throws SQLException {
		ArrayList<Company> companyList = new ArrayList<>(); 
		Statement s = conn.createStatement();
		ResultSet res = s
				.executeQuery("SELECT * FROM COMPUTER");
		
		while(res.next()) {
		    int idCompany = res.getInt( "id" );
		    String nameCompany = res.getString( "name" );
		    
		    companyList.add(new Company(	idCompany, 
		    								nameCompany));
		}
		return companyList;
	}
}
