package io.ethertale.reasonanddominationspringdefenseproject.service;

import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.repo.ForumPostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.service.ForumPostServiceImpl;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ForumPostForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ForumPostServiceTest {

    @Mock
    private ForumPostRepo forumPostRepo;

    @InjectMocks
    private ForumPostServiceImpl forumPostService;

    private ForumPost forumPost;

    @BeforeEach
    void setUp() {
        forumPost = ForumPost.builder()
                .title("Test Title")
                .content("Test Content")
                .slug("test-title")
                .createdOn(LocalDateTime.now())
                .build();
    }

    @Test
    void testGetAllForumPosts() {
        when(forumPostRepo.findAll()).thenReturn(List.of(forumPost));
        List<ForumPost> result = forumPostService.getAllForumPosts();
        assertEquals(1, result.size());
        assertEquals("Test Title", result.get(0).getTitle());
    }

    @Test
    void testGetForumPostBySlug() {
        when(forumPostRepo.findBySlug("test-title")).thenReturn(forumPost);
        ForumPost result = forumPostService.getForumPostBySlug("test-title");
        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
    }

    @Test
    void testCreateForumPost() {
        ForumPostForm form = new ForumPostForm("New Post", "Some content");
        when(forumPostRepo.save(any(ForumPost.class))).thenReturn(forumPost);
        ForumPost result = forumPostService.createForumPost(form);
        assertNotNull(result);
        assertEquals("Test Title", result.getTitle());
    }
}
