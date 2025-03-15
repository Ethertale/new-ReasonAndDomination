package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.FormLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
//@RequestMapping("/login")
public class LoginController {

    private final ProfileService profileService;

    @Autowired
    public LoginController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/login")
    public ModelAndView login(@RequestParam(value = "error", required = false) String errorParam) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("form", new FormLoginDTO());

        if (errorParam != null) {
            modelAndView.addObject("errorMessage", "Incorrect username or password.");
        }

        return modelAndView;
    }

}
