package com.excilys.formation.cdb.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.formation.cdb.dto.UserDTO;
 
@Controller
public class SecurityController {

    @GetMapping("/login")
    public ModelAndView doGetLogin() {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }
    
    @PostMapping("/login")
    public ModelAndView doPostLogin(@ModelAttribute("user") UserDTO userDTO, Model model) {
        ModelAndView modelAndView = new ModelAndView();
        return modelAndView;
    }
}