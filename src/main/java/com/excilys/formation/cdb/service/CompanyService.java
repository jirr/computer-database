package com.excilys.formation.cdb.service;

import java.util.List;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;

public enum CompanyService {
	
	INSTANCE;
	
	private List<Company> list = CompanyDB.INSTANCE.list();
	
	public String listCompany (int from, int to) {
		String res = "";
		if (to > list.size()) {to = list.size();}
		for (Company c : list.subList(from, to)) {
			res += c.toString() + "\n";
		}
		return res;
	}
	
	public Company getCompany (int id) throws Exception {
		try {
			return Validator.INSTANCE.manufactorValidation(id);
		} catch (Exception e) {
			throw e;
		}
	}
}
