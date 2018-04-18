package com.excilys.formation.cdb.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.ServiceException;

@Controller
@RequestMapping("/editComputer")
public class EditComputerController {

    private ComputerService computerService;
    private CompanyService companyService;
    private ComputerMapper computerMapper;

    public EditComputerController(ComputerService computerService, CompanyService companyService, ComputerMapper computerMapper) {
        this.computerService = computerService;
        this.companyService = companyService;
        this.computerMapper = computerMapper;
    }

    @GetMapping
    public ModelAndView doGetEditComputer(@RequestParam(value = "id") int id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("companyList", companyService.listAllCompany());
        try {
            ComputerDTO computerDTO = computerMapper.computerToDTO(computerService.selectOne(id));
            modelAndView.addObject("computer", computerDTO);
        } catch (ServiceException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping
    public ModelAndView doPostEditComputer(@RequestParam(value = "computerName", required = true) String name,
            @RequestParam(value = "id", required = true) int computerId,
            @RequestParam(value = "introduced", required = false) LocalDate introduced,
            @RequestParam(value = "discontinued", required = false) LocalDate discontinued,
            @RequestParam(value = "companyId", required = true) int companyId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Company manufactor = null;
            if (companyId > 0) {
                manufactor = companyService.getCompany(companyId);
            }
            String confirmMessage = computerService.updateComputer(new Computer.ComputerBuilder(name)
                    .id(computerId)
                    .dateIntroduced(introduced)
                    .dateDiscontinued(discontinued)
                    .manufactor(manufactor)
                    .build());
            modelAndView.addObject("confirmMessage", confirmMessage);
        } catch (ServiceException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }
}
