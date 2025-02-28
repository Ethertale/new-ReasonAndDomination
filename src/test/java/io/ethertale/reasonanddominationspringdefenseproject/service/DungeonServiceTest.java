package io.ethertale.reasonanddominationspringdefenseproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.Dungeon;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.DungeonType;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.DungeonSize;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.repo.DungeonRepo;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.service.DungeonServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
public class DungeonServiceTest {

    @Mock
    private DungeonRepo dungeonRepo;

    @InjectMocks
    private DungeonServiceImpl dungeonService;

    private Dungeon dungeon;

    @BeforeEach
    void setUp() {
        dungeon = new Dungeon(1L, "Dark Abyss", "A mysterious dark dungeon", 10, DungeonType.DUNGEON, DungeonSize.DUNGEON_10, "image.jpg", "map.jpg");
    }

    @Test
    void testGetAllDungeons() {
        when(dungeonRepo.findAll()).thenReturn(List.of(dungeon));
        List<Dungeon> dungeons = dungeonService.getAllDungeons();
        assertEquals(1, dungeons.size());
        assertEquals("Dark Abyss", dungeons.get(0).getName());
        assertEquals("A mysterious dark dungeon", dungeons.get(0).getDescription());
        assertEquals(10, dungeons.get(0).getLevel());
        assertEquals(DungeonType.DUNGEON, dungeons.get(0).getDungeonType());
        assertEquals(DungeonSize.DUNGEON_10, dungeons.get(0).getDungeonSize());
        assertEquals("image.jpg", dungeons.get(0).getImage());
        assertEquals("map.jpg", dungeons.get(0).getImageMap());
    }

    @Test
    void testGetAllDungeons_EmptyList() {
        when(dungeonRepo.findAll()).thenReturn(Collections.emptyList());
        List<Dungeon> dungeons = dungeonService.getAllDungeons();
        assertTrue(dungeons.isEmpty());
    }
}


