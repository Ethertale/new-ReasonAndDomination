package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.guides.model.GuidePost;
import io.ethertale.reasonanddominationspringdefenseproject.guides.model.PostType;
import io.ethertale.reasonanddominationspringdefenseproject.news.model.NewsPost;
import io.ethertale.reasonanddominationspringdefenseproject.news.service.NewsPostService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.GuidePostForm;
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
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(controllers = NewsController.class)
class NewsControllerAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private NewsPostService newsPostService;

    @Test
    void getUnauthenticatedRequestToNews_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/news");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    void getUnauthenticatedRequestToNewsCreateNews_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/news/create-news");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    void getUnauthenticatedRequestToNewsPost_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/news/{slug}", "slug");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    void getAuthenticatedRequestToNews_ReturnNews() throws Exception {
        List<NewsPost> mockedNews = List.of(
                NewsPost.builder().id(UUID.randomUUID()).title("title1").createdOn(LocalDateTime.now()).build(),
                NewsPost.builder().id(UUID.randomUUID()).title("title2").createdOn(LocalDateTime.now()).build(),
                NewsPost.builder().id(UUID.randomUUID()).title("title3").createdOn(LocalDateTime.now()).build()
        );

        when(newsPostService.getAllNewsPostsReversed()).thenReturn(mockedNews);

        MockHttpServletRequestBuilder request = get("/news")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("news"))
                .andExpect(model().attributeExists("principal"))
                .andExpect(model().attributeExists("news"));
    }

    @Test
    void getAuthenticatedRequestToCreateNewsPage_ADMIN_ReturnCreateNewsPage() throws Exception {
        MockHttpServletRequestBuilder request = get("/news/create-news")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.ADMIN, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("createNewsPost"))
                .andExpect(model().attributeExists("NewsPostForm"));
    }

    @Test
    void getAuthenticatedRequestToCreateNewsPage_ANYOTHERROLE_RedirectToNewsPage() throws Exception {
        MockHttpServletRequestBuilder request = get("/news/create-news")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/news"));
    }

    @Test
    void getAuthenticatedRequestToCreateNews_POST_RedirectToNewsPage() throws Exception {
        GuidePostForm mockedForm = new GuidePostForm();
        mockedForm.setTitle("title");
        mockedForm.setContent("content");

        NewsPost mockedPost = NewsPost.builder()
                .id(UUID.randomUUID())
                .title(mockedForm.getTitle())
                .content(mockedForm.getContent())
                .slug("slug")
                .build();

        when(newsPostService.createNewsPost(mockedForm)).thenReturn(mockedPost);

        MockHttpServletRequestBuilder request = post("/news/create-news/create")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.ADMIN, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/news"));
    }
    @Test
    void getAuthenticated_getGuidePost_ReturnGuidePost() throws Exception {
        String slug = "title";

        MockHttpServletRequestBuilder request = get("/news/posts/{slug}", slug)
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        NewsPost mockedPost = NewsPost.builder()
                .id(UUID.randomUUID())
                .title("title")
                .content("content")
                .slug("slug")
                .createdOn(LocalDateTime.now())
                .build();

        when(newsPostService.getNewsPostBySlug(slug)).thenReturn(mockedPost);

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("newsPost"))
                .andExpect(model().attributeExists("specPost"));
    }
}