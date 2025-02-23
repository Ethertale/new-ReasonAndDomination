package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class WelcomePageController {

    @GetMapping
    public ModelAndView welcomePage() {
        ModelAndView mav = new ModelAndView("welcomePage");
        mav.setViewName("welcomePage");
        return mav;
    }
}
