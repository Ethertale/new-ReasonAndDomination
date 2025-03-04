package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.Dungeon;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.service.DungeonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/dungeons")
public class DungeonsRESTController {

    private final DungeonService dungeonService;

    @Autowired
    public DungeonsRESTController(DungeonService dungeonService) {
        this.dungeonService = dungeonService;
    }

    @GetMapping
    public ResponseEntity<List<Dungeon>> getDungeons() {
        List<Dungeon> dungeons = dungeonService.getAllDungeons();
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(dungeons);
    }

    @GetMapping("/{title}")
    public ResponseEntity<Dungeon> showDungeon(@PathVariable String title) {
        Dungeon dungeon = dungeonService.getDungeonByTitle(title);
        return dungeon != null ? ResponseEntity.ok(dungeon) : ResponseEntity.notFound().build();
    }
}
