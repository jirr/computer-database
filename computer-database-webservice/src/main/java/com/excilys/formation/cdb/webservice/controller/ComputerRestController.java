package com.excilys.formation.cdb.webservice.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.service.ComputerService;

@RestController
public class ComputerRestController {

    private ComputerService computerService;
    private ComputerMapper computerMapper;

    public ComputerRestController(ComputerService computerService, ComputerMapper computerMapper) {
        this.computerService = computerService;
        this.computerMapper = computerMapper;
    }

    @RequestMapping(method = RequestMethod.GET)
    @GetMapping("/computers")
    public ResponseEntity<List<ComputerDTO>> getComputers() {
        List<ComputerDTO> computerDTOs = new ArrayList<>();
        computerService.selectAllComputers()
                .forEach(computer -> computerDTOs.add(computerMapper.computerToDTO(computer)));
        return new ResponseEntity<List<ComputerDTO>>(computerDTOs, HttpStatus.OK);
    }
}
