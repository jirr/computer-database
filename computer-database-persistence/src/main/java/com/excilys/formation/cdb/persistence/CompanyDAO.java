package com.excilys.formation.cdb.persistence;

import java.util.List;
import java.util.Optional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.QCompany;
import com.querydsl.jpa.hibernate.HibernateQueryFactory;

@Repository
public class CompanyDAO {

    private HibernateQueryFactory hibernateQueryFactory;
    private QCompany qCompany;
    
    @Autowired 
    public CompanyDAO(SessionFactory sessionFactory) {
        this.hibernateQueryFactory = new HibernateQueryFactory(sessionFactory.openSession());
        this.qCompany = QCompany.company;
    }

    /**
     * @param id of Company that should be in the DB
     * @return Optional<Company> contains the company, could be empty if the id does not exist
     */
    public Optional<Company> selectOne(int id) {
        return Optional.ofNullable((Company) this.hibernateQueryFactory.select(qCompany)
                .from(qCompany).where(qCompany.id.eq(id)).fetchOne());
    }

    /**
     * @return List<Company> The list of all companies
     */
    public List<Company> list() {
        return (List<Company>) this.hibernateQueryFactory.select(qCompany)
                                                .from(qCompany)
                                                .fetch();
    }
    
    /**
     * @return int number of companies
     */
    public int countAllCompany() {
        return (int) this.hibernateQueryFactory.select(qCompany.id)
                                       .from(qCompany)
                                       .fetchCount();
    }

    /**
     * @param limit index du dernier element
     * @param offset index du premier element
     * @return List<Company> The sublist of Company object from the DB
     */
    public List<Company> subList(int offset, int limit) {
        return (List<Company>) this.hibernateQueryFactory.select(qCompany)
                                                    .from(qCompany)
                                                    .offset(offset)
                                                    .limit(limit)
                                                    .fetch();
    }

    /**
     * @param id the ID of computer to delete from the DB
     */
    public void deleteCompany(int id) {
        this.hibernateQueryFactory.delete(qCompany).where(qCompany.id.eq(id)).execute();
    }
}
