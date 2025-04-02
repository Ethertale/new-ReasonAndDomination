package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.dungeon.service.DungeonService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = DungeonsViewController.class)
class DungeonsViewControllerAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private DungeonService dungeonService;

    @Test
    void getUnauthenticatedRequestToDungeons_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/dungeons");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    void getAuthenticatedRequestToDungeons_getDungeonsView() throws Exception {
        MockHttpServletRequestBuilder request = get("/dungeons")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("dungeons"));
    }

    @Test
    void getAuthenticatedRequestToSpecificDungeon_getSpecificDungeonsView() throws Exception {
        String title = "test-dungeon";


        MockHttpServletRequestBuilder request = get("/dungeons/{title}", title)
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("dungeonViewer"))
                .andExpect(model().attribute("title", title));
    }
}