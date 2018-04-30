package com.excilys.formation.cdb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.service.CompanyService;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.ServiceException;
import com.excilys.formation.cdb.pagination.ComputerPage;

@Controller
@RequestMapping("/computer")
public class ComputerController {

    private ComputerService computerService;
    private CompanyService companyService;
    private ComputerMapper computerMapper;

    public ComputerController(ComputerService computerService, CompanyService companyService, ComputerMapper computerMapper) {
        this.computerService = computerService;
        this.companyService = companyService;
        this.computerMapper = computerMapper;
    }

    @GetMapping("/dashboard")
    public ModelAndView doGetDashboard(@RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "index", defaultValue = "1") int index,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "asc", defaultValue = "true") boolean asc) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            ComputerPage page = new ComputerPage(computerService, size, search, index - 1, sort, asc);
            int nbComputer = computerService.countAllComputers(page.getKeywords());
            List<ComputerDTO> computersDTO = new ArrayList<>();
            page.getContent().forEach(computer -> computersDTO.add(computerMapper.computerToDTO(computer)));
            addAllObject(modelAndView, nbComputer, computersDTO, page);
        } catch (ServiceException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }

    @PostMapping("/dashboard")
    public ModelAndView doPostDashboard(@RequestParam(value = "selection") int[] ids) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("successMessage", computerService.deleteComputer(ids));
        } catch (ServiceException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }
    
    @GetMapping("/add")
    public ModelAndView doGetAddComputer() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("companyList", companyService.listAllCompany());
        modelAndView.addObject("computer", new ComputerDTO());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView doPostAddComputer(@ModelAttribute("computer") ComputerDTO computerDTO, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            String successMessage = computerService.createComputer(computerMapper.dtoToComputer(computerDTO));
            modelAndView.addObject("successMessage", successMessage);
        } catch (ServiceException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }
    
    @GetMapping("/edit")
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

    @PostMapping("/edit")
    public ModelAndView doPostEditComputer(@ModelAttribute("computer") ComputerDTO computerDTO, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            String successMessage = computerService.updateComputer(computerMapper.dtoToComputer(computerDTO));
            modelAndView.addObject("successMessage", successMessage);
        } catch (ServiceException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }

    private ModelAndView addAllObject (ModelAndView modelAndView, int nbComputer, List<ComputerDTO> computersDTO, ComputerPage page) {
        modelAndView.addObject("nbComputers", nbComputer);
        modelAndView.addObject("computer_list", computersDTO);
        modelAndView.addObject("keywords", page.getKeywords());
        modelAndView.addObject("maxIndex", page.getLastPageIndex() + 1);
        modelAndView.addObject("currentIndex", page.getCurrentPageIndex() + 1);
        modelAndView.addObject("sortBy", page.getSortBy());
        modelAndView.addObject("asc", page.isAsc());
        modelAndView.addObject("size", page.getSize());
        return modelAndView;
    }

}
