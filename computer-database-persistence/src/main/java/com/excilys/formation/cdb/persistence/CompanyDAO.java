package com.excilys.formation.cdb.persistence;

import java.sql.ResultSet;
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

import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.model.Company;
<<<<<<< HEAD
=======
import com.excilys.formation.cdb.model.QCompany;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)

/**
 * @author jirr
 *
 */
@Repository
public class CompanyDAO {

<<<<<<< HEAD
    private final String SELECT_ALL_COMPANIES = "SELECT ca.id as caId, ca.name as caName FROM company ca";
    private final String COUNT_ALL_COMPANIES = "SELECT COUNT(computer.id) FROM computer LEFT JOIN company ON company.id = computer.company_id";
    private final String SELECT_SOME_COMPANIES = "SELECT ca.id as caId, ca.name as caName FROM company ca LIMIT ? OFFSET ?;";
    private final String SELECT_ONE_COMPANY = "SELECT ca.id as caId, ca.name as caName FROM company ca WHERE ca.id = ?;";
    private final String DELETE_ONE_COMPANY = "DELETE FROM computer WHERE id=?;";

    private JdbcTemplate jdbcTemplate;
    private CompanyMapper companyMapper;

    @Autowired
    public CompanyDAO(DataSource dataSource, CompanyMapper companyMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.companyMapper = companyMapper;
=======
    private HibernateQueryFactory hibernateQueryFactory;
    private QCompany qCompany;
    
    @Autowired 
    public CompanyDAO(SessionFactory sessionFactory) {
        this.hibernateQueryFactory = new HibernateQueryFactory(sessionFactory.openSession());
        this.qCompany = QCompany.company;
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
    }

    /**
     * @param id of Company that should be in the DB
     * @return Optional<Company> contains the company, could be empty if the id does not exist
     * @throws DBException if can't reach the database
     */
    public Optional<Company> selectOne(int id) {
<<<<<<< HEAD
        Company company;
        try {
            company = this.jdbcTemplate.query(SELECT_ONE_COMPANY, (ResultSet resultSet, int row) -> companyMapper.resToCompany(resultSet), id).get(0);
        } catch (IndexOutOfBoundsException e) {
            company = null;
        }
        return Optional.ofNullable(company);
=======
        return Optional.ofNullable((Company) this.hibernateQueryFactory.select(qCompany)
                .from(qCompany).where(qCompany.id.eq(id)).fetchOne());
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
    }

    /**
     * @return List<Company>
     * @throws DBException if can't reach the database
     */
    public List<Company> list() {
<<<<<<< HEAD
        return this.jdbcTemplate.query(SELECT_ALL_COMPANIES, (resultSet, row) -> companyMapper.resToCompany(resultSet));
=======
        return (List<Company>) this.hibernateQueryFactory.select(qCompany).from(qCompany).fetch();
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
    }

    /**
     * @return int number of companies
     */
    public int countAllCompany() {
<<<<<<< HEAD
        return this.jdbcTemplate.queryForObject(COUNT_ALL_COMPANIES, Integer.class);
=======
        return (int) this.hibernateQueryFactory.select(qCompany.id).from(qCompany).fetchCount();
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
    }

    /**
     * @param limit index du dernier element
     * @param offset index du premier element
     * @return List<Company> The sublist of Company object from the DB
     * @throws DBException if can't reach the database
     */
    public List<Company> subList(int offset, int limit) {
<<<<<<< HEAD
        return this.jdbcTemplate.queryForList(SELECT_SOME_COMPANIES, new Object[] {limit, offset}, Company.class);
=======
        return (List<Company>) this.hibernateQueryFactory.select(qCompany)
                .from(qCompany).offset(offset).limit(limit).fetch();
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
    }

    /**
     * @param id the ID of computer to delete from the DB
     * @throws DBException if can't reach the database
     */
    public void deleteCompany(int id) {
<<<<<<< HEAD
        this.jdbcTemplate.update(DELETE_ONE_COMPANY, new Object[] {id});
=======
        this.hibernateQueryFactory.delete(qCompany).where(qCompany.id.eq(id));
>>>>>>> 7dd1918... [FEAT] (QueryDSL) Now working (without OrderBy)
    }
}
