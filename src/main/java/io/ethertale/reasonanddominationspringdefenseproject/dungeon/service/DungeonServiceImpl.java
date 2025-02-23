package io.ethertale.reasonanddominationspringdefenseproject.dungeon.service;

import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.Dungeon;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.repo.DungeonRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DungeonServiceImpl implements DungeonService {

    DungeonRepo dungeonRepo;

    @Autowired
    public DungeonServiceImpl(DungeonRepo dungeonRepo) {
        this.dungeonRepo = dungeonRepo;
    }

    @Override
    public List<Dungeon> getAllDungeons() {
        return dungeonRepo.findAll();
    }
}
