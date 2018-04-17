package com.excilys.formation.cdb.servlet;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dashboard")
public class DashboardSpringMVC {
    
    @GetMapping
    public ModelAndView getDash () {
        ModelAndView modelAndView = new ModelAndView("dashboard");
        return modelAndView;
    }
}