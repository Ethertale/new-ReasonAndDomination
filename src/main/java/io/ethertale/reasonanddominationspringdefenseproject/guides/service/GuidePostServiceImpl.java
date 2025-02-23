package io.ethertale.reasonanddominationspringdefenseproject.guides.service;

import io.ethertale.reasonanddominationspringdefenseproject.guides.model.GuidePost;
import io.ethertale.reasonanddominationspringdefenseproject.guides.model.PostType;
import io.ethertale.reasonanddominationspringdefenseproject.guides.repo.GuidePostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.GuidePostForm;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class GuidePostServiceImpl implements GuidePostService {

    HttpSession session;
    GuidePostRepo guidePostRepo;

    @Autowired
    public GuidePostServiceImpl(HttpSession session, GuidePostRepo guidePostRepo) {
        this.session = session;
        this.guidePostRepo = guidePostRepo;
    }

    @Override
    public List<GuidePost> getAllGuidePosts() {
        return guidePostRepo.findAll();
    }

    @Override
    public GuidePost getGuidePostsBySlug(String slug) {
        return guidePostRepo.findBySlug(slug);
    }

    @Override
    public GuidePost createGuidePost(GuidePostForm guidePostForm) {
        GuidePost guidePost = GuidePost.builder()
                .title(guidePostForm.getTitle())
                .content(guidePostForm.getContent())
                .createdOn(LocalDateTime.now())
                .type(guidePostForm.getPostType())
                .author(session.getAttribute("user_name").toString())
                .build();

        guidePost.setSlug(guidePostForm.getTitle());

        return guidePostRepo.save(guidePost);
    }

    @Override
    public List<GuidePost> getGuidePostsByType(PostType type) {
        List<GuidePost> returnPosts = new ArrayList<>();
        guidePostRepo.findAll().stream().filter(post -> post.getType().equals(type)).forEach(
                returnPosts::add
        );
        return returnPosts
                .stream()
                .sorted(Comparator.comparing(GuidePost::getCreatedOn))
                .toList();
    }
}
