package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("/{id}")
    public ModelAndView getUserProfile(@PathVariable UUID id, @AuthenticationPrincipal AuthenticationDetails details, HttpSession session) {

        ModelAndView modelAndView = new ModelAndView("profile");
        modelAndView.addObject("user", profileService.getProfileById(id));
        modelAndView.addObject("principal", profileService.getProfileById(details.getId()));
        modelAndView.setViewName("profile");
        return modelAndView;
    }


}
