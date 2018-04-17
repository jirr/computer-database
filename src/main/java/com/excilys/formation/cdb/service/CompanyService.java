package com.excilys.formation.cdb.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDAO;
import com.excilys.formation.cdb.persistence.ComputerDAO;

/**
 * @author jirr
 *
 */
@Service
public class CompanyService {

    private CompanyDAO companyDAO;
    private ComputerDAO computerDAO;
    private Validator validator;

    @Autowired
    public CompanyService(CompanyDAO companyDAO, ComputerDAO computerDAO, Validator validator) {
        this.companyDAO = companyDAO;
        this.computerDAO = computerDAO;
        this.validator = validator;
    }

    /**
     * @param offset index of the first element
     * @param numberToDisplay number of object to display
     * @return List<Company> the sublist (page) of companies
     * @throws ServiceException if failure in persistence execution
     */
    public List<Company> subListCompany(int offset, int numberToDisplay) {
        return companyDAO.subList(offset, numberToDisplay);
    }

    /**
     * @return List<Company> the list of all companies
     * @throws ServiceException if failure in persistence execution
     */
    public List<Company> listAllCompany() {
        return companyDAO.list();
    }

    /**
     * @return int number of companies
     * @throws ServiceException if failure in persistence execution
     */
    public int countAllCompanies() {
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
    @Transactional(rollbackFor = Exception.class)
    public String deleteCompany(int idCompany) throws ServiceException {
        validator.manufactorValidation(idCompany);
        for (int idComputer : computerDAO.getComputersWithCompanyId(idCompany)) {
            computerDAO.deleteComputer(idComputer);
        }
        companyDAO.deleteCompany(idCompany);
        return "Companny " + idCompany + " removed from database.";
    }
}
