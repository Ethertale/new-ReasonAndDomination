package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.EditProfile;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ProfileController.class)
class ProfileControllerAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProfileService profileService;

    @Test
    void getUnauthenticatedRequestToProfile_Redirect() throws Exception {
        String userId = UUID.randomUUID().toString();

        MockHttpServletRequestBuilder request = get("/profile/{id}", userId);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }
    @Test
    void getAuthenticatedRequestToProfile_ReturnProfile() throws Exception {
        UUID userId = UUID.randomUUID();

        Profile mockedProfile = Profile.builder()
                .id(userId)
                .username("username")
                .email("user@mail.com")
                .password("password")
                .role(AccountRole.USER)
                .status(AccountStatus.ACTIVE)
                .profilePicture("profilePic")
                .build();

        when(profileService.getProfileById(userId)).thenReturn(mockedProfile);

        MockHttpServletRequestBuilder request = get("/profile/{id}", userId)
                .with(user(new AuthenticationDetails(
                        userId, "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("profile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("principal"));
    }
    @Test
    void getAuthenticatedUser_EditProfile_ReturnEditProfileView() throws Exception {
        UUID userId = UUID.randomUUID();

        Profile mockedProfile = Profile.builder()
                .id(userId)
                .username("username")
                .email("user@mail.com")
                .role(AccountRole.USER)
                .build();

        when(profileService.getProfileById(userId)).thenReturn(mockedProfile);
        when(profileService.getAllRoles()).thenReturn(List.of(AccountRole.USER, AccountRole.ADMIN));

        MockHttpServletRequestBuilder request = get("/profile/{id}/edit", userId)
                .with(user(new AuthenticationDetails(
                        userId, "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(view().name("editProfile"))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("editProfileForm"))
                .andExpect(model().attributeExists("principal"))
                .andExpect(model().attributeExists("roles"));
    }

    @Test
    void postAuthenticatedUser_EditProfile_SaveChangesAndRedirect() throws Exception {
        UUID userId = UUID.randomUUID();

        Profile mockedProfile = Profile.builder()
                .id(userId)
                .username("username")
                .email("user@mail.com")
                .role(AccountRole.USER)
                .profilePicture("oldProfilePic")
                .build();

        EditProfile editProfile = new EditProfile();
        editProfile.setProfilePicture("newProfilePic");

        when(profileService.getProfileById(userId)).thenReturn(mockedProfile);

        MockHttpServletRequestBuilder request = post("/profile/{id}/edit/submit", userId)
                .param("username", "newUsername")
                .with(user(new AuthenticationDetails(
                        userId, "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/profile/" + userId));

        verify(profileService, times(1)).updateProfile(any(EditProfile.class), eq(mockedProfile));
    }

}