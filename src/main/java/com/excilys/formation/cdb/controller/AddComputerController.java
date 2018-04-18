package com.excilys.formation.cdb.controller;

import java.time.LocalDate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.model.Company;
import com.excilys.formation.cdb.model.Computer;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.ServiceException;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {

    private ComputerService computerService;
    private CompanyService companyService;

    public AddComputerController(ComputerService computerService, CompanyService companyService) {
        this.computerService = computerService;
        this.companyService = companyService;
    }

    @GetMapping
    public ModelAndView doGetAddComputer() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("companyList", companyService.listAllCompany());
        return modelAndView;
    }

    @PostMapping
    public ModelAndView doPostAddComputer(@RequestParam(value = "computerName", required = true) String name,
            @RequestParam(value = "introduced", required = false) LocalDate introduced,
            @RequestParam(value = "discontinued", required = false) LocalDate discontinued,
            @RequestParam(value = "companyId", required = true) int companyId) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            Company manufactor = null;
            if (companyId > 0) {
                manufactor = companyService.getCompany(companyId);
            }
            String confirmMessage = computerService.createComputer(new Computer.ComputerBuilder(name)
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
