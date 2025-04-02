package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.Item;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemRarity;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemType;
import io.ethertale.reasonanddominationspringdefenseproject.item.service.ItemService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ItemsController.class)
class ItemsControllerAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ItemService itemService;

    @Test
    void getUnauthenticatedRequestToItemsPage_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/items");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    void getUnauthenticatedRequestToSpecificItemPage_Redirect() throws Exception {
        String title = "title";
        MockHttpServletRequestBuilder request = get("/items/{title}", title);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    void getAuthenticatedRequestToItemsPage_GetItemsPage() throws Exception {
        List<Item> mockedItems = List.of(
                Item.builder().id(1).name("name1").image("image").type(ItemType.FINGER).rarity(ItemRarity.RARE).build(),
                Item.builder().id(2).name("name2").image("image").type(ItemType.FINGER).rarity(ItemRarity.RARE).build(),
                Item.builder().id(3).name("name3").image("image").type(ItemType.FINGER).rarity(ItemRarity.RARE).build()
        );

        when(itemService.getAllItems()).thenReturn(mockedItems);

        MockHttpServletRequestBuilder request = get("/items")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("items"))
                .andExpect(model().attributeExists("items"));
    }

    @Test
    void getAuthenticatedRequestToSpecificItemPage_GetSpecificItemPage() throws Exception {
        String title = "title";

        Item item = Item.builder()
                .id(1)
                .name("name")
                .image("image")
                .type(ItemType.FINGER)
                .rarity(ItemRarity.RARE)
                .description("description")
                .armour(1)
                .strength(1)
                .agility(1)
                .intellect(1)
                .stamina(1)
                .spirit(1)
                .minDamage(1)
                .maxDamage(1)
                .slug("name")
                .build();

        when(itemService.getItemBySlug(title)).thenReturn(item);

        MockHttpServletRequestBuilder request = get("/items/{title}", title)
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("itemViewer"))
                .andExpect(model().attributeExists("itemSpec"));
    }

}