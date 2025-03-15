package io.ethertale.reasonanddominationspringdefenseproject.forumPost.model;

import io.ethertale.reasonanddominationspringdefenseproject.account.model.Profile;
import io.ethertale.reasonanddominationspringdefenseproject.forumPostContent.model.ForumPostContent;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
    private List<ForumPostContent> comments;

    @Column
    private LocalDateTime createdOn;

    public String getCreatedOn() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdOn);
    }

    public String setSlug(String slug) {
        this.slug = slug.toLowerCase()
                .replaceAll("[^a-zA-Z0-9]+","")
                .replaceAll("\\s", "-");
        return slug;
    }

    public String getSlug() {
        return slug.toLowerCase()
                .replaceAll("[^a-zA-Z0-9]+","")
                .replaceAll("\\s", "-");
    }

    public void addComment(ForumPostContent comment) {
        this.comments.add(comment);
    }
}
