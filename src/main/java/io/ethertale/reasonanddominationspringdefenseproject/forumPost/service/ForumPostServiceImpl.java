package io.ethertale.reasonanddominationspringdefenseproject.forumPost.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.repo.ForumPostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.repo.ForumPostContentRepo;
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

    @Autowired
    public ForumPostServiceImpl(ForumPostRepo forumPostRepo, ForumPostContentRepo forumPostContentRepo) {
        this.forumPostRepo = forumPostRepo;
        this.forumPostContentRepo = forumPostContentRepo;
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
        if (comment.isBlank() || comment.isEmpty()) {
            throw new IllegalArgumentException("Comment cannot be blank or empty");
        }

        ForumPost post = getForumPostBySlug(slug);
        ForumPostContent newComment = new ForumPostContent(comment, post, commenter, LocalDateTime.now());
        forumPostContentRepo.save(newComment);
        post.getComments().add(newComment);
        System.out.println();
    }

    @Override
    public List<ForumPostContent> getCommentsSortedAsc(String slug) {
        ForumPost post = forumPostRepo.findBySlug(slug);
        post.getComments().sort(Comparator.comparing(ForumPostContent::getCreatedOn));
        return post.getComments();
    }
}
