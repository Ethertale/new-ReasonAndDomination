package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.forumPost.service.ForumPostService;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ForumPostForm;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Comparator;

@Controller
@RequestMapping("/forum")
public class ForumController {

    private final ForumPostService forumPostService;

    public ForumController(ForumPostService forumPostService) {
        this.forumPostService = forumPostService;
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
    public ModelAndView getPost(@PathVariable String slug) {
        ModelAndView modelAndView = new ModelAndView("forumPost");
        modelAndView.addObject("specPost", forumPostService.getForumPostBySlug(slug));
        modelAndView.setViewName("forumPost");
        return modelAndView;
    }
}
