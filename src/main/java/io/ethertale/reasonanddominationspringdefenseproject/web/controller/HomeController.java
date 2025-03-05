package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.service.ForumPostService;
import io.ethertale.reasonanddominationspringdefenseproject.guides.model.PostType;
import io.ethertale.reasonanddominationspringdefenseproject.guides.repo.GuidePostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.guides.service.GuidePostService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/home")
public class HomeController {

    private final ProfileService profileService;
    private final ForumPostService forumPostService;
    private final GuidePostService guidePostService;

    @Autowired
    public HomeController(ProfileService profileService, ForumPostService forumPostService, GuidePostService guidePostService) {
        this.profileService = profileService;
        this.forumPostService = forumPostService;
        this.guidePostService = guidePostService;
    }

    @GetMapping
    public ModelAndView index(@AuthenticationPrincipal AuthenticationDetails authenticationDetails, HttpSession session) {

        Profile user = profileService.getProfileById(authenticationDetails.getId());

        if (user.getStatus() == AccountStatus.DEACTIVATED) {
            session.invalidate();
            return new ModelAndView("redirect:/login?error");
        }

        ModelAndView modelAndView = new ModelAndView("index");
        modelAndView.addObject("user", user);
        modelAndView.addObject("forumPosts", forumPostService.findLastFive());
        modelAndView.addObject("edisonspireGuides", guidePostService.getGuidePostsByType(PostType.EDISONSPIRE_FOUNDRY));
        modelAndView.addObject("geargrindGrottoGuides", guidePostService.getGuidePostsByType(PostType.GEARGRIND_GROTTO));
        modelAndView.addObject("arenaS3Guides", guidePostService.getGuidePostsByType(PostType.ARENA_3));
        modelAndView.setViewName("index");

        return modelAndView;
    }

    @GetMapping("/player-lookup")
    public String playerLookup(Model model) {
        return "player-lookup";
    }

    @GetMapping("/about-us")
    public String aboutUsView(){
        return "aboutUs";
    }


}
