package com.excilys.formation.cdb.persistence;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

<<<<<<< HEAD
import javax.sql.DataSource;

=======
import org.hibernate.SessionFactory;
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Computer;
=======
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.QCompany;
import com.excilys.formation.cdb.model.QComputer;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)

@Repository
public class ComputerDAO {

<<<<<<< HEAD
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
=======
    private HibernateQueryFactory hibernateQueryFactory;
    private QComputer qComputer;
    private QCompany qCompany;

    @Autowired 
    public ComputerDAO(SessionFactory sessionFactory) {
        this.hibernateQueryFactory = new HibernateQueryFactory(sessionFactory.openSession());
        this.qComputer = QComputer.computer;
        this.qCompany = QCompany.company;
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
    }

    /**
     * @return List<Computer> The list of all Computer object from the DB
     */
    public List<Computer> listAll() {
<<<<<<< HEAD
        return this.jdbcTemplate.queryForList(SELECT_ALL_COMPUTERS, Computer.class);
=======
        return (List<Computer>) this.hibernateQueryFactory.select(qComputer).from(qComputer).fetch();
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
    }

    /**
     * @param computer the computer object to create in the DB
     */
    public void createComputer(Computer computer) {
<<<<<<< HEAD
        this.jdbcTemplate.update(CREATE_COMPUTER, preparedStatement -> {
                computerToPreparedStatement(computer, preparedStatement);
            });
=======
        //this.hibernateQueryFactory.update(qComputer.computer);
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
    }

    /**
     * @param computer the computer object to create in the DB
     */
    public void updateComputer(Computer computer) {
<<<<<<< HEAD
        this.jdbcTemplate.update(UPDATE_COMPUTER, preparedStatement -> {
                computerToPreparedStatement(computer, preparedStatement);
                preparedStatement.setInt(5, computer.getId());
            });
=======
        this.hibernateQueryFactory.update(qComputer)
        .where(qComputer.id.eq(computer.getId())).set(qComputer, computer).execute();
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
    }

    /**
     * @param ids the IDs of computer to delete from the DB
     */
    public void deleteComputer(int... ids) {
        for (int id : ids) {
<<<<<<< HEAD
            this.jdbcTemplate.update(DELETE_COMPUTER, id);
=======
            this.hibernateQueryFactory.delete(qComputer).where(qComputer.id.eq(id));
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
        }
    }

    /**
     * @param id the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id does not exist
     */
    public Optional<Computer> selectOne(int id) {
<<<<<<< HEAD
        Computer computer;
        try {
            computer = this.jdbcTemplate.query(SELECT_ONE_COMPUTER, (ResultSet resultSet, int row) -> computerMapper.resToComputer(resultSet), id).get(0);
        } catch (IndexOutOfBoundsException e) {
            computer = null;
        }
        return Optional.ofNullable(computer);
=======
        return Optional.ofNullable((Computer) this.hibernateQueryFactory.select(qComputer)
                .from(qComputer).where(qComputer.id.eq(id)).fetchOne());
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
    }

    /**
     * @param id the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id does not exist
     */
    public List<Integer> getComputersWithCompanyId(int id) {
<<<<<<< HEAD
        List<Integer> ids = this.jdbcTemplate.queryForList(SELECT_COMPUTERS_WITH_COMPANY, int.class);
        return ids;
=======
        return (List<Integer>) this.hibernateQueryFactory.select(qComputer.id)
                .from(qComputer).where(qComputer.manufactor.id.eq(id)).fetch();
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
    }

    /**
     * @param keywords The keywords of the search, can be empty
     * @return int number of computers
     */
    public int countAllComputer(String keywords) {
<<<<<<< HEAD
        String like = (keywords.length() > 0) ? " WHERE computer.name LIKE '%" + keywords + "%' OR company.name LIKE '%" + keywords + "%'" : "";
        String count = COUNT_ALL_COMPUTERS + like + ";";
        return this.jdbcTemplate.queryForObject(count, Integer.class);
=======
        return (int) this.hibernateQueryFactory.select(qComputer.id)
                .from(qComputer).leftJoin(qComputer.manufactor, qCompany)
                .where(qComputer.name.contains(keywords)
                        .or(qComputer.manufactor.name.contains(keywords)))
                .fetchCount();
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
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
        return (List<Computer>) this.hibernateQueryFactory.select(qComputer)
                                        .from(qComputer).leftJoin(qComputer.manufactor, qCompany)
                                        .offset(offset).limit(limit).fetch();
        //query = orderBy(query, sortBy, asc);
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
