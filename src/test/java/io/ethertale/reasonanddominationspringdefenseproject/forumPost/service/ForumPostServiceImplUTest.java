package io.ethertale.reasonanddominationspringdefenseproject.forumPost.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.repo.ForumPostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.repo.ForumPostContentRepo;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.service.ForumPostContentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForumPostServiceImplUTest {

    @Mock
    private ForumPostRepo forumPostRepo;
    @Mock
    private ForumPostContentRepo forumPostContentRepo;
    @InjectMocks
    private ForumPostServiceImpl forumPostService;
    @InjectMocks
    private ForumPostContentServiceImpl forumPostContentService;

    @Test
    void givenTwoPosts_getAllForumPosts_shouldReturnTwoPosts() {
        ForumPost forumPost1 = ForumPost.builder().id(UUID.randomUUID()).build();
        ForumPost forumPost2 = ForumPost.builder().id(UUID.randomUUID()).build();

        forumPostRepo.save(forumPost1);
        forumPostRepo.save(forumPost2);

        when(forumPostRepo.findAll()).thenReturn(List.of(forumPost1, forumPost2));

        List<ForumPost> allForumPosts = forumPostService.getAllForumPosts();

        assertEquals(2, allForumPosts.size());
    }
    @Test
    void givenZeroPosts_getAllForumPosts_shouldReturnZeroPosts() {
        when(forumPostRepo.findAll()).thenReturn(List.of());

        List<ForumPost> allForumPosts = forumPostService.getAllForumPosts();

        assertEquals(0, allForumPosts.size());
    }
    @Test
    void givenTwoPosts_getAllForumPostsReversed_shouldReturnTwoPostsReversed() {
        ForumPost forumPost1 = ForumPost.builder().id(UUID.randomUUID()).createdOn(LocalDateTime.of(2017, Month.FEBRUARY, 12, 10, 30)).build();
        ForumPost forumPost2 = ForumPost.builder().id(UUID.randomUUID()).createdOn(LocalDateTime.of(2020, Month.FEBRUARY, 12, 10, 30)).build();

        forumPostRepo.save(forumPost1);
        forumPostRepo.save(forumPost2);

        when(forumPostRepo.findAll()).thenReturn(List.of(forumPost1, forumPost2));

        List<ForumPost> allForumPostsReversed = forumPostService.getAllForumPostsReversed();

        assertEquals(2, allForumPostsReversed.size());
        assertEquals(forumPost1, allForumPostsReversed.get(1));
        assertEquals(forumPost2, allForumPostsReversed.get(0));
    }
    @Test
    void givenSinglePost_getForumPostBySlug_shouldReturnPostBySlug() {
        String slug = "slugtest";

        ForumPost forumPost = ForumPost.builder().id(UUID.randomUUID()).slug(slug).build();
        forumPostRepo.save(forumPost);

        when(forumPostRepo.findBySlug(slug)).thenReturn(forumPost);

        ForumPost result = forumPostService.getForumPostBySlug(slug);

        assertThat(result).isNotNull();
        assertThat(result.getSlug()).isEqualTo(slug);

        verify(forumPostRepo, times(1)).findBySlug(slug);
    }

    @Test
    void createForumPost() {

    }
    @Test
    void givenSixPosts_findLastFive_shouldReturnLastFivePosts() {
        ForumPost forumPost1 = ForumPost.builder().id(UUID.randomUUID()).createdOn(LocalDateTime.of(2017, Month.FEBRUARY, 12, 10, 30)).build();
        ForumPost forumPost2 = ForumPost.builder().id(UUID.randomUUID()).createdOn(LocalDateTime.of(2018, Month.FEBRUARY, 12, 10, 30)).build();
        ForumPost forumPost3 = ForumPost.builder().id(UUID.randomUUID()).createdOn(LocalDateTime.of(2019, Month.FEBRUARY, 12, 10, 30)).build();
        ForumPost forumPost4 = ForumPost.builder().id(UUID.randomUUID()).createdOn(LocalDateTime.of(2020, Month.FEBRUARY, 12, 10, 30)).build();
        ForumPost forumPost5 = ForumPost.builder().id(UUID.randomUUID()).createdOn(LocalDateTime.of(2021, Month.FEBRUARY, 12, 10, 30)).build();
        ForumPost forumPost6 = ForumPost.builder().id(UUID.randomUUID()).createdOn(LocalDateTime.of(2022, Month.FEBRUARY, 12, 10, 30)).build();

        forumPostRepo.save(forumPost1);
        forumPostRepo.save(forumPost2);
        forumPostRepo.save(forumPost3);
        forumPostRepo.save(forumPost4);
        forumPostRepo.save(forumPost5);
        forumPostRepo.save(forumPost6);

        when(forumPostRepo.findAll()).thenReturn(List.of(forumPost1, forumPost2, forumPost3, forumPost4, forumPost5, forumPost6));

        List<ForumPost> allForumPosts = forumPostService.findLastFive();

        assertEquals(5, allForumPosts.size());
        assertEquals(forumPost6, allForumPosts.get(0));
        assertEquals(forumPost5, allForumPosts.get(1));
        assertEquals(forumPost4, allForumPosts.get(2));
        assertEquals(forumPost3, allForumPosts.get(3));
        assertEquals(forumPost2, allForumPosts.get(4));

        verify(forumPostRepo, times(3)).findAll();

    }

    @Test
    void addCommentToPost() {
    }
    @Test
    void givenThreeComments_getCommentsSortedAsc_ShouldReturnCommentsAsc() {
        String slug = "posttest";

        ForumPostContent comment1 = ForumPostContent.builder()
                .id(UUID.randomUUID())
                .createdOn(LocalDateTime.of(2020, Month.FEBRUARY, 12, 10, 30))
                .build();
        ForumPostContent comment2 = ForumPostContent.builder()
                .id(UUID.randomUUID())
                .createdOn(LocalDateTime.of(2021, Month.FEBRUARY, 12, 10, 30))
                .build();
        ForumPostContent comment3 = ForumPostContent.builder()
                .id(UUID.randomUUID())
                .createdOn(LocalDateTime.of(2022, Month.FEBRUARY, 12, 10, 30))
                .build();

        ForumPost forumPost = ForumPost.builder()
                .id(UUID.randomUUID())
                .slug(slug)
                .comments(new ArrayList<>(List.of(comment1, comment2, comment3)))
                .build();

        when(forumPostRepo.findBySlug(slug)).thenReturn(forumPost);

        List<ForumPostContent> commentsAsc = forumPostService.getCommentsSortedAsc(slug);

        assertEquals(3, commentsAsc.size());
        assertEquals(forumPost.getComments().get(0), commentsAsc.get(0));
        assertEquals(forumPost.getComments().get(1), commentsAsc.get(1));

        verify(forumPostRepo, times(1)).findBySlug(slug);
    }
}