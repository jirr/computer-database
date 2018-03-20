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
                companyList.add(CompanyMapper.INSTANCE.resToCompany(result));
            }
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
        }
        return companyList;
    }

    /**
     * @param limit index du dernier element
     * @param offset index du premier element
     * @return List<Company> The sublist of Company object from the DB
     */
    public List<Company> subList(int limit, int offset) {
        List<Company> computerList = new ArrayList<>();
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
            PreparedStatement preparedStatement = conn.prepareStatement(selectAllRequest + " LIMIT ? OFFSET ?;");
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                computerList.add(CompanyMapper.INSTANCE.resToCompany(res));
            }
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
        }
        return computerList;
    }

    /**
     * @param id
     *            of Company that should be in the DB
     * @return Optional<Company> contains the company, could be empty if the id does
     *         not exist
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
            logger.error("Unable to reach the database: " + e.getMessage());
        }
        logger.info("Connection to database closed.");
        return Optional.ofNullable(company);
    }
}