package io.ethertale.reasonanddominationspringdefenseproject.forumPost.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.repo.ForumPostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.repo.ForumPostContentRepo;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.service.ForumPostContentServiceImpl;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ForumPostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class ForumPostServiceImpl implements ForumPostService {

    private final ForumPostRepo forumPostRepo;
    private final ForumPostContentRepo forumPostContentRepo;
    private final ForumPostContentServiceImpl forumPostContentService;

    @Autowired
    public ForumPostServiceImpl(ForumPostRepo forumPostRepo, ForumPostContentRepo forumPostContentRepo, ForumPostContentServiceImpl forumPostContentService) {
        this.forumPostRepo = forumPostRepo;
        this.forumPostContentRepo = forumPostContentRepo;
        this.forumPostContentService = forumPostContentService;
    }

    @Override
    public List<ForumPost> getAllForumPosts() {
        return forumPostRepo.findAll();
    }

    @Override
    public List<ForumPost> getAllForumPostsReversed() {
        return forumPostRepo.findAll().stream().sorted(Comparator.comparing(ForumPost::getCreatedOn).reversed()).toList();
    }

    @Override
    public ForumPost getForumPostBySlug(String slug) {
        return forumPostRepo.findBySlug(slug);
    }

    @Override
    public ForumPost createForumPost(ForumPostForm forumPostForm) {
        ForumPost forumPost = ForumPost.builder()
                .title(forumPostForm.getTitle())
                .content(forumPostForm.getContent())
                .createdOn(LocalDateTime.now())
                .build();

        forumPost.setSlug(forumPost.getTitle());

        return forumPostRepo.save(forumPost);
    }

    @Override
    public List<ForumPost> findLastFive() {
        return forumPostRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(ForumPost::getCreatedOn))
                .toList()
                .subList(forumPostRepo.findAll().size() - 5, forumPostRepo.findAll().size())
                .stream().sorted(Comparator.comparing(ForumPost::getCreatedOn).reversed()).toList();
    }

    @Override
    public void addCommentToPost(String slug, Profile commenter, String comment) {
        ForumPost post = getForumPostBySlug(slug);
        ForumPostContent newComment = forumPostContentService.createForumPostContent(post, slug, commenter, comment);
        post.getComments().add(newComment);
    }

    @Override
    public List<ForumPostContent> getCommentsSortedAsc(String slug) {
        ForumPost post = forumPostRepo.findBySlug(slug);
        post.getComments().sort(Comparator.comparing(ForumPostContent::getCreatedOn));
        return post.getComments();
    }
}
