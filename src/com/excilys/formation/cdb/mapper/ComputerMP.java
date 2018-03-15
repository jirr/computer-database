package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import com.excilys.formation.cdb.model.*;

public class ComputerMP {
	public static Computer resToComputer(ResultSet res) throws SQLException {
		    int idComputer = res.getInt("cuId");
		    String nameComputer = res.getString("cuName");
		    LocalDate introducedComputer = res.getDate("introduced").toLocalDate();
		    LocalDate discontinuedComputer = res.getDate("discontinued").toLocalDate();
		    Company manufactor = CompanyMP.resToCompany(res);
		    
		    return new Computer(idComputer, 
		    					nameComputer, 
		    					introducedComputer,
		    					discontinuedComputer,
		    					manufactor);
	}
}
