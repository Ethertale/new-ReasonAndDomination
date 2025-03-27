package io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.account.repo.ProfileRepo;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.ForumPostContentBlankOrEmptyComment;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.repo.ForumPostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.repo.ForumPostContentRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ForumPostContentServiceImplUTest {

    @Mock
    private ForumPostContentRepo forumPostContentRepo;
    @Mock
    private ProfileRepo profileRepo;
    @Mock
    private ForumPostRepo forumPostRepo;
    @InjectMocks
    private ForumPostContentServiceImpl forumPostContentService;

    @Test
    void givenValidComment_shouldCreateOK(){
        String validComment = "valid comment";

        Profile profile = Profile.builder().id(UUID.randomUUID()).build();
        profileRepo.save(profile);

        ForumPost post = ForumPost.builder()
                .id(UUID.randomUUID())
                .title("title")
                .content("content")
                .slug("title")
                .author(profile)
                .comments(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .build();
        forumPostRepo.save(post);

        when(forumPostRepo.findBySlug(post.getSlug())).thenReturn(post);
        when(profileRepo.getProfileById(profile.getId())).thenReturn(profile);

        ForumPostContent testComment = forumPostContentService.createForumPostContent(forumPostRepo.findBySlug(post.getSlug()), post.getSlug(), profileRepo.getProfileById(profile.getId()), validComment);

        forumPostContentRepo.save(testComment);

        assertNotNull(testComment);
        assertThat(validComment).isEqualTo(testComment.getContent());
    }
    @Test
    void givenBlankComment_shouldThrowException(){
        String blankComment = " ";

        Profile profile = Profile.builder().id(UUID.randomUUID()).build();
        profileRepo.save(profile);

        ForumPost post = ForumPost.builder()
                .id(UUID.randomUUID())
                .title("title")
                .content("content")
                .slug("title")
                .author(profile)
                .comments(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .build();
        forumPostRepo.save(post);

        when(forumPostRepo.findBySlug(post.getSlug())).thenReturn(post);
        when(profileRepo.getProfileById(profile.getId())).thenReturn(profile);

        assertThrows(ForumPostContentBlankOrEmptyComment.class, () -> forumPostContentService.createForumPostContent(forumPostRepo.findBySlug(post.getSlug()), post.getSlug(), profileRepo.getProfileById(profile.getId()), blankComment));
    }
    @Test
    void givenEmptyComment_shouldThrowException(){
        String blankComment = "";

        Profile profile = Profile.builder().id(UUID.randomUUID()).build();
        profileRepo.save(profile);

        ForumPost post = ForumPost.builder()
                .id(UUID.randomUUID())
                .title("title")
                .content("content")
                .slug("title")
                .author(profile)
                .comments(new ArrayList<>())
                .createdOn(LocalDateTime.now())
                .build();
        forumPostRepo.save(post);

        when(forumPostRepo.findBySlug(post.getSlug())).thenReturn(post);
        when(profileRepo.getProfileById(profile.getId())).thenReturn(profile);

        assertThrows(ForumPostContentBlankOrEmptyComment.class, () -> forumPostContentService.createForumPostContent(forumPostRepo.findBySlug(post.getSlug()), post.getSlug(), profileRepo.getProfileById(profile.getId()), blankComment));
    }

    @Test
    void getForumPostContents() {
    }
}