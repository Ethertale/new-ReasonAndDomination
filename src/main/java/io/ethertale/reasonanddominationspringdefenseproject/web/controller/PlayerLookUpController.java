package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/player-lookup")
public class PlayerLookUpController {

    @GetMapping
    public String playerLookUpView(){
        return "player-lookup";
    }
}
