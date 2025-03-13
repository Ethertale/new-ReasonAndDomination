package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.dungeon.service.DungeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dungeons")
public class DungeonsViewController {

    private final DungeonService dungeonService;

    @Autowired
    public DungeonsViewController(DungeonService dungeonService) {
        this.dungeonService = dungeonService;
    }

    @GetMapping
    public String showDungeonsPage() {
        return "dungeons"; // This must match the Thymeleaf template name (dungeons.html)
    }

    @GetMapping("/{title}")
    public String showDungeonView(@PathVariable String title, Model model) {
        model.addAttribute("title", title);
        return "dungeonViewer";
    }
}
