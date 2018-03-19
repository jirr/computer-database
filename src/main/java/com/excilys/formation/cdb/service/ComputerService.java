package com.excilys.formation.cdb.service;
import java.util.List;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDB;

/**
 * @author jirr
 *
 */
public enum ComputerService {
	INSTANCE;

	private List<Computer> list = ComputerDB.INSTANCE.list();

	public String listComputer(int from, int to) {
		String res = "";
		if (to > list.size()) {to = list.size();}
		for (Computer p:list.subList(from, to)) {
			res += p.toString() + "\n";
		}
		return res;
	}

	public String selectOne(int id) throws Exception {
		return Validator.INSTANCE.computerIdValidation(id).toString();
	}

	public String createComputer(Computer computer) throws Exception {
		Validator.INSTANCE.nameValidation(computer.getName());
		Validator.INSTANCE.datesValidation(computer.getDateIntroduced(), computer.getDateDiscontinued());
		Validator.INSTANCE.manufactorValidation(computer.getManufactor().getId());
		ComputerDB.INSTANCE.createComputer(computer);
		return "New computer added to database.";
	}

	public String updateComputer(Computer computer) throws Exception {
		Validator.INSTANCE.computerIdValidation(computer.getId());
		Validator.INSTANCE.nameValidation(computer.getName());
		Validator.INSTANCE.datesValidation(computer.getDateIntroduced(), computer.getDateDiscontinued());
		Validator.INSTANCE.manufactorValidation(computer.getManufactor().getId());
		ComputerDB.INSTANCE.updateComputer(computer);
		return "Computer " + computer.getId() + " updated.";
	}

	public String deleteComputer(int id) throws Exception {
		Validator.INSTANCE.computerIdValidation(id);
		ComputerDB.INSTANCE.deleteComputer(id);
		return "Computer " + id + " removed from database.";
	}
}
