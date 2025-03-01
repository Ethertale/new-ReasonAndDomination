package io.ethertale.reasonanddominationspringdefenseproject.dungeon.service;

import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.Dungeon;

import java.util.List;

public interface DungeonService {
    List<Dungeon> getAllDungeons();

    Dungeon getDungeonByTitle(String title);
}
