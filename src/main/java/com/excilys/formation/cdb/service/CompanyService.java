package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;

public enum CompanyService {
    INSTANCE;

    /**
     * @param pageNumber index of the current page
     * @param numberToDisplay number of object to display
     * @return String the String version of the sublist
     */
    public String subListCompany(int pageNumber, int numberToDisplay) {
        String resultStr = "";
        int limit = (pageNumber == 0) ? 1 : pageNumber*numberToDisplay;
        List<Company> list = CompanyDB.INSTANCE.subList(numberToDisplay, limit);
        for (Company company : list) {
            resultStr += company.toString() + "\n";
        }
        return resultStr;
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
