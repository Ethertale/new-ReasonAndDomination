package io.ethertale.reasonanddominationspringdefenseproject.forumPost;

import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.repo.ForumPostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.service.ForumPostService;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ForumPostForm;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@ActiveProfiles("test")
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ForumPostServiceITest {

    @Autowired
    private ForumPostService forumPostService;

    @Autowired
    private ForumPostRepo forumPostRepo;

    @Test
    void testCreateForumPost() {
        // Given
        ForumPostForm form = new ForumPostForm();
        form.setTitle("Test Title");
        form.setContent("This is a test forum post.");

        // When
        ForumPost createdPost = forumPostService.createForumPost(form);

        // Then
        assertNotNull(createdPost.getId());
        assertEquals("testtitle", createdPost.getSlug());
        assertEquals("Test Title", createdPost.getTitle());
        assertEquals("This is a test forum post.", createdPost.getContent());
    }

    @Test
    void testFindForumPostBySlug() {
        // Given
        ForumPostForm form = new ForumPostForm();
        form.setTitle("Another Post");
        form.setContent("Some content.");
        forumPostService.createForumPost(form);

        // When
        ForumPost foundPost = forumPostService.getForumPostBySlug("anotherpost");

        // Then
        assertNotNull(foundPost);
        assertEquals("Another Post", foundPost.getTitle());
    }

    @Test
    void testGetAllForumPosts() {
        // Given
        forumPostService.createForumPost(new ForumPostForm("Post 1", "Content 1"));
        forumPostService.createForumPost(new ForumPostForm("Post 2", "Content 2"));

        // When
        List<ForumPost> posts = forumPostService.getAllForumPosts();

        // Then
        assertEquals(2, posts.size());
    }
}
