package com.excilys.formation.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDAO;

/**
 * @author jirr
 *
 */
@Service
public class CompanyService {

    @Autowired
    private CompanyDAO companyDAO;

    @Autowired
    private Validator validator;

    /**
     * @param offset index of the first element
     * @param numberToDisplay number of object to display
     * @return List<Company> the sublist (page) of companies
     * @throws ServiceException if failure in persistence execution
     */
    public List<Company> subListCompany(int offset, int numberToDisplay) throws ServiceException {
        return companyDAO.subList(offset, numberToDisplay);
    }

    /**
     * @return List<Company> the list of all companies
     * @throws ServiceException if failure in persistence execution
     */
    public List<Company> listAllCompany() throws ServiceException {
        return companyDAO.list();
    }

    /**
     * @return int number of companies
     * @throws ServiceException if failure in persistence execution
     */
    public int countAllCompanies() throws ServiceException {
        return companyDAO.countAllCompany();
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
        companyDAO.deleteCompany(id);
        return "Companny " + id + " removed from database.";
    }
}
