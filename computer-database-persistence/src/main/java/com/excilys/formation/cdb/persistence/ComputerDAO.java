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

@Repository
public class ComputerDAO {
    private SessionFactory sessionFactory;
    private QComputer qComputer;
    private QCompany qCompany;

    @Autowired 
    public ComputerDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.qComputer = QComputer.computer;
        this.qCompany = QCompany.company;
    }

    /**
     * @return List<Computer> The list of all Computer object from the DB
     */
    public List<Computer> listAll() {
        Session session = this.sessionFactory.openSession();
        List<Computer> computerList = new HibernateQuery<Company>(session).select(qComputer)
                .from(qComputer).fetch();
        session.close();
        return computerList;
    }

    /**
     * @param computer the computer object to create in the DB
     * @throws Exception 
     */
    public void createComputer(Computer computer) {
        Session session = this.sessionFactory.openSession();
        session.save(computer);
        session.flush();
        session.close();
    }

    /**
     * @param computer the computer object to create in the DB
     */
    public void updateComputer(Computer computer) {
        Session session = this.sessionFactory.openSession();
        session.update(computer);
        session.flush();
        session.close();
    }

    /**
     * @param ids the IDs of computer to delete from the DB
     */
    public void deleteComputer(int... ids) {
        Session session = this.sessionFactory.openSession();
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
        Session session = this.sessionFactory.openSession();
        Computer computer = new HibernateQuery<Company>(session).select(qComputer)
                .from(qComputer).where(qComputer.id.eq(id)).fetchOne();
        session.close();
        return Optional.ofNullable(computer);
    }

    /**
     * @param id the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id does not exist
     */
    public List<Integer> getComputersWithCompanyId(int id) {
        Session session = this.sessionFactory.openSession();
        List<Integer> idList = new HibernateQuery<Company>(session)
                .select(qComputer.id).from(qComputer).where(qComputer.manufactor.id.eq(id)).fetch();
        session.close();
        return idList;
    }

    /**
     * @param keywords The keywords of the search, can be empty
     * @return int number of computers
     */
    public int countAllComputer(String keywords) {
        Session session = this.sessionFactory.openSession();
        int total = (int)new HibernateQuery<Company>(session).select(qComputer.id)
                .from(qComputer).leftJoin(qComputer.manufactor, qCompany)
                .where(qComputer.name.contains(keywords)
                        .or(qComputer.manufactor.name.contains(keywords)))
                .fetchCount();
        session.close();
        return total;
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
        Session session = this.sessionFactory.openSession();
        List<Computer> computerList = new HibernateQuery<Company>(session).select(qComputer)
                .from(qComputer).leftJoin(qComputer.manufactor, qCompany)
                .offset(offset).limit(limit)
                .fetch();
        session.close();
        return computerList;
    }
}
