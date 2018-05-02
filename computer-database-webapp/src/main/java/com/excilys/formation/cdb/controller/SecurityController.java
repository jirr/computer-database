package com.excilys.formation.cdb.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.model.User;
 
@Controller
public class SecurityController {

    @GetMapping("/login")
    public ModelAndView doGetLogin() {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }
    
    @GetMapping("/logout")
    public ModelAndView doPostLogin(@ModelAttribute("user") User user, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("successMessage", "Successfully logged out !");
        return modelAndView;
    }
}