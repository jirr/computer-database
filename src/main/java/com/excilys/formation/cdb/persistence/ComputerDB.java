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

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;

@Repository
@EnableTransactionManagement
public class ComputerDB {
    
    @Autowired
    private DataSource dataSource;

    private final Logger LOGGER = LoggerFactory.getLogger(ComputerDB.class);

    private final String selectAllRequest = "SELECT cu.id as cuId, cu.name as cuName, introduced, discontinued, ca.id as caId, ca.name as caName FROM computer cu LEFT JOIN company ca ON ca.id = cu.company_id";
    private final String createRequest = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
    private final String updateRequest = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? ";
    private final String deleteRequest = "DELETE FROM computer WHERE id=?;";
    private final String countAllRequest = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON company.id = computer.company_id";

    /**
     * @param keywords The keywords of the search, can be empty
     * @return int number of computers
     * @throws DBException if can't reach the database
     */
    public int countAllComputer(String keywords) throws DBException {
        String like = (keywords.length() > 0) ? " WHERE computer.name LIKE '%" + keywords + "%' OR company.name LIKE '%" + keywords + "%'" : "";
        String request = countAllRequest + like + ";";
        try (Connection connection = DataSourceUtils.getConnection(dataSource);
                PreparedStatement preparedStatement = connection.prepareStatement(request)) {
            ResultSet result = preparedStatement.executeQuery(request);
            result.next();
            return result.getInt(1);
        } catch (SQLException e) {
            LOGGER.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @return List<Computer> The list of all Computer object from the DB
     * @throws DBException if can't reach the database
     */
    public List<Computer> listAll() throws DBException {
        List<Computer> computerList = new ArrayList<>();
        try (Connection conn = DataSourceUtils.getConnection(dataSource);
                Statement statement = conn.createStatement();
                ResultSet result = statement.executeQuery(selectAllRequest + " ;")) {
            while (result.next()) {
                computerList.add(ComputerMapper.INSTANCE.resToComputer(result));
            }
            return computerList;
        } catch (SQLException e) {
            LOGGER.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @param limit index du dernier element
     * @param offset index du premier element
     * @param keywords The keywords of the search, can be empty
     * @return List<Computer> The sublist of Computer object from the DB
     * @throws DBException if can't reach the database
     */
    public List<Computer> subList(int offset, int limit, String keywords, String sortBy, boolean asc) throws DBException {
        List<Computer> computerList = new ArrayList<>();
        int indiceStatement = (keywords.length() > 0) ? 0 : 2;
        String like = (keywords.length() > 0) ? " WHERE (cu.name LIKE ? or ca.name LIKE ?)" : "";
        String sort = "";
        if (sortBy.length() > 0) {
            sort += " ORDER BY " + sortBy;
            sort += asc ? " ASC" : " DESC";
        }
        String request = selectAllRequest + like + sort + " LIMIT ? OFFSET ?;";
        try (Connection conn = DataSourceUtils.getConnection(dataSource);
                PreparedStatement preparedStatement = conn.prepareStatement(request);) {
            if (keywords.length() > 0) {
                preparedStatement.setString(1 - indiceStatement, "%" + keywords + "%");
                preparedStatement.setString(2 - indiceStatement, "%" + keywords + "%");
            }
            preparedStatement.setInt(3 - indiceStatement, limit);
            preparedStatement.setInt(4 - indiceStatement, offset);
            ResultSet res = preparedStatement.executeQuery();
            while (res.next()) {
                computerList.add(ComputerMapper.INSTANCE.resToComputer(res));
            }
            return computerList;
        } catch (SQLException e) {
            LOGGER.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @param computer the computer object to create in the DB
     * @throws DBException if can't reach the database
     */
    @Transactional(rollbackFor = Exception.class)
    public void createComputer(Computer computer) throws DBException {
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            PreparedStatement preparedStatement = connection.prepareStatement(createRequest);
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
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @param computer the computer object to update in the DB
     * @throws DBException if can't reach the database
     */
    @Transactional(rollbackFor = Exception.class)
    public void updateComputer(Computer computer) throws DBException {
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            PreparedStatement preparedStatement = connection.prepareStatement(updateRequest);
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
            preparedStatement.close();
        } catch (SQLException e) {
            LOGGER.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @param ids the IDs of computer to delete from the DB
     * @throws DBException if can't reach the database
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteComputer(int... ids) throws DBException {
        try {
            Connection connection = DataSourceUtils.getConnection(dataSource);
            deleteComputerWithConnection(connection, ids);
        } catch (SQLException e) {
            LOGGER.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }

    /**
     * @param connection the connection
     * @param ids IDs of computers to delete
     * @throws SQLException if problem in database.
     */
    @Transactional(rollbackFor = Exception.class)
    public void deleteComputerWithConnection(Connection connection, int... ids) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(deleteRequest);) {
            for (int id : ids) {
                preparedStatement.setInt(1, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            LOGGER.error("Errors in computers suppresions: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * @param id the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id does not exist
     * @throws DBException if can't reach the database
     */
    public Optional<Computer> selectOne(int id) throws DBException {
        try (Connection conn = DataSourceUtils.getConnection(dataSource);
                PreparedStatement ps = conn.prepareStatement(selectAllRequest + " WHERE cu.id = ?;");) {
            ps.setInt(1, id);
            ResultSet res = ps.executeQuery();
            Computer computer = null;
            if (res.next()) {
                computer = ComputerMapper.INSTANCE.resToComputer(res);
            }
            return Optional.ofNullable(computer);
        } catch (SQLException e) {
            LOGGER.error("Unable to reach the database: {}", e.getMessage(), e);
            throw new DBException("Unable to reach the database.");
        }
    }
}