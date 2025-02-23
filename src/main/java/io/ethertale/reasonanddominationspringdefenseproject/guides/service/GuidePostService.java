package io.ethertale.reasonanddominationspringdefenseproject.guides.service;

import io.ethertale.reasonanddominationspringdefenseproject.guides.model.GuidePost;
import io.ethertale.reasonanddominationspringdefenseproject.guides.model.PostType;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.GuidePostForm;
import jakarta.servlet.http.HttpSession;

import java.util.List;

public interface GuidePostService {
    List<GuidePost> getAllGuidePosts();
    GuidePost getGuidePostsBySlug(String slug);
    GuidePost createGuidePost(GuidePostForm guidePostForm);
    List<GuidePost> getGuidePostsByType(PostType type);
}
