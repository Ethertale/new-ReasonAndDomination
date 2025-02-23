package io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.forumPost.model.ForumPost;
import jakarta.persistence.*;
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
    private String title;
    @Column
    private String content;
    @ManyToOne
    @JoinColumn(name = "forum_post_id")
    private ForumPost forumPost;

    @Column
    private LocalDateTime createdOn;

}
