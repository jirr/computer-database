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
	
	public String selectOne (int id) {
		if (Validator.INSTANCE.computerIdValidation(id)) {
			return ComputerDB.INSTANCE.selectOne(id).toString();
		} else {
			return "Invalid Computer ID.";
		}
	}
	
	public String createComputer (String name, String introducedStr, String discontinuedStr, int companyId) {
		Timestamp introduced = stringToTimestamp(introducedStr);
		Timestamp discontinued = stringToTimestamp(introducedStr);
		if (!Validator.INSTANCE.computerDatesValidation(introduced, discontinued)) {
			return "Dates compatibility error.";
		} else if (!Validator.INSTANCE.manufactorValidation(companyId)) {
			return "Invalid Company ID.";
		} else {
			ComputerDB.INSTANCE.create(new Computer(name, introduced, discontinued, CompanyDB.INSTANCE.selectOne(companyId)));
			return "New computer added to database.";
		}
	}
	
	public void updateComputer () {
		
	}
	
	public String deleteComputer (int id) {
		if (Validator.INSTANCE.computerIdValidation(id)) {
			ComputerDB.delete(id);
			return "Computer "+id+" removed from database.";
		} else {
			return "Invalid Computer ID.";
		}
	}
	
	private Timestamp stringToTimestamp(String str_date) {
	    try {
		    DateFormat format = new SimpleDateFormat("dd/mm/yyyy");
		    Date date = format.parse(str_date);
		    Timestamp timestampDate = new Timestamp(date.getTime());
		    return timestampDate;
	    } catch (ParseException e) {
	    	e.printStackTrace();
	    	return null;
	    }
	}
}
