package io.ethertale.reasonanddominationspringdefenseproject.forumPost.model;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "forum_post")
public class ForumPost {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String title;
    @Column
    private String slug;
    @Column
    private String content;
    @ManyToOne
    @JoinColumn(name = "profile_id")
    private Profile author;
    @OneToMany(mappedBy = "forumPost", cascade = CascadeType.ALL)
    private Set<ForumPostContent> comments;

    @Column
    private LocalDateTime createdOn;

    public String getCreatedOn() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdOn);
    }

    public String setSlug(String slug) {
        this.slug = slug.toLowerCase()
                .replaceAll("\\s", "-")
                .replaceAll("'", "")
                .replaceAll(",", "");
        return slug;
    }
}
