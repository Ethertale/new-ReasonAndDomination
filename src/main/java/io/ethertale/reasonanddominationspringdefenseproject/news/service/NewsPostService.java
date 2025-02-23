package io.ethertale.reasonanddominationspringdefenseproject.news.service;

import io.ethertale.reasonanddominationspringdefenseproject.news.model.NewsPost;

public interface NewsPostService {
    NewsPost createNewsPost(String title, String content);
}
