package io.ethertale.reasonanddominationspringdefenseproject.web.controller;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.AccountRole;
import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.service.ProfileService;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.repo.ForumPostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.service.ForumPostService;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.repo.ForumPostContentRepo;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.service.ForumPostContentService;
import io.ethertale.reasonanddominationspringdefenseproject.security.AuthenticationDetails;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ForumPostForm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = ForumController.class)
class ForumControllerAPITest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private ForumPostService forumPostService;
    @MockitoBean
    private ProfileService profileService;
    @MockitoBean
    private ForumPostRepo forumPostRepo;
    @MockitoBean
    private ForumPostContentService forumPostContentService;
    @MockitoBean
    private ForumPostContentRepo forumPostContentRepo;


    @Test
    void getUnauthenticatedRequestToForums_Redirect() throws Exception {
        MockHttpServletRequestBuilder request = get("/forum");

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

    }

    @Test
    void getAuthenticated_getForums_ReturnsForums() throws Exception {
        MockHttpServletRequestBuilder request = get("/forum")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("forumPosts"))
                .andExpect(view().name("forum"));
    }

    @Test
    void getAuthenticated_getForumCreatePost_ReturnsForumCreatePost() throws Exception {
        MockHttpServletRequestBuilder request = get("/forum/create-post")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().attributeExists("forumPostForm"))
                .andExpect(view().name("createForumPost"));
    }

    @Test
    void getAuthenticated_getForumCreatePost_POST_ReturnsForums_IfSuccess() throws Exception {
        MockHttpServletRequestBuilder request = post("/forum/create-post/create")
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        ForumPostForm postForm = new ForumPostForm();
        postForm.setTitle("title");
        postForm.setContent("content");

        ForumPost createdPost = ForumPost.builder()
                .id(UUID.randomUUID())
                .title("title")
                .content("content")
                .slug("slug")
                .comments(new ArrayList<>())
                .author(new Profile())
                .build();

        when(forumPostService.createForumPost(any(ForumPostForm.class))).thenReturn(createdPost);

        forumPostService.createForumPost(postForm);

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/forum"));
    }

    @Test
    void getAuthenticated_getForumPosts_returnsForumPosts() throws Exception {
        String slug = "title";

        MockHttpServletRequestBuilder request = get("/forum/posts/{slug}", slug)
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        ForumPost mockedPost = ForumPost.builder()
                .id(UUID.randomUUID())
                .title("title")
                .createdOn(LocalDateTime.now())
                .content("content1")
                .slug("title")
                .comments(new ArrayList<>())
                .author(Profile.builder().id(UUID.randomUUID()).username("username").role(AccountRole.USER).build())
                .build();

        when(forumPostRepo.findBySlug(slug)).thenReturn(mockedPost);

        mockMvc.perform(request)
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("forumPost"))
                .andExpect(model().attributeExists("specPost"))
                .andExpect(model().attribute("specPost", equalTo(mockedPost)))
                .andExpect(model().attributeExists("user"))
                .andExpect(model().attributeExists("comments"))
                .andExpect(view().name("forumPost"))
                .andExpect(content().string(containsString("<title>R&D - Forum - title</title>")));
    }

    @Test
    void getAuthenticated_getForumPosts_POST_createForumComment() throws Exception {
        String slug = "title";
        String commenterId = UUID.randomUUID().toString();
        String comment = "This is a test comment.";

        Profile commenter = Profile.builder()
                .id(UUID.fromString(commenterId))
                .email("user@mail.com")
                .build();

        ForumPost forumPost = ForumPost.builder()
                .id(UUID.randomUUID())
                .slug(slug)
                .title("title")
                .content("Some content")
                .build();

        ForumPostContent forumPostContent = new ForumPostContent(comment, forumPost, commenter, LocalDateTime.now());

        when(profileService.getProfileById(UUID.fromString(commenterId))).thenReturn(commenter);
        when(forumPostContentService.createForumPostContent(forumPost, slug, commenter, comment)).thenReturn(forumPostContent);

        MockHttpServletRequestBuilder request = post("/forum/posts/{slug}", slug)
                .param("commenterId", commenterId)
                .param("commentArea", comment)
                .with(user(new AuthenticationDetails(
                        UUID.randomUUID(), "user@mail.com", "password", AccountRole.USER, true, "profilePic"
                ))).with(csrf());

        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/forum/posts/" + slug));

        verify(forumPostService, times(1)).addCommentToPost(slug, commenter, comment);


    }
}