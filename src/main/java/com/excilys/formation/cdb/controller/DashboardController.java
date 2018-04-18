package com.excilys.formation.cdb.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.dto.ComputerDTO;
import com.excilys.formation.cdb.mapper.ComputerMapper;
import com.excilys.formation.cdb.service.ComputerService;
import com.excilys.formation.cdb.service.ServiceException;
import com.excilys.formation.cdb.service.pagination.ComputerPage;

@Controller
@RequestMapping("/dashboard")
public class DashboardController {

    private ComputerService computerService;
    private ComputerMapper computerMapper;

    public DashboardController(ComputerService computerService, ComputerMapper computerMapper) {
        this.computerService = computerService;
        this.computerMapper = computerMapper;
    }

    @GetMapping
    public ModelAndView doGetDashboard(@RequestParam(value = "search", defaultValue = "") String search,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "index", defaultValue = "1") int index,
            @RequestParam(value = "sort", defaultValue = "") String sort,
            @RequestParam(value = "asc", defaultValue = "true") boolean asc) throws ServiceException {
        ModelAndView modelAndView = new ModelAndView();
        ComputerPage page = new ComputerPage(size, computerService);
        page.setKeywords(search);
        page.setSize(size);
        page.goToPage(index - 1);
        page.setSortBy(sort);
        page.setAsc(asc);
        int nbComputer = computerService.countAllComputers(page.getKeywords());
        List<ComputerDTO> computersDTO = new ArrayList<>();
        page.getContent().forEach(computer -> computersDTO.add(computerMapper.computerToDTO(computer)));
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

    @PostMapping
    public ModelAndView doPostDashboard(@RequestParam(value = "selection") int[] ids) {
        ModelAndView modelAndView = new ModelAndView();
        try {
            modelAndView.addObject("confirmMessage", computerService.deleteComputer(ids));
        } catch (ServiceException e) {
            modelAndView.addObject("errorMessage", e.getMessage());
        }
        return modelAndView;
    }
}
