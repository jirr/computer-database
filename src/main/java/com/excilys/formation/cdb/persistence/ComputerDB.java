package com.excilys.formation.cdb.persistence;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;

public enum ComputerDB {
    INSTANCE;

    private final Logger logger = LoggerFactory.getLogger(ComputerDB.class);

    private final String selectAllRequest = "SELECT cu.id as cuId, cu.name as cuName, introduced, discontinued, ca.id as caId, ca.name as caName FROM computer cu LEFT JOIN company ca ON ca.id = cu.company_id";
    private final String createRequest = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
    private final String updateRequest = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE id = ?;";
    private final String deleteRequest = "DELETE FROM computer WHERE id=?;";
    private final String countAllRequest = "SELECT count(id) FROM computer;";

    /**
     * @return int number of computers
     */
    public int countAllComputer() {
        try (Connection connection = ConnexionManager.INSTANCE.getConn()) {
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(countAllRequest);
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
        }
        return -1;
    }

    /**
     * @return List<Computer> The list of all Computer object from the DB
     */
    public List<Computer> listAll() {
        List<Computer> computerList = new ArrayList<>();
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
            Statement s = conn.createStatement();
            ResultSet res = s.executeQuery(selectAllRequest + " ;");
            while (res.next()) {
                computerList.add(ComputerMapper.INSTANCE.resToComputer(res));
            }
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
        }
        return computerList;
    }

    /**
     * @param limit index du dernier element
     * @param offset index du premier element
     * @return List<Computer> The sublist of Computer object from the DB
     */
    public List<Computer> subList(int offset, int limit) {
        List<Computer> computerList = new ArrayList<>();
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
            PreparedStatement preparedStatement = conn.prepareStatement(selectAllRequest + " LIMIT ? OFFSET ?;");
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                computerList.add(ComputerMapper.INSTANCE.resToComputer(res));
            }
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
        }
        return computerList;
    }

    /**
     * @param computer
     *            the computer object to create in the DB
     */
    public void createComputer(Computer computer) {
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
            PreparedStatement preparedStatement = conn.prepareStatement(createRequest);
            preparedStatement.setString(1, computer.getName());
            preparedStatement.setDate(2, Date.valueOf(computer.getDateIntroduced()));
            preparedStatement.setDate(3, Date.valueOf(computer.getDateDiscontinued()));
            preparedStatement.setInt(4, computer.getManufactor().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
        }
    }

    /**
     * @param computer
     *            the computer object to update in the DB
     */
    public void updateComputer(Computer computer) {
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
            PreparedStatement preparedStatement = conn.prepareStatement(updateRequest);
            preparedStatement.setString(1, computer.getName());
            preparedStatement.setDate(2, Date.valueOf(computer.getDateIntroduced()));
            preparedStatement.setDate(3, Date.valueOf(computer.getDateDiscontinued()));
            preparedStatement.setInt(4, computer.getManufactor().getId());
            preparedStatement.setInt(5, computer.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
        }
    }

    /**
     * @param id
     *            the ID of computer to delete from the DB
     */
    public void deleteComputer(int id) {
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
            PreparedStatement preparedStatement = conn.prepareStatement(deleteRequest);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
        }
    }

    /**
     * @param id
     *            the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id
     *         does not exist
     */
    public Optional<Computer> selectOne(int id) {
        Computer cres = null;
        ResultSet res = null;
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
            PreparedStatement ps = conn.prepareStatement(selectAllRequest + " WHERE cu.id = ?;");
            ps.setInt(1, id);
            res = ps.executeQuery();
            if (res.next()) {
                cres = ComputerMapper.INSTANCE.resToComputer(res);
            }
        } catch (SQLException e) {
            logger.error("Unable to reach the database: " + e.getMessage());
        }
        return Optional.ofNullable(cres);
    }
}