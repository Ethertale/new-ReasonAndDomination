package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.service.DungeonService;
import io.ethertale.reasonanddominationspringdefenseproject.heroRace.service.HeroRaceService;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.Item;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemRarity;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemType;
import io.ethertale.reasonanddominationspringdefenseproject.item.service.ItemService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ItemDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = DatabaseDaRController.class)
class DatabaseDaRControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;
    @MockitoBean
    private ItemService itemService;
    @MockitoBean
    private DungeonService dungeonService;
    @MockitoBean
    private HeroRaceService heroRaceService;

    @Test
    @WithMockUser(username = "user", authorities = "USER")
    void getAuthenticatedRequestToDatabase_NotAdmin_ShouldReturn404NotFound() throws Exception {
        AuthenticationDetails principal = new AuthenticationDetails(UUID.randomUUID(), "gatara@mail.com", "123123", AccountRole.USER, true, "profilePic");

        MockHttpServletRequestBuilder request = get("/database-dar")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is4xxClientError());

        verify(profileService, never()).getAllProfilesReversed();
        verify(dungeonService, never()).getAllDungeons();
        verify(heroRaceService, never()).getAllHeroRaces();
        verify(itemService, never()).getAllItems();
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void getAuthenticatedRequestToDatabase_Admin_ReturnDatabase() throws Exception {


        AuthenticationDetails principal = new AuthenticationDetails(UUID.randomUUID(), "gatara@mail.com", "123123", AccountRole.ADMIN, true, "profilePic");

        MockHttpServletRequestBuilder request = get("/database-dar")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful());

        verify(profileService, times(1)).getAllProfilesReversed();
        verify(dungeonService, times(1)).getAllDungeons();
        verify(heroRaceService, times(1)).getAllHeroRaces();
        verify(itemService, times(1)).getAllItems();
    }

    @Test
    void getUnauthenticatedRequestToDatabase_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/database-dar");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection());

        verify(profileService, never()).getAllProfilesReversed();
        verify(dungeonService, never()).getAllDungeons();
        verify(heroRaceService, never()).getAllHeroRaces();
        verify(itemService, never()).getAllItems();
    }

    @Test
    @WithMockUser(username = "admin", authorities = "ADMIN")
    void postAuthorizedRequest_createItem_Admin_ShouldCreateItem() throws Exception {

        AuthenticationDetails principal = new AuthenticationDetails(UUID.randomUUID(), "gatara@mail.com", "123123", AccountRole.ADMIN, true, "profilePic");

        MockHttpServletRequestBuilder request = post("/database-dar/item-create")
                .with(user(principal))
                .with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection());

        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("Test");
        itemDTO.setDescription("Test");
        itemDTO.setMinDamage(1);
        itemDTO.setMaxDamage(1);
        itemDTO.setSpirit(1);
        itemDTO.setStrength(1);
        itemDTO.setAgility(1);
        itemDTO.setStamina(1);
        itemDTO.setIntellect(1);
        itemDTO.setType(ItemType.FINGER);
        itemDTO.setArmour(1);
        itemDTO.setImageLink("1");
        itemDTO.setRarity(ItemRarity.EPIC);

        Item itemObject = Item.builder()
                .name(itemDTO.getName())
                .description(itemDTO.getDescription())
                .minDamage(itemDTO.getMinDamage())
                .maxDamage(itemDTO.getMaxDamage())
                .spirit(itemDTO.getSpirit())
                .strength(itemDTO.getStrength())
                .agility(itemDTO.getAgility())
                .stamina(itemDTO.getStamina())
                .intellect(itemDTO.getIntellect())
                .type(itemDTO.getType())
                .armour(itemDTO.getArmour())
                .image(itemDTO.getImageLink())
                .rarity(itemDTO.getRarity())
                .build();

        when(itemService.createItem(itemDTO)).thenReturn(itemObject);

        Item newItem = itemService.createItem(itemDTO);

        assertThat(newItem.getName()).isEqualTo(itemDTO.getName());
        assertThat(newItem.getDescription()).isEqualTo(itemDTO.getDescription());

        verify(itemService, times(1)).createItem(itemDTO);
    }

}