package com.excilys.formation.cdb.persistence;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.mapper.CompanyMP;
import com.excilys.formation.cdb.model.Company;

public enum CompanyDB {
	
	INSTANCE;
	
	private final Logger logger = LoggerFactory.getLogger(CompanyDB.class);
	
	private String selectAllRequest = "SELECT ca.id as caId, ca.name as caName FROM company ca";
	
	public List<Company> list () {
		List<Company> companyList = new ArrayList<>(); 
		try (Connection conn = ConnexionManager.INSTANCE.getConn()){
			Statement s = conn.createStatement();
			ResultSet res = s.executeQuery(selectAllRequest+" ;");
			
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
		logger.info("Connection to database opening.");
		try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
			PreparedStatement ps = conn.prepareStatement(selectAllRequest+" WHERE ca.id = ?;");
			ps.setInt(1, id);
			res = ps.executeQuery();
			if (!res.next()) {
				logger.warn("Fail to get data with id {}.", id);
				return null;
			} else {
				cres = CompanyMP.resToCompany(res);
			}
		} catch (SQLException e) {
			logger.error("Unable to reach the database.");
			e.printStackTrace();
		}
		logger.info("Connection to database closed.");
		return cres;
	}
}
