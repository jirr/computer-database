package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.excilys.formation.cdb.model.Computer;

public class ComputerMP {
	public static Computer resToComputer(ResultSet res) throws SQLException {
	    int idComputer = res.getInt( "id" );
	    String nameComputer = res.getString( "name" );
	    Timestamp introducedComputer = res.getTimestamp( "introduced" );
	    Timestamp discontinuedComputer = res.getTimestamp( "discontinued" );
	    int idCompany = res.getInt( "company_id" );
	    
	    return new Computer(idComputer, 
	    					nameComputer, 
	    					introducedComputer,
	    					discontinuedComputer,
	    					idCompany);
	}
}
