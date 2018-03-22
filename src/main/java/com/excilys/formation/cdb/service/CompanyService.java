package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;

/**
 * @author jirr
 *
 */
public enum CompanyService {
    INSTANCE;

    /**
     * @param offset index of the first element
     * @param numberToDisplay number of object to display
     * @return List<Company> the sublist (page) of companies
     */
    public List<Company> subListCompany(int offset, int numberToDisplay) {
        return CompanyDB.INSTANCE.subList(offset, numberToDisplay);
    }

    /**
     * @return List<Company> the list of all companies
     */
    public List<Company> listAllCompany() {
        return CompanyDB.INSTANCE.list();
    }
    
    /**
     * @return int number of companies
     */
    public int countAllCompanies() {
        return CompanyDB.INSTANCE.countAllCompany();
    }

    /**
     * @param id The id of company
     * @return Company with the right id
     * @throws Exception if the id does not exist
     */
    public Company getCompany(int id) throws Exception {
        return Validator.INSTANCE.manufactorValidation(id);
    }
}
