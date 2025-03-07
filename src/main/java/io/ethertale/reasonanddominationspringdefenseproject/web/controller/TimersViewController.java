package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/timers")
public class TimersViewController {

    @GetMapping
    public String timersView() {
        return "timers";
    }
}
