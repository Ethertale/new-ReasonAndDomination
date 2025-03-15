package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.EditProfile;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

    @GetMapping("/{id}/edit")
    @PreAuthorize("#id == authentication.principal.id or hasRole('ADMIN')")
    public ModelAndView editUserProfile(@AuthenticationPrincipal AuthenticationDetails details, @PathVariable UUID id) {
        ModelAndView modelAndView = new ModelAndView("editProfile");
        modelAndView.addObject("user", profileService.getProfileById(id));
        modelAndView.addObject("editProfileForm", new EditProfile());
        modelAndView.addObject("principal", profileService.getProfileById(details.getId()));
        modelAndView.addObject("roles", profileService.getAllRoles());
        return modelAndView;
    }

    @PostMapping("/{id}/edit/submit")
    @PreAuthorize("#id == authentication.principal.userId or hasRole('ADMIN')")
    public String saveEditProfile(EditProfile editProfile, @AuthenticationPrincipal AuthenticationDetails details, @PathVariable UUID id) {
        Profile profileToBeEdited = profileService.getProfileById(id);
        profileService.updateProfile(editProfile, profileToBeEdited);
        return "redirect:/profile/" + profileToBeEdited.getId();
    }


}
