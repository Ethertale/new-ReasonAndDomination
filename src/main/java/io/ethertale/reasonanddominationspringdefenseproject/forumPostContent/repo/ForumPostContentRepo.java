package io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.repo;

import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ForumPostContentRepo extends JpaRepository<ForumPostContent, UUID> {
}
