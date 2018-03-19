package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.excilys.formation.cdb.model.Company;

/**
 * @author jirr
 *
 */
public enum CompanyMapper {
    INSTANCE;
    /**
     * @param res
     *            ResultSet of a request
     * @return Company the DB object convert in java Object
     * @throws SQLException
     *             if res is null
     */
    public Company resToCompany(ResultSet res) throws SQLException {
        int idCompany = res.getInt("caId");
        String nameCompany = res.getString("caName");
        return new Company(idCompany, nameCompany);
    }
}