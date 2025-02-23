package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import org.springframework.boot.Banner;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/races")
public class RaceViewController {

    @GetMapping
    public ModelAndView races() {
        ModelAndView mav = new ModelAndView("races");
        mav.setViewName("raceView");
        return mav;
    }
}
