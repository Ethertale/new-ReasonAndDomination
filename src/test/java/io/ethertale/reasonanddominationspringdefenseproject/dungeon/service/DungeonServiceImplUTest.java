package io.ethertale.reasonanddominationspringdefenseproject.dungeon.service;

import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.Dungeon;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.DungeonSize;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.DungeonType;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.repo.DungeonRepo;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.DungeonDungeonWithThisTitleDoesNotExist;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DungeonServiceImplUTest {

    @Mock
    private DungeonRepo dungeonRepo;
    @InjectMocks
    private DungeonServiceImpl dungeonService;

    @Test
    void getAllDungeons_GivenTwoDungeonsSavedToRepo_ThenExpectReturnOK(){
        Dungeon dungeon1 = Dungeon.builder()
                .id(1)
                .name("Dungeon 1")
                .description("Dungeon 1")
                .level(1)
                .dungeonType(DungeonType.DUNGEON)
                .dungeonSize(DungeonSize.DUNGEON_10)
                .image("image")
                .imageMap("imageMap")
                .slug("dungeon-1")
                .lastBoss("Last Boss 1")
                .build();

        Dungeon dungeon2 = Dungeon.builder()
                .id(2)
                .name("Dungeon 2")
                .description("Dungeon 2")
                .level(2)
                .dungeonType(DungeonType.DUNGEON)
                .dungeonSize(DungeonSize.DUNGEON_10)
                .image("image")
                .imageMap("imageMap")
                .slug("dungeon-2")
                .lastBoss("Last Boss 2")
                .build();

        dungeonRepo.save(dungeon1);
        dungeonRepo.save(dungeon2);

        when(dungeonRepo.findAll()).thenReturn(List.of(dungeon1, dungeon2));

        List<Dungeon> dungeons = dungeonService.getAllDungeons();

        assertThat(dungeons).hasSize(2);
        assertThat(dungeons).contains(dungeon1, dungeon2);
    }
//    @Test
//    void getDungeonByTitle() {
//    }\
    @Test
    void givenCorrectTitleExistingDungeon_getDungeonByTitle_shouldReturnDungeon(){
        Dungeon dungeon = Dungeon.builder()
                .id(1)
                .name("Dungeon 1")
                .description("Dungeon 1")
                .level(1)
                .dungeonType(DungeonType.DUNGEON)
                .dungeonSize(DungeonSize.DUNGEON_10)
                .image("image")
                .imageMap("imageMap")
                .slug("dungeon-1")
                .lastBoss("Last Boss 1")
                .build();

        when(dungeonRepo.findDungeonBySlug("dungeon-1")).thenReturn(dungeon);

        dungeonRepo.save(dungeon);

        Dungeon dungeonByTitle = dungeonService.getDungeonByTitle("dungeon-1");

        assertThat(dungeonByTitle.getSlug()).isEqualTo(dungeon.getSlug());
    }
    @Test
    void givenIncorrectTitle_getDungeonByTitle_shouldThrowException(){
        Dungeon dungeon = Dungeon.builder()
                .id(1)
                .name("Dungeon 1")
                .description("Dungeon 1")
                .level(1)
                .dungeonType(DungeonType.DUNGEON)
                .dungeonSize(DungeonSize.DUNGEON_10)
                .image("image")
                .imageMap("imageMap")
                .slug("dungeon-1")
                .lastBoss("Last Boss 1")
                .build();

        when(dungeonRepo.findDungeonBySlug("dungeon-2")).thenReturn(null);

        dungeonRepo.save(dungeon);

        assertThrows(DungeonDungeonWithThisTitleDoesNotExist.class, () -> dungeonService.getDungeonByTitle("dungeon-2"));
    }
}