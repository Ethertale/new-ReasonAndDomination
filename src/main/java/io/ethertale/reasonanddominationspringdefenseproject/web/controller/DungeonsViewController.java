package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dungeons")
public class DungeonsViewController {

    @GetMapping
    public String showDungeonsPage() {
        return "dungeons"; // This must match the Thymeleaf template name (dungeons.html)
    }

    @GetMapping("/{title}")
    public String showDungeonView(@PathVariable String title) {
        return "dungeonViewer";
    }
}
