package com.excilys.formation.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.persistence.ComputerDB;

/**
 * @author jirr
 *
 */
public enum ComputerMapper {
    INSTANCE;
    private final Logger logger = LoggerFactory.getLogger(ComputerDB.class);

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
        return new Computer.ComputerBuilder(nameComputer)
                            .id(idComputer)
                            .dateIntroduced(introducedComputer)
                            .dateDiscontinued(discontinuedComputer)
                            .manufactor(manufactor)
                            .build();
    }

    /**
     * @param computer The object to map
     * @return ComputerDTO The object mapped to DTO version
     */
    public ComputerDTO computerToDTO(Computer computer) {
        return new ComputerDTO.ComputerDTOBuilder(computer.getName())
                                .id(computer.getId())
                                .dateIntroduced(optionalDateToString(computer.getDateIntroduced()))
                                .dateDiscontinued(optionalDateToString(computer.getDateDiscontinued()))
                                .manufactorId(optionalCompanyToId(computer.getManufactor()))
                                .manufactorName(optionalCompanyToName(computer.getManufactor()))
                                .build();
    }

    /**
     * @param date The Optional<LocateDate> to check and convert to string
     * @return String The string version of LocalDate
     */
    private String optionalDateToString(Optional<LocalDate> date) {
        if (date.isPresent()) {
            return date.get().toString();
        } else {
            return "";
        }
    }

    /**
     * @param company The Optional<Company> to check and convert to string
     * @return String The string version of LocalDate
     */
    private String optionalCompanyToName(Optional<Company> company) {
        if (company.isPresent()) {
            return company.get().getName();
        } else {
            return "";
        }
    }

    /**
     * @param company The Optional<Company> to check and convert to string
     * @return String The string version of LocalDate
     */
    private int optionalCompanyToId(Optional<Company> company) {
        if (company.isPresent()) {
            return company.get().getId();
        } else {
            return -1;
        }
    }
}
