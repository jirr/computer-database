package com.excilys.formation.cdb.webapp.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
 
@Controller
public class SecurityController {

    @GetMapping("/login")
    public ModelAndView doGetLogin() {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }

    @GetMapping(value = {"/403"})
    public String accessDeniedGet() {
        return "403";
    }
    
    @PostMapping(value = {"/403"})
    public String accessDeniedPost() {
        return "403";
    }

}