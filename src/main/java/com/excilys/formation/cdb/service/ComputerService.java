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

    private List<Computer> list = ComputerDB.INSTANCE.list();

    /**
     * @param from index of the first computer to print
     * @param to index of the last computer to print
     * @return String the String version of the sublist
     */
    public String listComputer(int from, int to) {
        String res = "";
        if (to > list.size()) {
            to = list.size();
        }
        for (Computer p : list.subList(from, to)) {
            res += p.toString() + "\n";
        }
        return res;
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
