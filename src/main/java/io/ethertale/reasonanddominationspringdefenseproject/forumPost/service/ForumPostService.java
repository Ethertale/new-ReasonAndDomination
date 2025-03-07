package io.ethertale.reasonanddominationspringdefenseproject.forumPost.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ForumPostForm;

import java.util.List;

public interface ForumPostService {
    List<ForumPost> getAllForumPosts();
    List<ForumPost> getAllForumPostsReversed();
    ForumPost getForumPostBySlug(String slug);
    ForumPost createForumPost(ForumPostForm forumPostForm);

    List<ForumPost> findLastFive();

    void addCommentToPost(String slug, Profile commenter, String comment);

    List<ForumPostContent> getCommentsSortedAsc(String slug);
}
