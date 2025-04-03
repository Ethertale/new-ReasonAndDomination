package io.ethertale.reasonanddominationspringdefenseproject.dungeon;

import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.Dungeon;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.DungeonSize;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.DungeonType;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.repo.DungeonRepo;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.service.DungeonServiceImpl;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.DungeonDungeonWithThisTitleDoesNotExist;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class DungeonITests {

    @Autowired
    private DungeonRepo dungeonRepo;
    @Autowired
    private DungeonServiceImpl dungeonService;

    @Test
    public void givenDungeon_whenSaved_thenCanBeRetrieved() {
        Dungeon dungeon = Dungeon.builder()
                .name("Shadow Depths")
                .description("A dark and eerie dungeon filled with undead.")
                .level(10)
                .dungeonType(DungeonType.RAID)
                .dungeonSize(DungeonSize.RAID_40)
                .image("shadow_depths.jpg")
                .imageMap("shadow_depths_map.jpg")
                .slug("shadow-depths")
                .lastBoss("Undead Overlord")
                .build();

        dungeonRepo.save(dungeon);

        Dungeon retrievedDungeon = dungeonRepo.findDungeonBySlug("shadow-depths");
        assertThat(retrievedDungeon).isNotNull();
        assertThat(retrievedDungeon.getName()).isEqualTo("Shadow Depths");
    }

    @Test
    public void givenMultipleDungeons_whenFindAll_thenReturnAll() {
        Dungeon dungeon1 = Dungeon.builder().name("Dark Cave").slug("dark-cave").dungeonType(DungeonType.DUNGEON).dungeonSize(DungeonSize.DUNGEON_10).build();
        Dungeon dungeon2 = Dungeon.builder().name("Frozen Keep").slug("frozen-keep").dungeonType(DungeonType.RAID).dungeonSize(DungeonSize.RAID_20).build();

        dungeonRepo.save(dungeon1);
        dungeonRepo.save(dungeon2);

        List<Dungeon> dungeons = dungeonService.getAllDungeons();
        assertThat(dungeons).hasSize(2);
    }

    @Test
    public void givenDungeon_whenFindByTitle_thenReturnCorrectDungeon() {
        Dungeon dungeon = Dungeon.builder().name("Cursed Tomb").slug("cursed-tomb").dungeonType(DungeonType.DUNGEON).dungeonSize(DungeonSize.DUNGEON_10).build();
        dungeonRepo.save(dungeon);

        Dungeon foundDungeon = dungeonService.getDungeonByTitle("cursed-tomb");
        assertThat(foundDungeon).isNotNull();
        assertThat(foundDungeon.getSlug()).isEqualTo("cursed-tomb");
    }

    @Test
    public void givenNonExistentDungeon_whenFindByTitle_thenThrowException() {
        assertThatThrownBy(() -> dungeonService.getDungeonByTitle("non-existent"))
                .isInstanceOf(DungeonDungeonWithThisTitleDoesNotExist.class);
    }
}
