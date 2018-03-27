package com.excilys.formation.cdb.persistence;

import java.io.IOException;
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
     * @throws DBException if can't reach the database
     */
    public int countAllComputer() throws DBException {
        try (Connection connection = ConnexionManager.INSTANCE.getConn();
                Statement statement = connection.createStatement();
                ResultSet result = statement.executeQuery(countAllRequest)) {
            result.next();
            return result.getInt(1);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            logger.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @return List<Computer> The list of all Computer object from the DB
     * @throws DBException if can't reach the database
     */
    public List<Computer> listAll() throws DBException {
        List<Computer> computerList = new ArrayList<>();
        try (Connection conn = ConnexionManager.INSTANCE.getConn();
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(selectAllRequest + " ;")) {
            while (result.next()) {
                computerList.add(ComputerMapper.INSTANCE.resToComputer(result));
            }
            return computerList;
        } catch (SQLException | ClassNotFoundException | IOException e) {
            logger.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @param limit index du dernier element
     * @param offset index du premier element
     * @return List<Computer> The sublist of Computer object from the DB
     * @throws DBException if can't reach the database
     */
    public List<Computer> subList(int offset, int limit) throws DBException {
        List<Computer> computerList = new ArrayList<>();
        try (Connection conn = ConnexionManager.INSTANCE.getConn();
                PreparedStatement preparedStatement = conn.prepareStatement(selectAllRequest + " LIMIT ? OFFSET ?;");
                ) {
            preparedStatement.setInt(1, limit);
            preparedStatement.setInt(2, offset);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                computerList.add(ComputerMapper.INSTANCE.resToComputer(res));
            }
            return computerList;
        } catch (SQLException | ClassNotFoundException | IOException e) {
            logger.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @param computer the computer object to create in the DB
     * @throws DBException if can't reach the database
     */
    public void createComputer(Computer computer) throws DBException {
        try (Connection connection = ConnexionManager.INSTANCE.getConn();
                AutoSetAutoCommit autoCommit = new AutoSetAutoCommit(connection,false);
                AutoRollback autoRollbackConnection = new AutoRollback(connection);
                PreparedStatement preparedStatement = connection.prepareStatement(createRequest);) {
            preparedStatement.setString(1, computer.getName());
            if (computer.getDateIntroduced().isPresent()) {
                preparedStatement.setDate(2, Date.valueOf(computer.getDateIntroduced().get()));
            } else {
                preparedStatement.setNull(2, java.sql.Types.DATE);
            }
            if (computer.getDateDiscontinued().isPresent()) {
                preparedStatement.setDate(3, Date.valueOf(computer.getDateDiscontinued().get()));
            } else {
                preparedStatement.setNull(3, java.sql.Types.DATE);
            }
            if (computer.getManufactor().isPresent()) {
                preparedStatement.setInt(4, computer.getManufactor().get().getId());
            } else {
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
            }
            preparedStatement.executeUpdate();
            autoRollbackConnection.commit();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            logger.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @param computer the computer object to update in the DB
     * @throws DBException if can't reach the database
     */
    public void updateComputer(Computer computer) throws DBException {
        try (Connection connection = ConnexionManager.INSTANCE.getConn();
                AutoSetAutoCommit autoCommit = new AutoSetAutoCommit(connection,false);
                AutoRollback autoRollbackConnection = new AutoRollback(connection);
                PreparedStatement preparedStatement = connection.prepareStatement(updateRequest);) {
            preparedStatement.setString(1, computer.getName());
            if (computer.getDateIntroduced().isPresent()) {
                preparedStatement.setDate(2, Date.valueOf(computer.getDateIntroduced().get()));
            } else {
                preparedStatement.setNull(2, java.sql.Types.DATE);
            }
            if (computer.getDateDiscontinued().isPresent()) {
                preparedStatement.setDate(3, Date.valueOf(computer.getDateDiscontinued().get()));
            } else {
                preparedStatement.setNull(3, java.sql.Types.DATE);
            }
            if (computer.getManufactor().isPresent()) {
                preparedStatement.setInt(4, computer.getManufactor().get().getId());
            } else {
                preparedStatement.setNull(4, java.sql.Types.INTEGER);
            }
            preparedStatement.setInt(5, computer.getId());
            preparedStatement.executeUpdate();
            autoRollbackConnection.commit();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            logger.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @param id the ID of computer to delete from the DB
     * @throws DBException if can't reach the database
     */
    public void deleteComputer(int id) throws DBException {
        try (Connection conn = ConnexionManager.INSTANCE.getConn()) {
            PreparedStatement preparedStatement = conn.prepareStatement(deleteRequest);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException | ClassNotFoundException | IOException e) {
            logger.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @param id the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id does not exist
     * @throws DBException if can't reach the database
     */
    public Optional<Computer> selectOne(int id) throws DBException {
        try (Connection conn = ConnexionManager.INSTANCE.getConn();
                PreparedStatement ps = conn.prepareStatement(selectAllRequest + " WHERE cu.id = ?;");) {
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            Computer computer = null;
            if (res.next()) {
                computer = ComputerMapper.INSTANCE.resToComputer(res);
            }
            return Optional.ofNullable(computer);
        } catch (SQLException | ClassNotFoundException | IOException e) {
            logger.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }
}