package io.ethertale.reasonanddominationspringdefenseproject.news.service;

import io.ethertale.reasonanddominationspringdefenseproject.news.model.NewsPost;
import io.ethertale.reasonanddominationspringdefenseproject.news.repo.NewsPostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class NewsPostServiceImpl implements NewsPostService {

    NewsPostRepo newsPostRepo;

    @Autowired
    public NewsPostServiceImpl(NewsPostRepo newsPostRepo) {
        this.newsPostRepo = newsPostRepo;
    }

    @Override
    public NewsPost createNewsPost(String title, String content) {
        NewsPost newsPost = NewsPost.builder()
                .title(title)
                .content(content)
                .createdOn(LocalDateTime.now())
                .build();

        newsPost.setSlug(title);

        return newsPostRepo.save(newsPost);
    }
}
