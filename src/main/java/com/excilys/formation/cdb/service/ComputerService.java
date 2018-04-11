package com.excilys.formation.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDB;
import com.excilys.formation.cdb.persistence.DBException;

/**
 * @author jirr
 *
 */
@Service
public class ComputerService {

    @Autowired
    private ComputerDB computerDB;

    @Autowired
    private Validator validator;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * @param offset index of the first element
     * @param numberToDisplay number of object to display
     * @return List<Computer> the sublist (page) of computers
     * @throws ServiceException if failure in persistence execution
     */
    public List<Computer> subListComputer(int offset, int numberToDisplay, String keywords, String sortBy, boolean asc) throws ServiceException {
        try {
            return computerDB.subList(offset, numberToDisplay, keywords, sortBy, asc);
        } catch (DBException e) {
            logger.error("Fail in persistence execution: {}", e.getMessage(), e);
            throw new ServiceException("Fail in persistence execution.");
        }
    }

    /**
     * @return int number of companies
     * @throws ServiceException if failure in persistence execution
     */
    public int countAllComputers(String keywords) throws ServiceException {
        try {
            return computerDB.countAllComputer(keywords);
        } catch (DBException e) {
            logger.error("Fail in persistence execution: {}", e.getMessage(), e);
            throw new ServiceException("Fail in persistence execution.");
        }
    }

    /**
     * @param id The id of computer
     * @return Computer with the right id
     * @throws Exception if the id does not exist
     */
    public Computer selectOne(int id) throws ServiceException {
        return validator.computerIdValidation(id);
    }

    /**
     * @param computer The computer object to create
     * @return String validation text
     * @throws Exception if the creation becomes wild
     */
    public String createComputer(Computer computer) throws ServiceException {
        validator.nameValidation(computer.getName());
        if (computer.getDateIntroduced().isPresent() && computer.getDateDiscontinued().isPresent()) {
            validator.datesCompatibilityValidation(computer.getDateIntroduced().get(), computer.getDateDiscontinued().get());
        }
        if (computer.getDateIntroduced().isPresent()) {
            validator.dateValidation(computer.getDateIntroduced().get());
        }
        if (computer.getDateDiscontinued().isPresent()) {
            validator.dateValidation(computer.getDateDiscontinued().get());
        }
        if (computer.getManufactor().isPresent()) {
            validator.manufactorValidation(computer.getManufactor().get().getId());
        }
        try {
            computerDB.createComputer(computer);
        } catch (DBException e) {
            logger.error("Problem with database: {}", e.getMessage(), e);
            throw new ServiceException("Problem encounter in database during creation.");
        }
        return "New computer added to database.";
    }

    /**
     * @param computer The computer object to update
     * @return String validation text
     * @throws Exception if the updating becomes wild
     */
    public String updateComputer(Computer computer) throws ServiceException {
        validator.computerIdValidation(computer.getId());
        validator.nameValidation(computer.getName());
        if (computer.getDateIntroduced().isPresent() && computer.getDateDiscontinued().isPresent()) {
            validator.datesCompatibilityValidation(computer.getDateIntroduced().get(), computer.getDateDiscontinued().get());
        }
        if (computer.getManufactor().isPresent()) {
            validator.manufactorValidation(computer.getManufactor().get().getId());
        }
        try {
            computerDB.updateComputer(computer);
        } catch (DBException e) {
            logger.error("Problem with database: {}", e.getMessage(), e);
            throw new ServiceException("Problem encounter in database during update.");
        }
        return "Computer " + computer.getId() + " updated.";
    }

    /**
     * @param id The id of the computer to delete from the DB
     * @return String validation test
     * @throws Exception if the deleting becomes wild
     */
    public String deleteComputer(int id) throws ServiceException {
        validator.computerIdValidation(id);
        try {
            computerDB.deleteComputer(id);
        } catch (DBException e) {
            logger.error("Problem with database: {}", e.getMessage(), e);
            throw new ServiceException("Problem encounter in database during deletion.");
        }
        return "Computer " + id + " removed from database.";
    }
}
