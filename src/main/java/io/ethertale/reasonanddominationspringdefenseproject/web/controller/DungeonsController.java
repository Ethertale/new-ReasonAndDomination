package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.dungeon.service.DungeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/dungeons")
public class DungeonsController {

    private final DungeonService dungeonService;

    @Autowired
    public DungeonsController(DungeonService dungeonService) {
        this.dungeonService = dungeonService;
    }

    @GetMapping
    public ModelAndView getDungeons() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("dungeons", dungeonService.getAllDungeons());
        modelAndView.setViewName("dungeons");
        return modelAndView;
    }

    @GetMapping("/{title}")
    public ModelAndView showDungeon(@PathVariable String title) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("dungeon", dungeonService.getDungeonByTitle(title));
        modelAndView.setViewName("dungeonViewer");
        return modelAndView;
    }
}
