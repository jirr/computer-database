package com.excilys.formation.cdb.service;

import java.time.LocalDate;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.CompanyDB;
import com.excilys.formation.cdb.persistence.ComputerDB;

public enum Validator {
    INSTANCE;

    /**
     * @param d1 first date to compare
     * @param d2 second date to compare
     * @throws Exception if the first date is after the second
     */
    protected void datesValidation(LocalDate d1, LocalDate d2) throws Exception {
        if (d1.compareTo(d2) > 0) {
            throw new Exception("Incompatibility of dates.");
        }
    }

    /**
     * @param name String to check
     * @throws Exception if there is not a least one char
     */
    protected void nameValidation(String name) throws Exception {
        if (name.compareTo("") == 0) {
            throw new Exception("Name is required.");
        }
    }

    /**
     * @param id The id to check
     * @return Computer The computer object with the id
     * @throws Exception if the id does not exist
     */
    protected Computer computerIdValidation(int id) throws Exception {
        if (ComputerDB.INSTANCE.selectOne(id).isPresent()) {
            return ComputerDB.INSTANCE.selectOne(id).get();
        } else {
            throw new Exception("Computer ID does not exist.");
        }
    }

    /**
     * @param id The id to check
     * @return Company The company object with the id
     * @throws Exception if the id does no exist
     */
    protected Company manufactorValidation(int id) throws Exception {
        if (CompanyDB.INSTANCE.selectOne(id).isPresent()) {
            return CompanyDB.INSTANCE.selectOne(id).get();
        } else {
            throw new Exception("Company ID does not exist.");
        }
    }
}