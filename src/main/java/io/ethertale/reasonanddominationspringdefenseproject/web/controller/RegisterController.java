package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.FormRegisterDTO;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final ProfileService profileService;

    @Autowired
    public RegisterController(ProfileService profileService) {
        this.profileService = profileService;
    }

    protected String registerMessage = "";

    @GetMapping
    public ModelAndView register() {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("message", registerMessage);
        registerMessage = "";
        mav.addObject("formRegisterDTO", new FormRegisterDTO());
        mav.setViewName("register");
        return mav;
    }

    @PostMapping
    public String registerProfile(@Valid FormRegisterDTO formRegisterDTO, BindingResult bindingResult) {

        profileService.registerProfile(formRegisterDTO.getUsername(), formRegisterDTO.getPassword(), formRegisterDTO.getEmail(), formRegisterDTO.getConfirmPassword());

        return "redirect:/login";
    }

}
