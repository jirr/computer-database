package com.excilys.formation.cdb.webservice.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.cdb.dto.CompanyDTO;
import com.excilys.formation.cdb.mapper.CompanyMapper;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ServiceException;

@RestController
public class CompanyRestController {

    private CompanyService companyService;
    private CompanyMapper companyMapper;

    @Autowired
    public CompanyRestController(CompanyService companyService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @GetMapping("/companies")
    public ResponseEntity<List<CompanyDTO>> getCompanies() {
        List<CompanyDTO> companyDTOList = new ArrayList<>();
        this.companyService.listAllCompany()
                .forEach(company -> companyDTOList.add(this.companyMapper.companyToDTO(company)));
        return new ResponseEntity<List<CompanyDTO>>(companyDTOList, HttpStatus.OK);
    }

    @DeleteMapping("/company/{id}")
    public ResponseEntity<String> deleteCompany(@PathVariable int id) {
        try {
            this.companyService.deleteCompany(id);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
