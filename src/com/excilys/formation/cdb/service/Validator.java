package com.excilys.formation.cdb.service;

import java.sql.Timestamp;

import com.excilys.formation.cdb.model.*;
import com.excilys.formation.cdb.persistence.*;

public enum Validator {
	
	INSTANCE;
	
	public boolean computerValidation (Computer computer) {
		return computerDatesValidation(	computer.getDateIntroduced(), 
										computer.getDateDiscontinued() ) 
				&& manufactorValidation(	computer.getManufactor().getId()) ? true:false;
	}
	
	public boolean computerDatesValidation (Timestamp d1, Timestamp d2) {
		
		return true;
	}
	
	public boolean computerIdValidation (int id) {
		return (ComputerDB.INSTANCE.selectOne(id) == null)?false:true;
	}
	
	public boolean manufactorValidation (int id) {
		return (CompanyDB.INSTANCE.selectOne(id) == null)?false:true;
	}
	
}
