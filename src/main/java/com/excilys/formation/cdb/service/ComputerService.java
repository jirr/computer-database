package com.excilys.formation.cdb.service;

import java.time.LocalDate;
import java.util.List;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CompanyDB;
import com.excilys.formation.cdb.persistence.ComputerDB;

public enum ComputerService {
	
	INSTANCE;
	
	private List<Computer> list = ComputerDB.INSTANCE.list();
	
	public String listComputer (int from, int to) {
		String res = "";
		if (to > list.size()) {to = list.size();}
		for (Computer p : list.subList(from, to)) {
			res += p.toString() + "\n";
		}
		return res;
	}
	
	public String selectOne (int id) throws Exception {
		try {
			return Validator.INSTANCE.computerIdValidation(id).toString();
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String createComputer (Computer computer) throws Exception {
		try {
			Validator.INSTANCE.nameValidation(computer.getName());
			Validator.INSTANCE.datesValidation(computer.getDateIntroduced(), computer.getDateDiscontinued());
			Validator.INSTANCE.manufactorValidation(computer.getManufactor().getId());
			ComputerDB.INSTANCE.createComputer(computer);
			return "New computer added to database.";
		} catch (Exception e) {
			throw e;
		}
	}
	
	public String updateComputer (Computer computer) throws Exception {
		try {
			Validator.INSTANCE.computerIdValidation(computer.getId());
			Validator.INSTANCE.nameValidation(computer.getName());
			Validator.INSTANCE.datesValidation(computer.getDateIntroduced(), computer.getDateDiscontinued());
			Validator.INSTANCE.manufactorValidation(computer.getManufactor().getId());
			ComputerDB.INSTANCE.updateComputer(computer);
		} catch (Exception e) {
			throw e;
		}
		return "Computer "+computer.getId()+" updated.";
	}
	
	public String deleteComputer (int id) throws Exception {
		try {
			Validator.INSTANCE.computerIdValidation(id);
			ComputerDB.INSTANCE.deleteComputer(id);
		} catch (Exception e) {
			throw e;
		}
		return "Computer "+id+" removed from database.";
	}
}
