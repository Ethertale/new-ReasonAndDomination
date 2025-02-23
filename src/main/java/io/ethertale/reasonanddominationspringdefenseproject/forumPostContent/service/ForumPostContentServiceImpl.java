package io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.service;

import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.repo.ForumPostContentRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ForumPostContentServiceImpl implements ForumPostContentService {

    ForumPostContentRepo forumPostContentRepo;

    public ForumPostContentServiceImpl(ForumPostContentRepo forumPostContentRepo) {
        this.forumPostContentRepo = forumPostContentRepo;
    }

    @Override
    public ForumPostContent createForumPostContent() {
        return null;
    }

    @Override
    public List<ForumPostContent> getForumPostContents() {
        return forumPostContentRepo.findAll();
    }
}
