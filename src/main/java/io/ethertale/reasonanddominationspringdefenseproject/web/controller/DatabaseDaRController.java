package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.service.DungeonService;
import io.ethertale.reasonanddominationspringdefenseproject.heroRace.service.HeroRaceService;
import io.ethertale.reasonanddominationspringdefenseproject.item.service.ItemService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/database-dar")
public class DatabaseDaRController {

    private final ProfileService profileService;
    private final ItemService itemService;
    private final DungeonService dungeonService;
    private final HeroRaceService heroRaceService;

    @Autowired
    public DatabaseDaRController(ProfileService profileService, ItemService itemService, DungeonService dungeonService, HeroRaceService heroRaceService) {
        this.profileService = profileService;
        this.itemService = itemService;
        this.dungeonService = dungeonService;
        this.heroRaceService = heroRaceService;
    }

    // hasAnyRole() - проверяваме за една от следните роли
    // hasRole() - точно тази роля
    // hasAuthority() - проверяваме за един permission
    // hasAnyAuthority() - проверяваме за един от следните permissions
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ModelAndView databaseDaR(@AuthenticationPrincipal AuthenticationDetails authenticationDetails) {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profiles", profileService.getAllProfilesReversed());
        modelAndView.addObject("dar", dungeonService.getAllDungeons());
        modelAndView.addObject("races", heroRaceService.getAllHeroRaces());
        modelAndView.addObject("items", itemService.getAllItems());
        modelAndView.addObject("formItem", new ItemDTO());

        modelAndView.setViewName("database-dar");
        return modelAndView;
    }

    @PostMapping("/item-create")
    public String createItemWear(@ModelAttribute ItemDTO itemDTO, Model model) {
        itemService.createItem(itemDTO);
        return "redirect:/database-dar";
    }

    //TODO Return Custom Error
}
