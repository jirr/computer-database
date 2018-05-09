package com.excilys.formation.cdb.webservice.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.ServiceException;

@RestController
public class ComputerRestController {

    private ComputerService computerService;
    private ComputerMapper computerMapper;

    @Autowired
    public ComputerRestController(ComputerService computerService, ComputerMapper computerMapper) {
        this.computerService = computerService;
        this.computerMapper = computerMapper;
    }

    @GetMapping("/computers")
    public ResponseEntity<List<ComputerDTO>> getComputers() {
        List<ComputerDTO> computerDTOList = new ArrayList<>();
        this.computerService.selectAllComputers()
                .forEach(computer -> computerDTOList.add(this.computerMapper.computerToDTO(computer)));
        return new ResponseEntity<List<ComputerDTO>>(computerDTOList, HttpStatus.OK);
    }

    @GetMapping("/computer/{id}")
    public ResponseEntity<ComputerDTO> getComputer(@PathVariable int id) {
        try {
            return new ResponseEntity<ComputerDTO>(this.computerMapper.computerToDTO(this.computerService.selectOne(id)), HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<ComputerDTO>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/computer/{id}")
    public ResponseEntity<String> deleteComputer(@PathVariable int id) {
        try {
            computerService.deleteComputer(id);
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/computer")
    public ResponseEntity<String> getComputerById(@RequestBody ComputerDTO computerDTO) {
        try {
            this.computerService.createComputer(this.computerMapper.dtoToComputer(computerDTO));
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/computer")
    public ResponseEntity<String> editComputer(@RequestBody ComputerDTO computerDTO) {
        try {
            this.computerService.updateComputer(this.computerMapper.dtoToComputer(computerDTO));
            return new ResponseEntity<String>(HttpStatus.OK);
        } catch (ServiceException e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
