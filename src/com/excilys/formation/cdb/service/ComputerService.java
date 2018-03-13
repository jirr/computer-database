package com.excilys.formation.cdb.service;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDB;

public class ComputerService {
	
	
	public static String listComputer () {
		ArrayList<Computer> list = ComputerDB.list();
		String res = "";
		for (Computer p : list) {
			res += p.toString() + "\n";
		}
		return res;
	}
	
	public static String selectOne (int id) {
		if (Validator.computerIdValidation(id)) {
			return ComputerDB.selectOne(id).toString();
		} else {
			return "Invalid Computer ID.";
		}
	}
	
	public static String createComputer (String name, String introducedStr, String discontinuedStr, int companyId) {
		Timestamp introduced = stringToTimestamp(introducedStr);
		Timestamp discontinued = stringToTimestamp(introducedStr);
		Computer newCptr = new Computer(name, introduced, discontinued, companyId);
		if (!Validator.computerDatesValidation(introduced, discontinued)) {
			return "Dates compatibility error.";
		} else if (!Validator.companyIdValidation(companyId)) {
			return "Invalid Company ID.";
		} else {
			ComputerDB.create(newCptr);
			return "New computer added to database.";
		}
	}
	
	public static void updateComputer () {
		
	}
	
	public static String deleteComputer (int id) {
		if (Validator.computerIdValidation(id)) {
			ComputerDB.delete(id);
			return "Computer "+id+" removed from database.";
		} else {
			return "Invalid Computer ID.";
		}
	}
	
	public static Timestamp stringToTimestamp(String str_date) {
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
