package io.ethertale.reasonanddominationspringdefenseproject.forumPost.repo;

import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ForumPostRepo extends JpaRepository<ForumPost, UUID> {
    ForumPost findBySlug(String slug);

}
