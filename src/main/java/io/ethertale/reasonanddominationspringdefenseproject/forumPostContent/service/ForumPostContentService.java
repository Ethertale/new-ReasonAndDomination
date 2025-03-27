package io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.service;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;

import java.util.List;

public interface ForumPostContentService {
    ForumPostContent createForumPostContent(ForumPost post, String slug, Profile commenter, String comment);}
