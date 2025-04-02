package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.guides.model.GuidePost;
import io.ethertale.reasonanddominationspringdefenseproject.guides.model.PostType;
import io.ethertale.reasonanddominationspringdefenseproject.guides.service.GuidePostService;
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

import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = GuidesController.class)
class GuidesControllerAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private GuidePostService guidePostService;

    @Test
    void getUnauthenticatedRequestToGuides_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/guides");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    void getUnauthenticatedRequestToGuideCreateGuide_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/guides/create-guide");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    void getUnauthenticatedRequestToGuidePost_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/posts/{slug}", "slug");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    void getAuthenticatedRequestToGuides_ReturnGuides() throws Exception {
        List<GuidePost> mockedGuidePosts = List.of(
                GuidePost.builder().id(UUID.randomUUID()).title("guideTest1").createdOn(LocalDateTime.now()).slug("guidetest1").type(PostType.ARENA_3).build(),
                GuidePost.builder().id(UUID.randomUUID()).title("guideTest2").createdOn(LocalDateTime.now()).slug("guidetest2").type(PostType.ARENA_2).build(),
                GuidePost.builder().id(UUID.randomUUID()).title("guideTest3").createdOn(LocalDateTime.now()).slug("guidetest3").type(PostType.ARENA_1).build()
        );

        when(guidePostService.getAllGuidePosts()).thenReturn(mockedGuidePosts);

        MockHttpServletRequestBuilder request = get("/guides")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("guides"))
                .andExpect(model().attributeExists("guidePosts"));

        verify(guidePostService, times(1)).getAllGuidePosts();
    }

    @Test
    void getAuthenticatedRequestToCreateGuidePage_ReturnCreateGuidePage() throws Exception {
        MockHttpServletRequestBuilder request = get("/guides/create-guide")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("createGuidePost"))
                .andExpect(model().attributeExists("guidePostForm"))
                .andExpect(model().attributeExists("postTypes"));
    }

    @Test
    void getAuthenticatedRequestToCreateGuide_POST_RedirectToGuidesPage() throws Exception {
        GuidePostForm mockedForm = new GuidePostForm();
        mockedForm.setTitle("title");
        mockedForm.setContent("content");
        mockedForm.setPostType(PostType.ARENA_2);

        GuidePost mockedPost = GuidePost.builder()
                .id(UUID.randomUUID())
                .title(mockedForm.getTitle())
                .content(mockedForm.getContent())
                .type(mockedForm.getPostType())
                .slug("slug")
                .build();

        when(guidePostService.createGuidePost(mockedForm)).thenReturn(mockedPost);

        MockHttpServletRequestBuilder request = post("/guides/create-guide/create")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/guides"));
    }

    @Test
    void getAuthenticated_getGuidePost_ReturnGuidePost() throws Exception {
        String slug = "title";

        MockHttpServletRequestBuilder request = get("/guides/posts/{slug}", slug)
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        GuidePost mockedPost = GuidePost.builder()
                .id(UUID.randomUUID())
                .title("title")
                .content("content")
                .type(PostType.ARENA_3)
                .slug("slug")
                .createdOn(LocalDateTime.now())
                .build();

        when(guidePostService.getGuidePostsBySlug(slug)).thenReturn(mockedPost);

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("guidesPost"))
                .andExpect(model().attributeExists("specPost"));
    }

}