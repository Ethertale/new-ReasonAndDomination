package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
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

@WebMvcTest(controllers = PlayerLookUpController.class)
class PlayerLookUpControllerAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProfileService profileService;

    @Test
    void getUnauthenticatedRequestToForums_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/player-lookup");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    void getAuthenticatedRequestToPlayerLookUp_ReturnPlayerLookUpPage() throws Exception {
        String username = "test";

        MockHttpServletRequestBuilder request = get("/player-lookup/search")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).param("userInput", username).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("player-lookup"))
                .andExpect(model().attributeExists("formDTO"))
                .andExpect(model().attributeExists("foundProfiles"));
    }

    @Test
    void getAuthenticatedRequestToPlayerLookUp_INPUT_ReturnPlayerList() throws Exception {
        String username = "tiger";

        List<Profile> foundUsers = List.of(
                Profile.builder().id(UUID.randomUUID()).username("tiger52").profilePicture("profile").build()
        );

        when(profileService.searchUsers(username)).thenReturn(foundUsers);

        MockHttpServletRequestBuilder request = get("/player-lookup/search")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).param("userInput", username).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("player-lookup"))
                .andExpect(model().attributeExists("formDTO"))
                .andExpect(model().attributeExists("foundProfiles"));
    }

    @Test
    void getAuthenticatedRequestToPlayerLookUp_NOINPUT_ReturnPlayerList() throws Exception {
        String username = "";

        List<Profile> foundUsers = List.of();

        when(profileService.searchUsers(username)).thenReturn(foundUsers);

        MockHttpServletRequestBuilder request = get("/player-lookup/search")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).param("userInput", username).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("player-lookup"))
                .andExpect(model().attributeExists("formDTO"))
                .andExpect(model().attributeExists("foundProfiles"));
    }

}