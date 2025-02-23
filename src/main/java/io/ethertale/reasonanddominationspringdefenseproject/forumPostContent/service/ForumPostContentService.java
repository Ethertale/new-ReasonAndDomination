package io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.service;

import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;

import java.util.List;

public interface ForumPostContentService {
    ForumPostContent createForumPostContent();
    List<ForumPostContent> getForumPostContents();
}
