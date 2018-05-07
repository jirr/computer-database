package com.excilys.formation.cdb.persistence.dao;

import java.util.Optional;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.excilys.formation.cdb.model.QUser;
import com.excilys.formation.cdb.model.User;
import com.querydsl.jpa.hibernate.HibernateQuery;

@Repository
public class UserDAO {
    
    private SessionFactory sessionFactory;
    private QUser qUser;
    
    @Autowired 
    public UserDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.qUser = QUser.user;
    }

    /**
     * @param name of Company that should be in the DB
     * @return Optional<User> contains the company, could be empty if the id does not exist
     */
    public Optional<User> selectOne(String name) {
        return Optional.ofNullable(new HibernateQuery<User>(this.sessionFactory.openSession())
                .select(qUser).from(qUser).where(qUser.username.eq(name)).fetchOne());
    }
}
