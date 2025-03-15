package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.news.service.NewsPostService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.GuidePostForm;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.NewsPostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/news")
public class NewsController {

    private final NewsPostService newsPostService;

    @Autowired
    public NewsController(NewsPostService newsPostService) {
        this.newsPostService = newsPostService;
    }

    @GetMapping
    public ModelAndView showNewsPage(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {
        ModelAndView modelAndView = new ModelAndView("news");
        modelAndView.addObject("principal", authenticationDetails.getRole());
        modelAndView.addObject("news", newsPostService.getAllNews());
        return modelAndView;
    }

    @GetMapping("/create-news")
    public ModelAndView guide(){
        ModelAndView modelAndView = new ModelAndView("createNewsPost");
        modelAndView.addObject("NewsPostForm", new NewsPostForm());
        return modelAndView;
    }

    @PostMapping("/create-news/create")
    public String createGuidePost(@ModelAttribute GuidePostForm guidePostForm){
        newsPostService.createNewsPost(guidePostForm);

        return "redirect:/news";
    }

    @GetMapping("/posts/{slug}")
    public ModelAndView getGuidePost(@PathVariable String slug){
        ModelAndView modelAndView = new ModelAndView("newsPost");
        modelAndView.addObject("specPost", newsPostService.getNewsPostBySlug(slug));
        modelAndView.setViewName("guidesPost");
        return modelAndView;
    }
}
