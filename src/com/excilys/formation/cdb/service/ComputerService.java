package com.excilys.formation.cdb.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CompanyDB;
import com.excilys.formation.cdb.persistence.ComputerDB;

public enum ComputerService {
	
	INSTANCE;
	
	public String listComputer () {
		ArrayList<Computer> list = ComputerDB.INSTANCE.list();
		String res = "";
		for (Computer p : list) {
			res += p.toString() + "\n";
		}
		return res;
	}
	
	public String selectOne (int id) throws Exception {
		String details = "";
		try {
			Validator.INSTANCE.computerIdValidation(id);
			details = ComputerDB.INSTANCE.selectOne(id).toString();
		} catch (Exception e) {
			throw e;
		}
		return details;
	}
	
	public String createComputer (String name, String introducedStr, String discontinuedStr, String companyIdStr) throws Exception {
		try {
			Timestamp introduced = stringToTimestamp(introducedStr);
			Timestamp discontinued = stringToTimestamp(introducedStr);
			int companyId = Integer.parseInt(companyIdStr);
			Validator.INSTANCE.nameValidation(name);
			Validator.INSTANCE.datesValidation(introduced, discontinued);
			Validator.INSTANCE.manufactorValidation(companyId);
			ComputerDB.INSTANCE.create(new Computer(name, introduced, discontinued, CompanyDB.INSTANCE.selectOne(companyId)));
		} catch (NumberFormatException e) {
			throw new Exception("Wrong format company ID.");
		} catch (Exception e) {
			throw e;
		}
		return "New computer added to database.";
	}
	
	public void updateComputer () {
		
	}
	
	public String deleteComputer (int id) throws Exception {
		try {
			Validator.INSTANCE.computerIdValidation(id);
			ComputerDB.delete(id);
		} catch (Exception e) {
			throw e;
		}
		return "Computer "+id+" removed from database.";
	}
	
	private Timestamp stringToTimestamp(String str_date) throws Exception {
		Timestamp timestampDate = null;
	    try {
		    DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
		    Date date = format.parse(str_date);
		    timestampDate = new Timestamp(date.getTime());
	    } catch (ParseException e) {
	    	throw new Exception("Date format invalid.");
	    }
	    return timestampDate;
	}
}
