package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.item.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/items")
public class ItemsController {

    private final ItemService itemService;

    @Autowired
    public ItemsController(ItemService itemService) {
        this.itemService = itemService;
    }

    @GetMapping
    public ModelAndView list() {
        ModelAndView modelAndView = new ModelAndView("items");
        modelAndView.addObject("items", itemService.getAllItems());
        modelAndView.setViewName("items");
        return modelAndView;
    }

    @GetMapping("/{title}")
    public ModelAndView items(@PathVariable String title) {
        ModelAndView modelAndView = new ModelAndView("items");
        modelAndView.addObject("itemSpec", itemService.getItemBySlug(title));
        modelAndView.setViewName("itemViewer");
        return modelAndView;
    }


}
