package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RaceViewController.class)
class RaceViewControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRacesPage_Authenticated_ReturnRacesPage() throws Exception {
        MockHttpServletRequestBuilder request = get("/races")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                )));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("raceView"));
    }

    @Test
    void getClassesPage_Authenticated_ReturnClassesPage() throws Exception {
        MockHttpServletRequestBuilder request = get("/classes")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                )));

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("classView"));
    }

    @Test
    void getUnauthenticatedRequestToRaces_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/races");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    void getUnauthenticatedRequestToClasses_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/classes");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }
}