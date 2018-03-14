package com.excilys.formation.cdb.service;

import java.util.ArrayList;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;

public class CompanyService {
	
	public static String listCompany () {
		ArrayList<Company> list = CompanyDB.list();
		String res = "";
		for (Company c : list) {
			res += c.toString() + "\n";
		}
		return res;
	}
}