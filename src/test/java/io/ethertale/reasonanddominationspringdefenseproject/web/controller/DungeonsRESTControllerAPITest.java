package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.Dungeon;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.DungeonSize;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.model.DungeonType;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.service.DungeonService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

        MockHttpServletRequestBuilder request = get("/api/dungeons");

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
    void getDungeons_InternalServerError() throws Exception {
        MockHttpServletRequestBuilder request = get("/api/dungeons");
        //TODO MAKE TEST RETURN 5XX WHEN SERVICE DOES NOT WORK
        mockMvc.perform(request)
                .andExpect(status().is5xxServerError());

        verify(dungeonService, times(1)).getAllDungeons();
    }
}