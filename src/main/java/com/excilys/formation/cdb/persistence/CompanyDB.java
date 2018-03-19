package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;

/**
 * @author jirr
 *
 */
public enum CompanyDB {
	INSTANCE;

	private final Logger logger = LoggerFactory.getLogger(CompanyDB.class);

	private String selectAllRequest = "SELECT ca.id as caId, ca.name as caName FROM company ca";

	/**
	 * @return List<Company>
	 */
	public List<Company> list() {
		List<Company> companyList = new ArrayList<>(); 
		try (Connection connection = ConnexionManager.INSTANCE.getConn()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(selectAllRequest + " ;");
			while (result.next()) {
				companyList.add(CompanyMapper.INSTANCE.resToCompany(result));;
			} 
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return companyList;
	}

	/**
	 * @param id
	 * @return
	 */
	public Optional<Company> selectOne(int id) {
		Company company = null;
		ResultSet result = null;
		logger.info("Connection to database opening.");
		try (Connection connection = ConnexionManager.INSTANCE.getConn()) {
			PreparedStatement preparedStatement = connection.prepareStatement(selectAllRequest + " WHERE ca.id = ?;");
			preparedStatement.setInt(1, id);
			result = preparedStatement.executeQuery();
			if (result.next()) {
				company = CompanyMapper.INSTANCE.resToCompany(result);
			}
		} catch (SQLException e) {
			logger.error("Unable to reach the database.");
			e.printStackTrace();
		}
		logger.info("Connection to database closed.");
		return Optional.ofNullable(company);
	}
}
