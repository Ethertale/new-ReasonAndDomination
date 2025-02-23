package io.ethertale.reasonanddominationspringdefenseproject.guides.repo;

import io.ethertale.reasonanddominationspringdefenseproject.guides.model.GuidePost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GuidePostRepo extends JpaRepository<GuidePost, UUID> {
    GuidePost findBySlug(String slug);
}
