package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.excilys.formation.cdb.mapper.CompanyMP;
import com.excilys.formation.cdb.model.Company;

public enum CompanyDB {
	
	INSTANCE;

	static Connection conn = ConnexionManager.INSTANCE.getConn();
	
	public ArrayList<Company> list () {
		ArrayList<Company> companyList = new ArrayList<>(); 
		try {
			Statement s = conn.createStatement();
			ResultSet res = s
					.executeQuery("SELECT ca.id as caId, ca.name as caName FROM company ca");
			
			while(res.next()) {
				companyList.add(CompanyMP.resToCompany(res));;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companyList;
	}
	
	public Company selectOne (int id) {
		Company cres = null;
		ResultSet res = null;
		try {
			PreparedStatement ps = conn.prepareStatement("SELECT ca.id as caId, ca.name as caName FROM company ca "
					+ "WHERE ca.id = ?;");
			ps.setInt(1, id);
			res = ps.executeQuery();
			if (!res.next()) {
				return null;
			} else {
				cres = CompanyMP.resToCompany(res);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return cres;
	}
}
