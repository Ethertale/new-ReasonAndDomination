package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ProfileLookup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/player-lookup")
public class PlayerLookUpController {

    private final ProfileService profileService;

    @Autowired
    public PlayerLookUpController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ModelAndView playerLookUpView() {
        ModelAndView modelAndView = new ModelAndView("player-lookup");
        modelAndView.addObject("formDTO", new ProfileLookup());
        modelAndView.addObject("foundProfiles", new ArrayList<>());
        return modelAndView;
    }


    @GetMapping("/search")
    public ModelAndView playerLookupResults(@RequestParam("userInput") String username) {
        ModelAndView modelAndView = new ModelAndView("player-lookup");
        modelAndView.addObject("formDTO", new ProfileLookup());
        modelAndView.addObject("foundProfiles", profileService.searchUsers(username));
        return modelAndView;
    }

}
