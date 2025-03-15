package io.ethertale.reasonanddominationspringdefenseproject.news.service;

import io.ethertale.reasonanddominationspringdefenseproject.news.model.NewsPost;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.GuidePostForm;

import java.util.List;

public interface NewsPostService {

    List<NewsPost> getAllNews();

    NewsPost createNewsPost(GuidePostForm guidePostForm);

    NewsPost getNewsPostBySlug(String slug);

    List<NewsPost> findLastFive();

    List<NewsPost> getAllNewsPostsReversed();
}
