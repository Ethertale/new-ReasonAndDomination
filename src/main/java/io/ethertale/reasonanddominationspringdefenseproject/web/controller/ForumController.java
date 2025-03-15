package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.service.ForumPostService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ForumPostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.UUID;

@Controller
@RequestMapping("/forum")
public class ForumController {

    private final ForumPostService forumPostService;
    private final ProfileService profileService;

    @Autowired
    public ForumController(ForumPostService forumPostService, ProfileService profileService) {
        this.forumPostService = forumPostService;
        this.profileService = profileService;
    }

    @GetMapping
    public ModelAndView forum() {
        ModelAndView modelAndView = new ModelAndView("forum");
        modelAndView.addObject("forumPosts", forumPostService.getAllForumPostsReversed());
        modelAndView.setViewName("forum");
        return modelAndView;
    }


    @GetMapping("/create-post")
    public ModelAndView post() {
        ModelAndView mav = new ModelAndView("createForumPost");
        mav.addObject("forumPostForm", new ForumPostForm());
        mav.setViewName("createForumPost");
        return mav;
    }

    @PostMapping("/create-post/create")
    public String createPost(@ModelAttribute ForumPostForm forumPostForm) {
        forumPostService.createForumPost(forumPostForm);

        return "redirect:/forum";
    }


    @GetMapping("/posts")
    public ModelAndView posting() {
        ModelAndView modelAndView = new ModelAndView("forumPost");
        modelAndView.setViewName("forumPost");
        return modelAndView;
    }

    @GetMapping("/posts/{slug}")
    public ModelAndView getPost(@PathVariable String slug, @AuthenticationPrincipal AuthenticationDetails authenticationDetails, Model model) {
        ModelAndView modelAndView = new ModelAndView("forumPost");
        modelAndView.addObject("specPost", forumPostService.getForumPostBySlug(slug));
        modelAndView.addObject("user", profileService.getProfileById(authenticationDetails.getId()));
//        modelAndView.addObject("comments", forumPostService.getForumPostBySlug(slug).getComments().stream().toList());
        modelAndView.addObject("comments", forumPostService.getCommentsSortedAsc(slug));
        modelAndView.setViewName("forumPost");
        return modelAndView;
    }

    @PostMapping("/posts/{slug}")
    public String postComment(@PathVariable String slug, @RequestParam("commenterId") String commenterId, @RequestParam("commentArea") String comment) {
        Profile commenter = profileService.getProfileById(UUID.fromString(commenterId));
        forumPostService.addCommentToPost(slug, commenter, comment);

        return "redirect:/forum/posts/" + slug;
    }
}
