package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.client.service.TimerFetchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/timers")
public class TimersViewController {

    private final TimerFetchService timerFetchService;

    @Autowired
    public TimersViewController(TimerFetchService timerFetchService) {
        this.timerFetchService = timerFetchService;
    }

    @GetMapping
    public String timersView(Model model) {
        model.addAttribute("worldBossTimer", timerFetchService.getTimer("WORLD_BOSS").getEndTime());
        model.addAttribute("raid20Timer", timerFetchService.getTimer("RAID_20").getEndTime());
        model.addAttribute("raid40Timer", timerFetchService.getTimer("RAID_40").getEndTime());
        return "timers";
    }
}
