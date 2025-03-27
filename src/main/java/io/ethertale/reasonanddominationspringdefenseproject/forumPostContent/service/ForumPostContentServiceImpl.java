package io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.ForumPostContentBlankOrEmptyComment;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.repo.ForumPostContentRepo;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ForumPostContentServiceImpl implements ForumPostContentService {

    ForumPostContentRepo forumPostContentRepo;

    public ForumPostContentServiceImpl(ForumPostContentRepo forumPostContentRepo) {
        this.forumPostContentRepo = forumPostContentRepo;
    }

    @Override
    public ForumPostContent createForumPostContent(ForumPost post, String slug, Profile commenter, String comment) {
        if (comment.isBlank() || comment.isEmpty()) {
            throw new ForumPostContentBlankOrEmptyComment(post.getSlug());
        }

        ForumPostContent newComment = new ForumPostContent(comment, post, commenter, LocalDateTime.now());
        forumPostContentRepo.save(newComment);

        return newComment;
    }
}
