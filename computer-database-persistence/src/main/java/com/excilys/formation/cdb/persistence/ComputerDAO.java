package com.excilys.formation.cdb.persistence;

import java.util.List;
import java.util.Optional;

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
        return (List<Computer>) new HibernateQuery<Company>(this.sessionFactory.openSession()).select(qComputer)
                .from(qComputer).fetch();
    }

    /**
     * @param computer the computer object to create in the DB
     */
    public void createComputer(Computer computer) {
        this.sessionFactory.openSession().save(computer);
    }

    /**
     * @param computer the computer object to create in the DB
     */
    public void updateComputer(Computer computer) {
        new HibernateUpdateClause(this.sessionFactory.openSession(), qComputer)
                .where(qComputer.id.eq(computer.getId()))
                .set(qComputer, computer).execute();
    }

    /**
     * @param ids the IDs of computer to delete from the DB
     */
    public void deleteComputer(int... ids) {
        for (int id : ids) {
            new HibernateDeleteClause(this.sessionFactory.openSession(), qComputer)
                    .where(qComputer.id.eq(id));
        }
    }

    /**
     * @param id the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id does not exist
     */
    public Optional<Computer> selectOne(int id) {
        return Optional.ofNullable((Computer) new HibernateQuery<Company>(this.sessionFactory.openSession())
                .select(qComputer).from(qComputer).where(qComputer.id.eq(id)).fetchOne());
    }

    /**
     * @param id the ID of Computer that should exist
     * @return Optional<Computer> contains the Computer, could be empty if the id does not exist
     */
    public List<Integer> getComputersWithCompanyId(int id) {
        return (List<Integer>) new HibernateQuery<Company>(this.sessionFactory.openSession())
                .select(qComputer.id).from(qComputer).where(qComputer.manufactor.id.eq(id)).fetch();
    }

    /**
     * @param keywords The keywords of the search, can be empty
     * @return int number of computers
     */
    public int countAllComputer(String keywords) {
        return (int) new HibernateQuery<Company>(this.sessionFactory.openSession()).select(qComputer.id)
                .from(qComputer).leftJoin(qComputer.manufactor, qCompany)
                .where(qComputer.name.contains(keywords)
                        .or(qComputer.manufactor.name.contains(keywords)))
                .fetchCount();
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
        return (List<Computer>) new HibernateQuery<Company>(this.sessionFactory.openSession()).select(qComputer)
                .from(qComputer).leftJoin(qComputer.manufactor, qCompany)
                .offset(offset).limit(limit)
                .fetch();
    }
}
