package io.ethertale.reasonanddominationspringdefenseproject.news.service;

import io.ethertale.reasonanddominationspringdefenseproject.exceptions.GuideFormContentIsEmptyException;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.GuideFormTitleIsEmptyException;
import io.ethertale.reasonanddominationspringdefenseproject.exceptions.NewsDoesNotExistException;
import io.ethertale.reasonanddominationspringdefenseproject.news.model.NewsPost;
import io.ethertale.reasonanddominationspringdefenseproject.news.repo.NewsPostRepo;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.GuidePostForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
public class NewsPostServiceImpl implements NewsPostService {

    NewsPostRepo newsPostRepo;

    @Autowired
    public NewsPostServiceImpl(NewsPostRepo newsPostRepo) {
        this.newsPostRepo = newsPostRepo;
    }

    @Override
    public List<NewsPost> getAllNews() {
        return newsPostRepo.findAll();
    }

    @Override
    public NewsPost createNewsPost(GuidePostForm guidePostForm) {
        if (guidePostForm.getTitle() == null || guidePostForm.getTitle().isBlank() || guidePostForm.getTitle().isEmpty()) {
            throw new GuideFormTitleIsEmptyException();
        }
        if (guidePostForm.getContent() == null || guidePostForm.getContent().isBlank() || guidePostForm.getContent().isEmpty()) {
            throw new GuideFormContentIsEmptyException();
        }

        NewsPost newsPost = NewsPost.builder()
                .title(guidePostForm.getTitle())
                .content(guidePostForm.getContent())
                .createdOn(LocalDateTime.now())
                .build();

        newsPost.setSlug(guidePostForm.getTitle());

        return newsPostRepo.save(newsPost);
    }

    @Override
    public NewsPost getNewsPostBySlug(String slug) {
        if (newsPostRepo.findBySlug(slug) == null) {
            throw new NewsDoesNotExistException();
        }
        return newsPostRepo.findBySlug(slug);
    }

    @Override
    public List<NewsPost> findLastFive() {
        return newsPostRepo.findAll()
                .stream()
                .sorted(Comparator.comparing(NewsPost::getCreatedOn))
                .toList()
                .subList(newsPostRepo.findAll().size() - 5, newsPostRepo.findAll().size())
                .stream().sorted(Comparator.comparing(NewsPost::getCreatedOn).reversed()).toList();
    }

    @Override
    public List<NewsPost> getAllNewsPostsReversed() {
        return newsPostRepo.findAll().stream().sorted(Comparator.comparing(NewsPost::getCreatedOn).reversed()).toList();
    }
}
