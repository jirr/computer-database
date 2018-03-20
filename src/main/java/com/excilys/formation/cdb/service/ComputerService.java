package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDB;

/**
 * @author jirr
 *
 */
public enum ComputerService {
    INSTANCE;

    /**
     * @param pageNumber index of the current page
     * @param numberToDisplay number of object to display
     * @return String the String version of the sublist
     */
    public String subListComputer(int pageNumber, int numberToDisplay) {
        String resultStr = "";
        int limit = (pageNumber == 0) ? 1 : pageNumber*numberToDisplay;
        List<Computer> list = ComputerDB.INSTANCE.subList(numberToDisplay, limit);
        for (Computer computer : list) {
            resultStr += computer.toString() + "\n";
        }
        return resultStr;
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
    public String createComputer(Computer computer) throws Exception {
        Validator.INSTANCE.nameValidation(computer.getName());
        Validator.INSTANCE.datesValidation(computer.getDateIntroduced(), computer.getDateDiscontinued());
        Validator.INSTANCE.manufactorValidation(computer.getManufactor().getId());
        ComputerDB.INSTANCE.createComputer(computer);
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
        Validator.INSTANCE.datesValidation(computer.getDateIntroduced(), computer.getDateDiscontinued());
        Validator.INSTANCE.manufactorValidation(computer.getManufactor().getId());
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