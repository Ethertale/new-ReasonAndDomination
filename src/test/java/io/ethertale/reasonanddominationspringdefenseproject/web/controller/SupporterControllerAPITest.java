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

import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = SupporterController.class)
class SupporterControllerAPITest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProfileService profileService;

    @Test
    void getUnauthenticatedRequestToDatabase_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/support-us");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection());

    }
    @Test
    void getRequestToSupportUsEndpoint_shouldReturnSupportUsView() throws Exception {

        Profile mockProfile = Profile.builder().id(UUID.randomUUID()).role(AccountRole.USER).build();

        when(profileService.getProfileById(any(UUID.class))).thenReturn(mockProfile);

        MockHttpServletRequestBuilder request = get("/support-us")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("supporterPage"))
                .andExpect(model().attributeExists("user"));
    }
    @Test
    void getRequestToSupportUsEndpoint_UpdateRole_RedirectToProfile() throws Exception {
        UUID userId = UUID.randomUUID();
        Profile mockProfile = Profile.builder().id(userId).role(AccountRole.USER).build();
        when(profileService.getProfileById(any(UUID.class))).thenReturn(mockProfile);

        AccountRole updateRole = AccountRole.TIER_EPIC;

        MockHttpServletRequestBuilder request = post("/support-us/{tier}", updateRole.name())
                .with(user(new AuthenticationDetails(
                        userId, "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/" + mockProfile.getId()));

        verify(profileService, times(1)).updateProfileRole(userId, updateRole.name());
    }
}