package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping
public class RaceViewController {

    @GetMapping("/races")
    public ModelAndView races() {
        ModelAndView mav = new ModelAndView("races");
        mav.setViewName("raceView");
        return mav;
    }

    @GetMapping("/classes")
    public ModelAndView classes(){
        ModelAndView mav = new ModelAndView("classes");
        mav.setViewName("classView");
        return mav;
    }
}
