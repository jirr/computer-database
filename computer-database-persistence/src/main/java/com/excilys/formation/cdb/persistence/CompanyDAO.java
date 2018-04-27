package com.excilys.formation.cdb.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.QCompany;
import com.querydsl.jpa.hibernate.HibernateDeleteClause;
import com.querydsl.jpa.hibernate.HibernateQuery;

@Repository
public class CompanyDAO {

    private SessionFactory sessionFactory;
    private QCompany qCompany;
    
    @Autowired 
    public CompanyDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.qCompany = QCompany.company;
    }

    /**
     * @param id of Company that should be in the DB
     * @return Optional<Company> contains the company, could be empty if the id does not exist
     */
    public Optional<Company> selectOne(int id) {
        Session session = this.sessionFactory.openSession();
        Company company = new HibernateQuery<Company>(session).select(qCompany)
                .from(qCompany).where(qCompany.id.eq(id)).fetchOne();
        session.close();
        return Optional.ofNullable(company);
    }

    /**
     * @return List<Company> The list of all companies
     */
    public List<Company> list() {
        Session session = this.sessionFactory.openSession();
        List<Company> companyList = new HibernateQuery<Company>(session).select(qCompany)
                .from(qCompany).fetch();
        session.close();
        return companyList;
    }
    
    /**
     * @return int number of companies
     */
    public int countAllCompany() {
        Session session = this.sessionFactory.openSession();
        int total = (int) new HibernateQuery<Company>(session).select(qCompany.id)
                .from(qCompany).fetchCount();
        session.close();
        return total;
    }

    /**
     * @param limit index du dernier element
     * @param offset index du premier element
     * @return List<Company> The sublist of Company object from the DB
     */
    public List<Company> subList(int offset, int limit) {
        Session session = this.sessionFactory.openSession();
        List<Company> companyList = new HibernateQuery<Company>(session).select(qCompany)
                .from(qCompany).offset(offset).limit(limit).fetch();
        session.close();
        return companyList;
    }

    /**
     * @param id the ID of computer to delete from the DB
     */
    public void deleteCompany(int id) {
        Session session = this.sessionFactory.openSession();
        new HibernateDeleteClause(session, qCompany)
                .where(qCompany.id.eq(id)).execute();
        session.close();
    }
}
