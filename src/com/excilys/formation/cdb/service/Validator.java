package com.excilys.formation.cdb.service;

import java.sql.Timestamp;

import com.excilys.formation.cdb.persistence.*;

public enum Validator {
	
	INSTANCE;
	
	public void datesValidation (Timestamp d1, Timestamp d2) throws Exception {
		if (false) {
			throw new Exception("Incompatibility of dates.");
		}
	}
	
	public void nameValidation (String name) throws Exception {
		if (name.compareTo("") == 0) {
			throw new Exception("Name is required.");
		}
	}
	
	public void computerIdValidation (int id) throws Exception {
		if (ComputerDB.INSTANCE.selectOne(id) == null) {
			throw new Exception("Computer ID does not exist.");
		}
	}
	
	public void manufactorValidation (int id) throws Exception {
		if (CompanyDB.INSTANCE.selectOne(id) == null) {
			throw new Exception("Company ID does not exist.");
		}
	}
}
