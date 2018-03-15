package com.excilys.formation.cdb.service;

import java.time.LocalDate;
import java.util.ArrayList;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CompanyDB;
import com.excilys.formation.cdb.persistence.ComputerDB;

public enum ComputerService {
	
	INSTANCE;
	
	private ArrayList<Computer> list = ComputerDB.INSTANCE.list();
	
	public String listComputer (int from, int to) {
		String res = "";
		if (to > list.size()) {to = list.size();}
		for (Computer p : list.subList(from, to)) {
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
			int companyId = Integer.parseInt(companyIdStr);
			LocalDate introduced = LocalDate.parse(introducedStr);
			LocalDate discontinued = LocalDate.parse(introducedStr);
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
	
	public String updateComputer (String idStr, String name, String introducedStr, String discontinuedStr, String companyIdStr) throws Exception {
		int id = -1;
		try {
			id = Integer.parseInt(idStr);
			int companyId = Integer.parseInt(companyIdStr);
			LocalDate introduced = LocalDate.parse(introducedStr);
			LocalDate discontinued = LocalDate.parse(discontinuedStr);
			Validator.INSTANCE.computerIdValidation(id);
			Validator.INSTANCE.nameValidation(name);
			Validator.INSTANCE.datesValidation(introduced, discontinued);
			Validator.INSTANCE.manufactorValidation(companyId);
			ComputerDB.INSTANCE.update(new Computer(id, name, introduced, discontinued, CompanyDB.INSTANCE.selectOne(companyId)));
		} catch (NumberFormatException e) {
			throw new Exception("Wrong format company ID.");
		} catch (Exception e) {
			throw e;
		}
		return "Computer "+id+" updated.";
	}
	
	public String deleteComputer (int id) throws Exception {
		try {
			Validator.INSTANCE.computerIdValidation(id);
			ComputerDB.INSTANCE.delete(id);
		} catch (Exception e) {
			throw e;
		}
		return "Computer "+id+" removed from database.";
	}
}
