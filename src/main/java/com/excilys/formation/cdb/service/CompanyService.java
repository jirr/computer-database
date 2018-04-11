package com.excilys.formation.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;
import com.excilys.formation.cdb.persistence.DBException;

/**
 * @author jirr
 *
 */
@Service
public class CompanyService {
    
    @Autowired
    private CompanyDB companyDB;

    @Autowired
    private Validator validator;

    /**
     * @param offset index of the first element
     * @param numberToDisplay number of object to display
     * @return List<Company> the sublist (page) of companies
     * @throws ServiceException if failure in persistence execution
     */
    public List<Company> subListCompany(int offset, int numberToDisplay) throws ServiceException {
        try {
            return companyDB.subList(offset, numberToDisplay);
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
            return companyDB.list();
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
            return companyDB.countAllCompany();
        } catch (DBException e) {
            throw new ServiceException("Fail in persistence execution.");
        }
    }

    /**
     * @param id The id of company
     * @return Company with the right id
     * @throws ServiceException if the id does not exist
     */
    public Company getCompany(int id) throws ServiceException {
        return validator.manufactorValidation(id);
    }

    /**
     * @param id The id of the computer to delete from the DB
     * @return String validation test
     * @throws ServiceException if the deleting becomes wild
     */
    public String deleteCompany(int id) throws ServiceException {
        validator.manufactorValidation(id);
        try {
            companyDB.deleteCompany(id);
        } catch (DBException e) {
            throw new ServiceException("Problem encounter in database during deletion.");
        }
        return "Companny " + id + " removed from database.";
    }
}
