package io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "forum_post_content")
public class ForumPostContent {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    @Size(max = 10000)
    private String content;
    @ManyToOne
    @JoinColumn(name = "forum_post_id")
    private ForumPost forumPost;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile owner;

    @Column
    private LocalDateTime createdOn;

    public ForumPostContent(String content, ForumPost forumPost, Profile owner, LocalDateTime createdOn) {
        this.content = content;
        this.forumPost = forumPost;
        this.owner = owner;
        this.createdOn = createdOn;
    }
}
