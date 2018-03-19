package com.excilys.formation.cdb.service;

import java.util.List;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.persistence.CompanyDB;

public enum CompanyService {
    INSTANCE;

    private List<Company> list = CompanyDB.INSTANCE.list();

    /**
     * @param from index of the first computer to print
     * @param to index of the last computer to print
     * @return String the String version of the sublist
     */
    public String listCompany(int from, int to) {
        String res = "";
        if (to > list.size()) {
            to = list.size();
        }
        for (Company c : list.subList(from, to)) {
            res += c.toString() + "\n";
        }
        return res;
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
