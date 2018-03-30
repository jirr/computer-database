package com.excilys.formation.cdb.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CompanyDB;
import com.excilys.formation.cdb.persistence.ComputerDB;
import com.excilys.formation.cdb.persistence.DBException;

public enum Validator {
    INSTANCE;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * @param date1 first date to compare
     * @param date2 second date to compare
     * @throws ServiceException if the first date is after the second
     */
    protected void datesCompatibilityValidation(LocalDate date1, LocalDate date2) throws ServiceException {
        if (date2.isBefore(date1)) {
            logger.error("Date introduced > date discontinued");
            throw new ServiceException("Incompatibility of dates.");
        }
    }

    /**
     * @param date date to check
     * @throws ServiceException if the date is after today or before the Altair 8800
     */
    protected void dateValidation(LocalDate date) throws ServiceException {
        if (date.isAfter(LocalDate.now())) {
            logger.error("The date ({}) must not be greater than today.", date.toString());
            throw new ServiceException("The date must not be greater than today.");
        }
        if (date.isBefore(LocalDate.of(1975, 01, 01))) {
            logger.error("The date ({}) must not be before 01/01/1975.", date.toString());
            throw new ServiceException("The computer can't be a Turing machine!");
        }
    }

    /**
     * @param name String to check
     * @throws ServiceException if there is not a least one char
     */
    protected void nameValidation(String name) throws ServiceException {
        if (name.compareTo("") == 0) {
            logger.error("No name.");
            throw new ServiceException("Name is required.");
        }
    }

    /**
     * @param id The id to check
     * @return Computer The computer object with the id
     * @throws ServiceException if the id does not exist
     */
    protected Computer computerIdValidation(int id) throws ServiceException {
        try {
            if (ComputerDB.INSTANCE.selectOne(id).isPresent()) {
                return ComputerDB.INSTANCE.selectOne(id).get();
            } else {
                logger.error("Computer ID does not exist.");
                throw new ServiceException("Computer ID does not exist.");
            }
        } catch (DBException e) {
            logger.error("Problem encounter in database: {}", e.getMessage(), e);
            throw new ServiceException("Problem encounter in database.");
        }
    }

    /**
     * @param id The id to check
     * @return Company The company object with the id
     * @throws ServiceException if the id does no exist
     */
    protected Company manufactorValidation(int id) throws ServiceException {
        try {
            if (CompanyDB.INSTANCE.selectOne(id).isPresent()) {
                return CompanyDB.INSTANCE.selectOne(id).get();
            } else {
                logger.error("Company ID does not exist: {}", id);
                throw new ServiceException("Company ID does not exist.");
            }
        } catch (DBException e) {
            logger.error("Problem encounter in database: {}", e.getMessage(), e);
            throw new ServiceException("Problem encounter in database.");
        }
    }
}