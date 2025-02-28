package io.ethertale.reasonanddominationspringdefenseproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.ethertale.reasonanddominationspringdefenseproject.guides.model.GuidePost;
import io.ethertale.reasonanddominationspringdefenseproject.guides.model.PostType;
import io.ethertale.reasonanddominationspringdefenseproject.guides.repo.GuidePostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.guides.service.GuidePostServiceImpl;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.GuidePostForm;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class GuidePostServiceTest {

    @Mock
    private GuidePostRepo guidePostRepo;

    @Mock
    private HttpSession session;

    @InjectMocks
    private GuidePostServiceImpl guidePostService;

    private GuidePost guidePost;
    private GuidePostForm guidePostForm;

    @BeforeEach
    void setUp() {
        guidePost = GuidePost.builder()
                .id(UUID.randomUUID())
                .title("Test Guide")
                .content("This is a test guide post.")
                .createdOn(LocalDateTime.now())
                .type(PostType.GEARGRIND_GROTTO)
                .author("TestUser")
                .build();

        guidePostForm = new GuidePostForm();
        guidePostForm.setTitle("Test Guide");
        guidePostForm.setContent("This is a test guide post.");
        guidePostForm.setPostType(PostType.GEARGRIND_GROTTO);
    }

    @Test
    void testGetAllGuidePosts() {
        List<GuidePost> guidePosts = List.of(guidePost);
        when(guidePostRepo.findAll()).thenReturn(guidePosts);

        List<GuidePost> result = guidePostService.getAllGuidePosts();

        assertEquals(1, result.size());
        assertEquals("Test Guide", result.get(0).getTitle());
    }

    @Test
    void testGetGuidePostsBySlug() {
        when(guidePostRepo.findBySlug("test-guide")).thenReturn(guidePost);

        GuidePost result = guidePostService.getGuidePostsBySlug("test-guide");

        assertNotNull(result);
        assertEquals("Test Guide", result.getTitle());
    }

    @Test
    void testCreateGuidePost() {
        when(session.getAttribute("user_name")).thenReturn("TestUser");
        when(guidePostRepo.save(any(GuidePost.class))).thenReturn(guidePost);

        GuidePost result = guidePostService.createGuidePost(guidePostForm);

        assertNotNull(result);
        assertEquals("Test Guide", result.getTitle());
        assertEquals("TestUser", result.getAuthor());
    }

    @Test
    void testGetGuidePostsByType() {
        List<GuidePost> guidePosts = new ArrayList<>();
        guidePosts.add(guidePost);
        when(guidePostRepo.findAll()).thenReturn(guidePosts);

        List<GuidePost> result = guidePostService.getGuidePostsByType(PostType.GEARGRIND_GROTTO);

        assertFalse(result.isEmpty());
        assertEquals(PostType.GEARGRIND_GROTTO, result.get(0).getType());
    }
}

