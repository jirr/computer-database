package com.excilys.formation.cdb.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.QCompany;
import com.excilys.formation.cdb.model.QComputer;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateQuery;
import com.querydsl.jpa.hibernate.HibernateUpdateClause;

@Repository
public class ComputerDAO {

    private SessionFactory sessionFactory;
    private QComputer qComputer;

    @Autowired 
    public ComputerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.qComputer = QComputer.computer;
    }

    /**
     * @return List<Computer> The list of all Computer object from the DB
     */
    public List<Computer> listAll() {
        Session session = sessionFactory.openSession();
        HibernateQuery<Computer> hibernateQuery = new HibernateQuery<>(session);
        List<Computer> computerList = hibernateQuery.select(qComputer)
                                                    .from(qComputer)
                                                    .fetch();
        session.close();
        return computerList;
    }

    /**
     * @param computer the computer object to create in the DB
     */
    public void createComputer(Computer computer) {
        Session session = sessionFactory.openSession();
        session.save(computer);
        session.close();
    }

    /**
     * @param computer the computer object to create in the DB
     */
    public void updateComputer(Computer computer) {
        Session session = sessionFactory.openSession();
        new HibernateUpdateClause(session, qComputer).where(qComputer.id.eq(computer.getId()))
                                 .set(qComputer.name, computer.getName())
                                 .set(qComputer.dateIntroduced, computer.getDateIntroduced())
                                 .set(qComputer.dateDiscontinued, computer.getDateDiscontinued())
                                 .set(qComputer.manufactor, computer.getManufactor())).execute();
        session.close();
    }

    /**
     * @param ids the IDs of computer to delete from the DB
     */
    public void deleteComputer(int... ids) {
        Session session = sessionFactory.openSession();
        for (int id : ids) {
            new HibernateDeleteClause(session, qComputer).where(qComputer.id.eq(id)).execute();
        }
        session.close();
    }

    /**
     * @param id the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id does not exist
     */
    public Optional<Computer> selectOne(int id) {
        Session session = sessionFactory.openSession();
        HibernateQuery<Computer> hibernateQuery = new HibernateQuery<>(session);
        Computer computer = hibernateQuery.select(qComputer)
                                        .from(qComputer)
                                        .where(qComputer.id.eq(id))
                                        .fetchOne();
        session.close();
        return Optional.ofNullable(computer);
    }

    /**
     * @param id the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id does not exist
     */
    public List<Integer> getComputersWithCompanyId(int id) {
        Session session = sessionFactory.openSession();
        HibernateQuery<Integer> hibernateQuery = new HibernateQuery<>(session);
        List<Integer> ids = hibernateQuery.select(qComputer.id)
                                        .from(qComputer)
                                        .where(qComputer.manufactor.id.eq(id))
                                        .fetch();
        session.close();
        return ids;
    }

    /**
     * @param keywords The keywords of the search, can be empty
     * @return int number of computers
     */
    public int countAllComputer(String keywords) {
        Session session = sessionFactory.openSession();
        HibernateQuery<Integer> hibernateQuery = new HibernateQuery<>(session);
        int number = (int) hibernateQuery.select(qComputer.id)
                                       .from(qComputer)
                                       .where(qComputer.name.like(keywords).or(qComputer.manufactor.name.like(keywords)))
                                       .fetchCount();
        session.close();
        return number;
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
}
