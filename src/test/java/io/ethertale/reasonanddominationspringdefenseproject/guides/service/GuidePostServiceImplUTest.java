package io.ethertale.reasonanddominationspringdefenseproject.guides.service;

import io.ethertale.reasonanddominationspringdefenseproject.guides.model.GuidePost;
import io.ethertale.reasonanddominationspringdefenseproject.guides.model.PostType;
import io.ethertale.reasonanddominationspringdefenseproject.guides.repo.GuidePostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.GuidePostForm;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GuidePostServiceImplUTest {

    @Mock
    private GuidePostRepo guidePostRepo;
    @Mock
    private HttpSession session;
    @InjectMocks
    private GuidePostServiceImpl guidePostService;

    @Test
    void givenTwoGuides_getAllGuidePosts_ReturnsTwoGuides() {
        GuidePost guide1 = new GuidePost();
        GuidePost guide2 = new GuidePost();

        guidePostRepo.save(guide1);
        guidePostRepo.save(guide2);

        when(guidePostRepo.findAll()).thenReturn(List.of(guide1, guide2));
        List<GuidePost> guidePosts = guidePostService.getAllGuidePosts();

        assertEquals(2, guidePosts.size());
        assertThat(guidePosts.get(0)).isEqualTo(guide1);
        assertThat(guidePosts.get(1)).isEqualTo(guide2);

        verify(guidePostRepo, times(1)).findAll();

    }

    @Test
    void givenNoGuides_getAllGuidePosts_ReturnsEmptyGuides() {
        when(guidePostRepo.findAll()).thenReturn(List.of());
        List<GuidePost> guidePosts = guidePostService.getAllGuidePosts();

        assertEquals(0, guidePosts.size());

        verify(guidePostRepo, times(1)).findAll();
    }

    @Test
    void givenOneGuide_getGuidePostsBySlug_ReturnsGuideBySlug() {
        GuidePost guide1 = GuidePost.builder().id(UUID.randomUUID()).slug("test").build();

        when(guidePostRepo.findBySlug("test")).thenReturn(guide1);
        GuidePost guideBySlug = guidePostService.getGuidePostsBySlug("test");

        assertEquals(guide1, guideBySlug);

        verify(guidePostRepo, times(2)).findBySlug("test");

    }

    @Test
    void givenWrongGuide_getGuidePostsBySlug_ShouldThrowException() {
        when(guidePostRepo.findBySlug("test")).thenReturn(null);

        assertThrows(RuntimeException.class, () -> guidePostService.getGuidePostsBySlug("test"));

        verify(guidePostRepo, times(1)).findBySlug("test");
    }

    @Test
    void givenGoodForm_createGuidePost_CreateGuidePost() {
        when(session.getAttribute("user_name")).thenReturn("test_user");

        GuidePostForm form = new GuidePostForm();
        form.setTitle("test");
        form.setContent("test");
        form.setPostType(PostType.ARENA_3);

        GuidePost post = GuidePost.builder()
                .title(form.getTitle())
                .content(form.getContent())
                .createdOn(LocalDateTime.now())
                .type(form.getPostType())
                .author("test_user")
                .build();
        post.setSlug("Sample Title");

        when(guidePostRepo.save(any(GuidePost.class))).thenReturn(post);

        GuidePost createdPost = guidePostService.createGuidePost(form);

        verify(session).getAttribute("user_name");
        verify(guidePostRepo).save(any(GuidePost.class));

        assertNotNull(createdPost);
        assertThat(createdPost.getId()).isEqualTo(post.getId());
        assertThat(createdPost.getTitle()).isEqualTo(post.getTitle());
        assertThat(createdPost.getContent()).isEqualTo(post.getContent());
        assertThat(createdPost.getCreatedOn()).isEqualTo(post.getCreatedOn());
        assertThat(createdPost.getType()).isEqualTo(post.getType());
        assertThat(createdPost.getAuthor()).isEqualTo(post.getAuthor());
    }

    @Test
    void givenThreeGuidePosts_getGuidePostsByType_ReturnsThreeGuidePosts() {
        GuidePost post1 = GuidePost.builder()
                .title("First Post")
                .content("Content 1")
                .createdOn(LocalDateTime.now().minusDays(2))
                .type(PostType.ARENA_3)
                .build();

        GuidePost post2 = GuidePost.builder()
                .title("Second Post")
                .content("Content 2")
                .createdOn(LocalDateTime.now().minusDays(1))
                .type(PostType.ARENA_1)
                .build();

        GuidePost post3 = GuidePost.builder()
                .title("Third Post")
                .content("Content 3")
                .createdOn(LocalDateTime.now())
                .type(PostType.ARENA_3)
                .build();

        List<GuidePost> guidePosts = List.of(post1, post2, post3);

        when(guidePostRepo.findAll()).thenReturn(guidePosts);

        List<GuidePost> result = guidePostService.getGuidePostsByType(PostType.ARENA_3);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("First Post", result.get(0).getTitle());
        assertEquals("Third Post", result.get(1).getTitle());

        verify(guidePostRepo).findAll();
    }

    @Test
    void givenZeroGuidePosts_getGuidePostsByType_ReturnsZeroGuidePosts() {
        when(guidePostRepo.findAll()).thenReturn(List.of());

        List<GuidePost> result = guidePostService.getGuidePostsByType(PostType.ARENA_3);

        assertNotNull(result);
        assertEquals(0, result.size());

        verify(guidePostRepo).findAll();
    }
}