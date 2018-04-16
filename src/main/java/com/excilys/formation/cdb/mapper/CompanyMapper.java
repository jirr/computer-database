package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Component;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.model.Company;

/**
 * @author jirr
 *
 */
@Component
public class CompanyMapper {
    /**
     * @param resultSet ResultSet of a request
     * @return Company the DB object convert in java Object
     * @throws SQLException if res is null
     */
    public Company resToCompany(ResultSet resultSet) throws SQLException {
        int idCompany = resultSet.getInt("caId");
        String nameCompany = resultSet.getString("caName");
        return new Company(idCompany, nameCompany);
    }

    /**
     * @param company The object to map
     * @return CompanyDTO The object mapped to DTO version
     */
    public CompanyDTO companyToDTO(Company company) {
        return new CompanyDTO(company.getId(), company.getName());
    }
}