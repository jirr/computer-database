package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.cdb.model.Company;

public class CompanyMP {
	public static Company resToCompany(ResultSet res) throws SQLException {
		    int idCompany = res.getInt( "id" );
		    String nameCompany = res.getString( "name" );
		    
		    return new Company(idCompany, 
		    					nameCompany);
	}
}