package io.ethertale.reasonanddominationspringdefenseproject.runnerTESTS;

import io.ethertale.reasonanddominationspringdefenseproject.item.service.ItemService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements CommandLineRunner {

    ItemService itemService;

    public StartupRunner(ItemService itemService) {
        this.itemService = itemService;
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
