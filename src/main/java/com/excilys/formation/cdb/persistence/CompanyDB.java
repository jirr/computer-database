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
    private String countAllRequest = "SELECT count(id) FROM company;";

    /**
     * @return int number of companies
     * @throws DBException if can't reach the database
     */
    public int countAllCompany() throws DBException {
        try (Connection connection = ConnexionManager.INSTANCE.getConn();
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(countAllRequest);) {
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @return List<Company>
     * @throws DBException if can't reach the database
     */
    public List<Company> list() throws DBException {
        List<Company> companyList = new ArrayList<>();
        try (Connection connection = ConnexionManager.INSTANCE.getConn();
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(selectAllRequest + " ;");) {
            while (result.next()) {
                companyList.add(CompanyMapper.INSTANCE.resToCompany(result));
            }
            return companyList;
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @param limit index du dernier element
     * @param offset index du premier element
     * @return List<Company> The sublist of Company object from the DB
     * @throws DBException if can't reach the database
     */
    public List<Company> subList(int offset, int limit) throws DBException {
        List<Company> computerList = new ArrayList<>();
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
            PreparedStatement preparedStatement = conn.prepareStatement(selectAllRequest + " LIMIT ? OFFSET ?;");
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                computerList.add(CompanyMapper.INSTANCE.resToCompany(res));
            }
            return computerList;
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @param id of Company that should be in the DB
     * @return Optional<Company> contains the company, could be empty if the id does not exist
     * @throws DBException if can't reach the database
     */
    public Optional<Company> selectOne(int id) throws DBException {
        Company company = null;
        logger.info("Connection to database opening.");
        try (Connection connection = ConnexionManager.INSTANCE.getConn();
                PreparedStatement preparedStatement = connection.prepareStatement(selectAllRequest + " WHERE ca.id = ?;");) {
            preparedStatement.setInt(1, id);
            ResultSet result = preparedStatement.executeQuery();
            if (result.next()) {
                company = CompanyMapper.INSTANCE.resToCompany(result);
            }
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
            throw new DBException("Unable to reach the database.");
        }
        logger.info("Connection to database closed.");
        return Optional.ofNullable(company);
    }
}