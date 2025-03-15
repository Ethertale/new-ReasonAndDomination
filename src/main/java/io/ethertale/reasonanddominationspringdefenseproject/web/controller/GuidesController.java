package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.guides.model.PostType;
import io.ethertale.reasonanddominationspringdefenseproject.guides.service.GuidePostService;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.GuidePostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/guides")
public class GuidesController {

    private final GuidePostService guidePostService;

    @Autowired
    public GuidesController(GuidePostService guidePostService) {
        this.guidePostService = guidePostService;
    }

    @GetMapping
    public ModelAndView guides(Model model) {
        ModelAndView modelAndView = new ModelAndView("guides");
        modelAndView.addObject("guidePosts", guidePostService.getAllGuidePosts());
        modelAndView.setViewName("guides");
        return modelAndView;
    }

    @GetMapping("/create-guide")
    public ModelAndView guide(){
        ModelAndView modelAndView = new ModelAndView("createGuidePost");
        modelAndView.addObject("guidePostForm", new GuidePostForm());
        modelAndView.addObject("postTypes", PostType.values());
        modelAndView.setViewName("createGuidePost");
        return modelAndView;
    }

    @PostMapping("/create-guide/create")
    public String createGuidePost(@ModelAttribute GuidePostForm guidePostForm){
        guidePostService.createGuidePost(guidePostForm);

        return "redirect:/guides";
    }

//    @GetMapping("/posts")
//    public ModelAndView guiding(){
//        ModelAndView modelAndView = new ModelAndView("guidesPost");
//        modelAndView.setViewName("guidesPost");
//        return modelAndView;
//    }

    @GetMapping("/posts/{slug}")
    public ModelAndView getGuidePost(@PathVariable String slug){
        ModelAndView modelAndView = new ModelAndView("guidesPost");
        modelAndView.addObject("specPost", guidePostService.getGuidePostsBySlug(slug));
        modelAndView.setViewName("guidesPost");
        return modelAndView;
    }
}
