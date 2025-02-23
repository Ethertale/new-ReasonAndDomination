package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.FormLoginDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController {

    private final ProfileService profileService;

    @Autowired
    public LoginController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ModelAndView login(@RequestParam(value = "error", required = false) String errorParam) {
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("form", new FormLoginDTO());

        if (errorParam != null) {
            modelAndView.addObject("errorMessage", "Incorrect username or password.");
        }

        return modelAndView;
    }

}
