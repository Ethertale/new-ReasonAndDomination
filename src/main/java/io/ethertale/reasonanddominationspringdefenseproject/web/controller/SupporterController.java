package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/support-us")
public class SupporterController {

    @GetMapping
    public ModelAndView getSupportUsPage(){
        ModelAndView mav = new ModelAndView("supporterPage");
        return mav;
    }
}
