package com.excilys.formation.cdb.service;

import java.sql.Timestamp;

import com.excilys.formation.cdb.model.*;
import com.excilys.formation.cdb.persistence.*;

public class Validator {
	public static boolean computerValidation (Computer computer) {
		return computerDatesValidation(	computer.getDateIntroduced(), 
										computer.getDateDiscontinued() ) 
				&& companyIdValidation(	computer.getIdCompany()) ? true:false;
	}
	
	public static boolean computerDatesValidation (Timestamp d1, Timestamp d2) {
		
		return true;
	}
	
	public static boolean computerIdValidation (int id) {
		return (ComputerDB.selectOne(id) == null)?false:true;
	}
	
	public static boolean companyIdValidation (int id) {
		return (CompanyDB.selectOne(id) == null)?false:true;
	}
	
	
}
