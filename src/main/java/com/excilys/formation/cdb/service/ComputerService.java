package com.excilys.formation.cdb.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDB;
import com.excilys.formation.cdb.persistence.DBException;

/**
 * @author jirr
 *
 */
public enum ComputerService {
    INSTANCE;

    private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

    /**
     * @param offset index of the first element
     * @param numberToDisplay number of object to display
     * @return List<Computer> the sublist (page) of computers
     * @throws ServiceException if failure in persistence execution
     */
    public List<Computer> subListComputer(int offset, int numberToDisplay) throws ServiceException {
        try {
            return ComputerDB.INSTANCE.subList(offset, numberToDisplay);
        } catch (DBException e) {
            logger.error("Fail in persistence execution: {}", e.getMessage(), e);
            throw new ServiceException("Fail in persistence execution.");
        }
    }

    /**
     * @return int number of companies
     * @throws ServiceException if failure in persistence execution
     */
    public int countAllComputers() throws ServiceException {
        try {
            return ComputerDB.INSTANCE.countAllComputer();
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
    public String selectOne(int id) throws Exception {
        return Validator.INSTANCE.computerIdValidation(id).toString();
    }

    /**
     * @param computer The computer object to create
     * @return String validation text
     * @throws Exception if the creation becomes wild
     */
    public String createComputer(Computer computer) throws ServiceException {
        Validator.INSTANCE.nameValidation(computer.getName());
        if (computer.getDateIntroduced().isPresent() && computer.getDateDiscontinued().isPresent()) {
            Validator.INSTANCE.datesValidation(computer.getDateIntroduced().get(), computer.getDateDiscontinued().get());
        }
        if (computer.getDateIntroduced().isPresent()) {
            Validator.INSTANCE.dateValidation(computer.getDateIntroduced().get());
        }
        if (computer.getDateDiscontinued().isPresent()) {
            Validator.INSTANCE.dateValidation(computer.getDateDiscontinued().get());
        }
        if (computer.getManufactor().isPresent()) {
            Validator.INSTANCE.manufactorValidation(computer.getManufactor().get().getId());
        }
        try {
            ComputerDB.INSTANCE.createComputer(computer);
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
    public String updateComputer(Computer computer) throws Exception {
        Validator.INSTANCE.computerIdValidation(computer.getId());
        Validator.INSTANCE.nameValidation(computer.getName());
        if (computer.getDateIntroduced().isPresent() && computer.getDateDiscontinued().isPresent()) {
            Validator.INSTANCE.datesValidation(computer.getDateIntroduced().get(), computer.getDateDiscontinued().get());
        }
        if (computer.getManufactor().isPresent()) {
            Validator.INSTANCE.manufactorValidation(computer.getManufactor().get().getId());
        }
        ComputerDB.INSTANCE.updateComputer(computer);
        return "Computer " + computer.getId() + " updated.";
    }

    /**
     * @param id The id of the computer to delete from the DB
     * @return String validation test
     * @throws Exception if the deleting becomes wild
     */
    public String deleteComputer(int id) throws Exception {
        Validator.INSTANCE.computerIdValidation(id);
        ComputerDB.INSTANCE.deleteComputer(id);
        return "Computer " + id + " removed from database.";
    }
}
