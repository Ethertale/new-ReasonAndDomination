package io.ethertale.reasonanddominationspringdefenseproject.news.service;

import io.ethertale.reasonanddominationspringdefenseproject.exceptions.GuideFormTitleIsEmptyException;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.NewsDoesNotExistException;
import io.ethertale.reasonanddominationspringdefenseproject.guides.model.PostType;
import io.ethertale.reasonanddominationspringdefenseproject.news.model.NewsPost;
import io.ethertale.reasonanddominationspringdefenseproject.news.repo.NewsPostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.GuidePostForm;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class NewsPostServiceImplUTest {

    @Mock
    private NewsPostRepo newsPostRepo;
    @InjectMocks
    private NewsPostServiceImpl newsPostServiceImpl;

    @Test
    void givenTwoNewsPosts_getAllNews_ReturnsTwoNewsPosts() {
        NewsPost newsPost1 = new NewsPost();
        NewsPost newsPost2 = new NewsPost();

        when(newsPostRepo.findAll()).thenReturn(List.of(newsPost1, newsPost2));
        List<NewsPost> newsPosts = newsPostServiceImpl.getAllNews();

        assertEquals(2, newsPosts.size());
        assertThat(newsPosts).contains(newsPost1, newsPost2);
        assertThat(newsPosts.get(0)).isEqualTo(newsPost1);
        assertThat(newsPosts.get(1)).isEqualTo(newsPost2);

        verify(newsPostRepo, times(1)).findAll();
    }
    @Test
    void givenNoNewsPosts_getAllNews_ReturnsEmptyList() {
        when(newsPostRepo.findAll()).thenReturn(List.of());
        List<NewsPost> newsPosts = newsPostServiceImpl.getAllNews();

        assertTrue(newsPosts.isEmpty());

        verify(newsPostRepo, times(1)).findAll();
    }
    @Test
    void givenCorrectForm_createNewsPost_ShouldCreatePost() {
        GuidePostForm guidePostForm = new GuidePostForm();
        guidePostForm.setTitle("title");
        guidePostForm.setContent("content");
        guidePostForm.setPostType(PostType.ARENA_1);

        NewsPost newPost = newsPostServiceImpl.createNewsPost(guidePostForm);

        when(newsPostRepo.findBySlug("title")).thenReturn(newPost);

        assertThat(newsPostRepo).isNotNull();
        assertThat(newsPostRepo.findBySlug("title")).isEqualTo(newPost);
    }
    @Test
    void givenForm_EmptyTitle_createNewsPost_ShouldThrowException() {
        GuidePostForm guidePostForm = new GuidePostForm();
        guidePostForm.setTitle("");

        assertThrows(GuideFormTitleIsEmptyException.class, () -> newsPostServiceImpl.createNewsPost(guidePostForm));
    }
    @Test
    void givenForm_BlankTitle_createNewsPost_ShouldThrowException() {
        GuidePostForm guidePostForm = new GuidePostForm();
        guidePostForm.setTitle(" ");

        assertThrows(GuideFormTitleIsEmptyException.class, () -> newsPostServiceImpl.createNewsPost(guidePostForm));
    }
    @Test
    void givenForm_EmptyOrNullContent_createNewsPost_ShouldThrowException() {
        GuidePostForm guidePostForm = new GuidePostForm();
        guidePostForm.setContent("");

        assertThrows(GuideFormTitleIsEmptyException.class, () -> newsPostServiceImpl.createNewsPost(guidePostForm));
    }
    @Test
    void givenForm_BlankOrNullContent_createNewsPost_ShouldThrowException() {
        GuidePostForm guidePostForm = new GuidePostForm();
        guidePostForm.setContent(" ");

        assertThrows(GuideFormTitleIsEmptyException.class, () -> newsPostServiceImpl.createNewsPost(guidePostForm));
    }
    @Test
    void givenOneNewsPost_getNewsPostBySlug_ReturnsNewsPost() {
        NewsPost post = NewsPost.builder()
                .slug("title")
                .build();

        when(newsPostRepo.findBySlug("title")).thenReturn(post);
        NewsPost newsPost = newsPostServiceImpl.getNewsPostBySlug("title");

        assertThat(newsPost).isNotNull();
        assertThat(newsPost.getSlug()).isEqualTo(post.getSlug());

        verify(newsPostRepo, times(2)).findBySlug("title");
    }
    @Test
    void givenWrongNewsPost_getNewsPostBySlug_shouldThrowException() {
        NewsPost post = NewsPost.builder()
                .slug("title")
                .build();

        when(newsPostRepo.findBySlug("title")).thenReturn(null);

        assertThrows(NewsDoesNotExistException.class, () -> newsPostServiceImpl.getNewsPostBySlug("title"));
    }
    @Test
    void givenSixNewsPosts_findLastFive_ReturnLastFive_SortedByDateDesc() {
        NewsPost newsPost1 = NewsPost.builder().createdOn(LocalDateTime.now().minusDays(6)).build();
        NewsPost newsPost2 = NewsPost.builder().createdOn(LocalDateTime.now().minusDays(5)).build();
        NewsPost newsPost3 = NewsPost.builder().createdOn(LocalDateTime.now().minusDays(4)).build();
        NewsPost newsPost4 = NewsPost.builder().createdOn(LocalDateTime.now().minusDays(3)).build();
        NewsPost newsPost5 = NewsPost.builder().createdOn(LocalDateTime.now().minusDays(2)).build();
        NewsPost newsPost6 = NewsPost.builder().createdOn(LocalDateTime.now().minusDays(1)).build();

        when(newsPostRepo.findAll()).thenReturn(List.of(newsPost1, newsPost2, newsPost3, newsPost4, newsPost5, newsPost6));
        List<NewsPost> newsPosts = newsPostServiceImpl.findLastFive();

        assertEquals(5, newsPosts.size());
        assertThat(newsPosts.get(0)).isEqualTo(newsPost6);
        assertThat(newsPosts.get(1)).isEqualTo(newsPost5);
        assertThat(newsPosts.get(2)).isEqualTo(newsPost4);
        assertThat(newsPosts.get(3)).isEqualTo(newsPost3);
        assertThat(newsPosts.get(4)).isEqualTo(newsPost2);

        verify(newsPostRepo, times(3)).findAll();
    }
    @Test
    void givenThreeNewsPosts_getAllNewsPostsReversed_ReturnsThreeNewsPosts_SortedByDateDesc() {
        NewsPost newsPost1 = NewsPost.builder().createdOn(LocalDateTime.now().minusDays(6)).build();
        NewsPost newsPost2 = NewsPost.builder().createdOn(LocalDateTime.now().minusDays(5)).build();
        NewsPost newsPost3 = NewsPost.builder().createdOn(LocalDateTime.now().minusDays(4)).build();

        when(newsPostRepo.findAll()).thenReturn(List.of(newsPost1, newsPost2, newsPost3));
        List<NewsPost> newsPosts = newsPostServiceImpl.getAllNewsPostsReversed();

        assertEquals(3, newsPosts.size());
        assertThat(newsPosts.get(0)).isEqualTo(newsPost3);
        assertThat(newsPosts.get(1)).isEqualTo(newsPost2);
        assertThat(newsPosts.get(2)).isEqualTo(newsPost1);

        verify(newsPostRepo, times(1)).findAll();
    }
}