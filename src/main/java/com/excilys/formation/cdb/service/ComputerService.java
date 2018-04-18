package com.excilys.formation.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDAO;

/**
 * @author jirr
 *
 */
@Service
public class ComputerService {

    private ComputerDAO computerDAO;
    private Validator validator;

    @Autowired
    public ComputerService(ComputerDAO computerDAO, Validator validator) {
        this.computerDAO = computerDAO;
        this.validator = validator;
    }

    /**
     * @param offset index of the first element
     * @param numberToDisplay number of object to display
     * @param keywords The keywords for the search, can be empty
     * @param sortBy Name of the column to sort on
     * @param asc Is the sort asc or desc
     * @return List<Computer> the sublist (page) of computers
     * @throws ServiceException if failure in persistence execution
     */
    public List<Computer> subListComputer(int offset, int numberToDisplay, String keywords, String sortBy, boolean asc) {
        return computerDAO.subList(offset, numberToDisplay, keywords, sortBy, asc);
    }

    /**
     * @param keywords The keywords for the search, can be empty
     * @return int number of companies
     */
    public int countAllComputers(String keywords) {
        return computerDAO.countAllComputer(keywords);
    }

    /**
     * @param id The id of computer
     * @return Computer with the right id
     * @throws ServiceException if the id does not exist
     */
    public Computer selectOne(int id) throws ServiceException {
        return validator.computerIdValidation(id);
    }

    /**
     * @param computer The computer object to create
     * @return String validation text
     * @throws ServiceException if the creation becomes wild
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
        computerDAO.createComputer(computer);
        return "New computer added to database.";
    }

    /**
     * @param computer The computer object to update
     * @return String validation text
     * @throws ServiceException if the updating becomes wild
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
        computerDAO.updateComputer(computer);
        return "Computer " + computer.getId() + " updated.";
    }

    /**
     * @param ids The ids of the computer to delete from the DB
     * @return String validation test
     * @throws ServiceException if the deleting becomes wild
     */
    @Transactional(rollbackFor = Exception.class)
    public String deleteComputer(int... ids) throws ServiceException {
        String res = "";
        for (int id : ids) {
            validator.computerIdValidation(id);
            computerDAO.deleteComputer(id);
            res += "Computer " + id + " removed from database./n";
        }
        return res;
    }
}
