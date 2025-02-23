package io.ethertale.reasonanddominationspringdefenseproject.news.repo;

import io.ethertale.reasonanddominationspringdefenseproject.guides.model.GuidePost;
import io.ethertale.reasonanddominationspringdefenseproject.news.model.NewsPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NewsPostRepo extends JpaRepository<NewsPost, UUID> {
    NewsPost findBySlug(String slug);
}
