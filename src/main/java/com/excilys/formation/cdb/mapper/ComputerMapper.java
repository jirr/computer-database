package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;

/**
 * @author jirr
 *
 */
public enum ComputerMapper {
    INSTANCE;
    /**
     * @param resultSet ResultSet of a request
     * @return Computer the BD object convert in java object
     * @throws SQLException if res is null
     */
    public Computer resToComputer(ResultSet resultSet) throws SQLException {
        int idComputer = resultSet.getInt("cuId");
        String nameComputer = resultSet.getString("cuName");
        Date intro = resultSet.getDate("introduced");
        Date disco = resultSet.getDate("discontinued");
        LocalDate introducedComputer = intro == null ? null : resultSet.getDate("introduced").toLocalDate();
        LocalDate discontinuedComputer = disco == null ? null : resultSet.getDate("discontinued").toLocalDate();
        Company manufactor = CompanyMapper.INSTANCE.resToCompany(resultSet);
        return new Computer(idComputer, nameComputer, introducedComputer, discontinuedComputer, manufactor);
    }

    /**
     * @param computer The object to map
     * @return ComputerDTO The object mapped to DTO version
     */
    public ComputerDTO computerToDTO(Computer computer) {
        return new ComputerDTO( computer.getId(),
                                computer.getName(),
                                computer.getDateIntroduced().toString(),
                                computer.getDateDiscontinued().toString(),
                                computer.getManufactor().getName());           
    }
}
