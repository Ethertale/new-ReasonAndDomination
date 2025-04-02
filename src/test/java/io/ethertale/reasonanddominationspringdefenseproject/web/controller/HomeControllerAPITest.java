package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountStatus;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.service.ForumPostService;
import io.ethertale.reasonanddominationspringdefenseproject.guides.model.GuidePost;
import io.ethertale.reasonanddominationspringdefenseproject.guides.model.PostType;
import io.ethertale.reasonanddominationspringdefenseproject.guides.service.GuidePostService;
import io.ethertale.reasonanddominationspringdefenseproject.news.model.NewsPost;
import io.ethertale.reasonanddominationspringdefenseproject.news.service.NewsPostService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HomeController.class)
class HomeControllerAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ProfileService profileService;
    @MockitoBean
    private ForumPostService forumPostService;
    @MockitoBean
    private GuidePostService guidePostService;
    @MockitoBean
    private NewsPostService newsPostService;

    @Test
    void getUnauthenticatedRequestToHomePage_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/home");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }
    @Test
    void getUnauthenticatedRequestToAboutUsPage_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/home/about-us");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }
    @Test
    void getUnauthenticatedRequestTo404Page_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/home/redirect-to-404-page");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }
    @Test
    void getAuthenticatedRequestToHome_ReturnHomeView() throws Exception {
        Profile mockedUser = Profile.builder()
                .id(UUID.randomUUID())
                .role(AccountRole.USER)
                .status(AccountStatus.ACTIVE)
                .username("test")
                .build();
        List<ForumPost> mockedForumPosts = List.of(
                ForumPost.builder().id(UUID.randomUUID()).title("forumTest1").createdOn(LocalDateTime.now()).slug("forumtest1").build(),
                ForumPost.builder().id(UUID.randomUUID()).title("forumTest2").createdOn(LocalDateTime.now()).slug("forumtest2").build(),
                ForumPost.builder().id(UUID.randomUUID()).title("forumTest3").createdOn(LocalDateTime.now()).slug("forumtest3").build()
        );
        List<NewsPost> mockedNewsPosts = List.of(
                NewsPost.builder().id(UUID.randomUUID()).title("newsTest1").createdOn(LocalDateTime.now()).slug("newstest1").build(),
                NewsPost.builder().id(UUID.randomUUID()).title("newsTest2").createdOn(LocalDateTime.now()).slug("newstest2").build(),
                NewsPost.builder().id(UUID.randomUUID()).title("newsTest3").createdOn(LocalDateTime.now()).slug("newstest3").build()
        );
        List<GuidePost> mockedEdisonSpireGuidePosts = List.of(
                GuidePost.builder().id(UUID.randomUUID()).title("guideTest1").createdOn(LocalDateTime.now()).slug("guidetest1").build(),
                GuidePost.builder().id(UUID.randomUUID()).title("guideTest2").createdOn(LocalDateTime.now()).slug("guidetest2").build(),
                GuidePost.builder().id(UUID.randomUUID()).title("guideTest3").createdOn(LocalDateTime.now()).slug("guidetest3").build()
        );
        List<GuidePost> mockedGeargrindGrottoGuidePosts = List.of(
                GuidePost.builder().id(UUID.randomUUID()).title("guideTest1").createdOn(LocalDateTime.now()).slug("guidetest1").build(),
                GuidePost.builder().id(UUID.randomUUID()).title("guideTest2").createdOn(LocalDateTime.now()).slug("guidetest2").build(),
                GuidePost.builder().id(UUID.randomUUID()).title("guideTest3").createdOn(LocalDateTime.now()).slug("guidetest3").build()
        );
        List<GuidePost> mockedArenaS3GuidePosts = List.of(
                GuidePost.builder().id(UUID.randomUUID()).title("guideTest1").createdOn(LocalDateTime.now()).slug("guidetest1").build(),
                GuidePost.builder().id(UUID.randomUUID()).title("guideTest2").createdOn(LocalDateTime.now()).slug("guidetest2").build(),
                GuidePost.builder().id(UUID.randomUUID()).title("guideTest3").createdOn(LocalDateTime.now()).slug("guidetest3").build()
        );

        when(profileService.getProfileById(any(UUID.class))).thenReturn(mockedUser);
        when(forumPostService.findLastFive()).thenReturn(mockedForumPosts);
        when(newsPostService.findLastFive()).thenReturn(mockedNewsPosts);
        when(guidePostService.getGuidePostsByType(PostType.EDISONSPIRE_FOUNDRY)).thenReturn(mockedEdisonSpireGuidePosts);
        when(guidePostService.getGuidePostsByType(PostType.GEARGRIND_GROTTO)).thenReturn(mockedGeargrindGrottoGuidePosts);
        when(guidePostService.getGuidePostsByType(PostType.ARENA_3)).thenReturn(mockedArenaS3GuidePosts);

        MockHttpServletRequestBuilder request = get("/home")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("forumPosts"))
                .andExpect(model().attributeExists("newsPosts"))
                .andExpect(model().attributeExists("edisonspireGuides"))
                .andExpect(model().attributeExists("geargrindGrottoGuides"))
                .andExpect(model().attributeExists("arenaS3Guides"))
                .andExpect(view().name("index"));
    }
    @Test
    void getAuthenticatedRequestToHome_DeactivatedAccount_RedirectToLogin() throws Exception {
        Profile mockedUser = Profile.builder()
                .id(UUID.randomUUID())
                .role(AccountRole.USER)
                .status(AccountStatus.DEACTIVATED)
                .username("test")
                .build();

        when(profileService.getProfileById(any(UUID.class))).thenReturn(mockedUser);

        MockHttpServletRequestBuilder request = get("/home")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, false, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login?error"));
    }
    @Test
    void getAuthenticatedRequestToAboutUsPage_ReturnAboutUsPage() throws Exception {
        MockHttpServletRequestBuilder request = get("/home/about-us")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("aboutUs"));
    }
}