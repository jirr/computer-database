package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;
import com.excilys.formation.cdb.persistence.DBException;

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
     * @throws ServiceException if failure in persistence execution
     */
    public List<Company> subListCompany(int offset, int numberToDisplay) throws ServiceException {
        try {
            return CompanyDB.INSTANCE.subList(offset, numberToDisplay);
        } catch (DBException e) {
            throw new ServiceException("Fail in persistence execution.");
        }
    }

    /**
     * @return List<Company> the list of all companies
     * @throws ServiceException if failure in persistence execution
     */
    public List<Company> listAllCompany() throws ServiceException {
        try {
            return CompanyDB.INSTANCE.list();
        } catch (DBException e) {
            throw new ServiceException("Fail in persistence execution.");
        }
    }

    /**
     * @return int number of companies
     * @throws ServiceException if failure in persistence execution
     */
    public int countAllCompanies() throws ServiceException {
        try {
            return CompanyDB.INSTANCE.countAllCompany();
        } catch (DBException e) {
            throw new ServiceException("Fail in persistence execution.");
        }
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
