package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.Dungeon;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.DungeonSize;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.DungeonType;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.service.DungeonService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DungeonsRESTController.class)
class DungeonsRESTControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DungeonService dungeonService;

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    void getDungeons_OK() throws Exception {

        List<Dungeon> mockDungeons = Arrays.asList(
                new Dungeon(1, "Dungeon 1", "Description", 1, DungeonType.DUNGEON, DungeonSize.DUNGEON_10, "image1", "imageMap1", "slug1", "boss1"),
                new Dungeon(2, "Dungeon 2", "Description", 2, DungeonType.RAID, DungeonSize.RAID_20, "image2", "imageMap2", "slug2", "boss2")
        );

        when(dungeonService.getAllDungeons()).thenReturn(mockDungeons);

        MockHttpServletRequestBuilder request = get("/api/dungeons")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[0].name").isNotEmpty())
                .andExpect(jsonPath("$[0].description").isNotEmpty())
                .andExpect(jsonPath("$[0].level").isNotEmpty())
                .andExpect(jsonPath("$[0].dungeonType").isNotEmpty())
                .andExpect(jsonPath("$[0].dungeonSize").isNotEmpty())
                .andExpect(jsonPath("$[0].image").isNotEmpty())
                .andExpect(jsonPath("$[0].imageMap").isNotEmpty())
                .andExpect(jsonPath("$[0].slug").isNotEmpty())
                .andExpect(jsonPath("$[0].lastBoss").isNotEmpty());

        verify(dungeonService, times(1)).getAllDungeons();
    }
    @Test
    @WithMockUser(username = "user", authorities = "USER")
    void getSpecificDungeon_ReturnsSpecificDungeon() throws Exception {
        String title = "Test Dungeon";

        Dungeon mockDungeon = Dungeon.builder()
                .id(1)
                .name(title)
                .description("test dungeon")
                .level(50)
                .dungeonType(DungeonType.DUNGEON)
                .dungeonSize(DungeonSize.DUNGEON_10)
                .image("image")
                .imageMap("imageMap")
                .slug("test-dungeon")
                .lastBoss("Last Boss")
                .build();

        when(dungeonService.getDungeonByTitle("Test Dungeon")).thenReturn(mockDungeon);

        MockHttpServletRequestBuilder request = get("/api/dungeons/{title}", title)
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").isNotEmpty())
                .andExpect(jsonPath("$.description").isNotEmpty())
                .andExpect(jsonPath("$.level").isNotEmpty())
                .andExpect(jsonPath("$.dungeonType").isNotEmpty())
                .andExpect(jsonPath("$.dungeonSize").isNotEmpty())
                .andExpect(jsonPath("$.image").isNotEmpty())
                .andExpect(jsonPath("$.imageMap").isNotEmpty())
                .andExpect(jsonPath("$.slug").isNotEmpty())
                .andExpect(jsonPath("$.lastBoss").isNotEmpty());
    }
    @Test
    @WithMockUser(username = "user", authorities = "USER")
    void getNonExistentDungeon_ReturnsNotFound() throws Exception {
        String title = "Test Dungeon";

        when(dungeonService.getDungeonByTitle("Test Dungeon")).thenReturn(null);

        MockHttpServletRequestBuilder request = get("/api/dungeons/{title}", title)
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }
}