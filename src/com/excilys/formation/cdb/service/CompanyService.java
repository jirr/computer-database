package com.excilys.formation.cdb.service;

import java.util.ArrayList;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;

public enum CompanyService {
	
	INSTANCE;
	
	private ArrayList<Company> list = CompanyDB.INSTANCE.list();
	
	public String listCompany (int from, int to) {
		String res = "";
		if (to > list.size()) {to = list.size();}
		for (Company c : list.subList(from, to)) {
			res += c.toString() + "\n";
		}
		return res;
	}
}
