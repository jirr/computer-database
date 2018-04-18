package com.excilys.formation.cdb.persistence;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;

@Repository
public class ComputerDAO {

    private final String SELECT_ALL_COMPUTERS = "SELECT cu.id as cuId, cu.name as cuName, introduced, discontinued, ca.id as caId, ca.name as caName FROM computer cu LEFT JOIN company ca ON ca.id = cu.company_id";
    private final String SELECT_ONE_COMPUTER = "SELECT cu.id as cuId, cu.name as cuName, introduced, discontinued, ca.id as caId, ca.name as caName FROM computer cu LEFT JOIN company ca ON ca.id = cu.company_id WHERE cu.id = ?;";
    private final String CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?);";
    private final String UPDATE_COMPUTER = "UPDATE computer SET name = ?, introduced = ?, discontinued = ?, company_id = ? WHERE computer.id = ?;";
    private final String DELETE_COMPUTER = "DELETE FROM computer WHERE id=?;";
    private final String COUNT_ALL_COMPUTERS = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON company.id = computer.company_id";
    private final String SELECT_COMPUTERS_WITH_COMPANY = "SELECT cu.id FROM computer as cu LEFT JOIN company as ca ON cu.id = ca.id WHERE cu.company_id = ?;";

    private JdbcTemplate jdbcTemplate;
    private ComputerMapper computerMapper;

    @Autowired
    public ComputerDAO(DataSource dataSource, ComputerMapper computerMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.computerMapper = computerMapper;
    }

    /**
     * @return List<Computer> The list of all Computer object from the DB
     */
    public List<Computer> listAll() {
        return this.jdbcTemplate.queryForList(SELECT_ALL_COMPUTERS, Computer.class);
    }

    /**
     * @param computer the computer object to create in the DB
     */
    public void createComputer(Computer computer) {
        this.jdbcTemplate.update(CREATE_COMPUTER, preparedStatement -> {
                computerToPreparedStatement(computer, preparedStatement);
            });
    }

    /**
     * @param computer the computer object to create in the DB
     */
    public void updateComputer(Computer computer) {
        this.jdbcTemplate.update(UPDATE_COMPUTER, preparedStatement -> {
                computerToPreparedStatement(computer, preparedStatement);
                preparedStatement.setInt(5, computer.getId());
            });
    }

    /**
     * @param ids the IDs of computer to delete from the DB
     */
    public void deleteComputer(int... ids) {
        for (int id : ids) {
            this.jdbcTemplate.update(DELETE_COMPUTER, id);
        }
    }

    /**
     * @param id the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id does not exist
     */
    public Optional<Computer> selectOne(int id) {
        Computer computer;
        try {
            computer = this.jdbcTemplate.query(SELECT_ONE_COMPUTER, (ResultSet resultSet, int row) -> computerMapper.resToComputer(resultSet), id).get(0);
        } catch (IndexOutOfBoundsException e) {
            computer = null;
        }
        return Optional.ofNullable(computer);
    }

    /**
     * @param id the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id does not exist
     */
    public List<Integer> getComputersWithCompanyId(int id) {
        List<Integer> ids = this.jdbcTemplate.queryForList(SELECT_COMPUTERS_WITH_COMPANY, int.class);
        return ids;
    }

    /**
     * @param keywords The keywords of the search, can be empty
     * @return int number of computers
     */
    public int countAllComputer(String keywords) {
        String like = (keywords.length() > 0) ? " WHERE computer.name LIKE '%" + keywords + "%' OR company.name LIKE '%" + keywords + "%'" : "";
        String count = COUNT_ALL_COMPUTERS + like + ";";
        return this.jdbcTemplate.queryForObject(count, Integer.class);
    }

    /**
     * @param limit index du dernier element
     * @param offset index du premier element
     * @param keywords The keywords of the search, can be empty
     * @param sortBy Name of the column to sort on
     * @param asc Is the sort asc or desc
     * @return List<Computer> The sublist of Computer object from the DB
     */
    public List<Computer> subList(int offset, int limit, String keywords, String sortBy, boolean asc) {
        String like = "";
        String sort = "";
        Object[] object;
        if (sortBy.length() > 0) {
            sort += " ORDER BY " + sortBy;
            sort += asc ? " ASC" : " DESC";
        }
        if (keywords.length() > 0) {
            object = new Object[] {"%" + keywords + "%", "%" + keywords + "%", limit, offset};
            like = " WHERE (cu.name LIKE ? or ca.name LIKE ?)";
        } else {
            object = new Object[] {limit, offset};
        }
        String request = SELECT_ALL_COMPUTERS + like + sort + " LIMIT ? OFFSET ?;";
        return this.jdbcTemplate.query(request, object, (ResultSet resultSet, int row) -> computerMapper.resToComputer(resultSet));
    }

    /**
     * @param computer The computer to put in the PreparedStatement
     * @param preparedStatement The PreparedStatement to fill
     * @return PreparedStatement The PreparedStatement filled with computer's field
     * @throws SQLException if can't fill the PreparedStatement
     */
    public PreparedStatement computerToPreparedStatement(Computer computer, PreparedStatement preparedStatement) throws SQLException {
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
        return preparedStatement;
    }
}
