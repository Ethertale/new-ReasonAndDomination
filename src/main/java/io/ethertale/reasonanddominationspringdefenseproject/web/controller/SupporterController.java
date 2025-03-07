package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/support-us")
public class SupporterController {

    private final ProfileService profileService;

    @Autowired
    public SupporterController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public ModelAndView getSupportUsPage(@AuthenticationPrincipal AuthenticationDetails details) {
        ModelAndView mav = new ModelAndView("supporterPage");
        mav.addObject("user", profileService.getProfileById(details.getId()));
        return mav;
    }

    @PostMapping("/{tier}")
    public String setStatus(@AuthenticationPrincipal AuthenticationDetails details, @PathVariable String tier) {
        profileService.updateProfileRole(details.getId(), tier);
        return "redirect:/profile/" + details.getId();
    }
}
