package com.excilys.formation.cdb.validator;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.model.User;
import com.excilys.formation.cdb.persistence.dao.CompanyDAO;
import com.excilys.formation.cdb.persistence.dao.ComputerDAO;
import com.excilys.formation.cdb.persistence.dao.UserDAO;
import com.excilys.formation.cdb.service.ServiceException;

@Component
public class Validator {

    private UserDAO userDAO;
    private CompanyDAO companyDAO;
    private ComputerDAO computerDAO;

    @Autowired
    public Validator(UserDAO userDAO, CompanyDAO companyDAO, ComputerDAO computerDAO) {
        this.userDAO = userDAO;
        this.companyDAO = companyDAO;
        this.computerDAO = computerDAO;
    }

    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * @param date1 first date to compare
     * @param date2 second date to compare
     * @throws ServiceException if the first date is after the second
     */
    public void datesCompatibilityValidation(LocalDate date1, LocalDate date2) throws ServiceException {
        if (date2.isBefore(date1)) {
            LOGGER.error("Date introduced > date discontinued");
            throw new ServiceException("Incompatibility of dates.");
        }
    }

    /**
     * @param date date to check
     * @throws ServiceException if the date is after today or before the Altair 8800
     */
    public void dateValidation(LocalDate date) throws ServiceException {
        if (date.isAfter(LocalDate.now())) {
            LOGGER.error("The date ({}) must not be greater than today.", date.toString());
            throw new ServiceException("The date must not be greater than today.");
        }
        if (date.isBefore(LocalDate.of(1975, 01, 01))) {
            LOGGER.error("The date ({}) must not be before 01/01/1975.", date.toString());
            throw new ServiceException("The computer can't be a Turing machine!");
        }
    }

    /**
     * @param name String to check
     * @throws ServiceException if there is not a least one char
     */
    public void nameValidation(String name) throws ServiceException {
        if (name.compareTo("") == 0) {
            LOGGER.error("No name.");
            throw new ServiceException("Name is required.");
        }
    }

    /**
     * @param id The id to check
     * @return Computer The computer object with the id
     * @throws ServiceException if the id does not exist
     */
    public Computer computerIdValidation(int id) throws ServiceException {
        if (computerDAO.selectOne(id).isPresent()) {
            return computerDAO.selectOne(id).get();
        } else {
            LOGGER.error("Computer ID does not exist.");
            throw new ServiceException("Computer ID does not exist.");
        }
    }

    /**
     * @param id The id to check
     * @return Company The company object with the id
     * @throws ServiceException if the id does no exist
     */
    public Company manufactorValidation(int id) throws ServiceException {
        if (companyDAO.selectOne(id).isPresent()) {
            return companyDAO.selectOne(id).get();
        } else {
            LOGGER.error("Company ID does not exist: {}", id);
            throw new ServiceException("Company ID does not exist.");
        }
    }

    /**
     * @param id The id to check
     * @return Company The company object with the id
     * @throws ServiceException if the id does no exist
     */
    public User userValidation(String name) throws ServiceException {
        if (userDAO.selectOne(name).isPresent()) {
            return userDAO.selectOne(name).get();
        } else {
            LOGGER.error("User name does not exist: {}", name);
            throw new ServiceException("User name does not exist.");
        }
    }
}