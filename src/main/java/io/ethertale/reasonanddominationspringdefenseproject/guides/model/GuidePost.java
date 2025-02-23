package io.ethertale.reasonanddominationspringdefenseproject.guides.model;

import jakarta.persistence.*;
import jakarta.servlet.http.HttpSession;
import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "guide_post")
public class GuidePost {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column
    private String title;
    @Column
    private String slug;
    @Column
    private String content;
    @Column
    private PostType type;
    @Column
    private String author;
    @Column
    private LocalDateTime createdOn;

    public String getCreatedOn() {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(createdOn);
    }

    public void setAuthor(HttpSession sessionUsername) {
        this.author = sessionUsername.getAttribute("user_name").toString();
    }

    public String setSlug(String slug) {
        this.slug = slug.toLowerCase()
                .replaceAll("\\s", "-")
                .replaceAll("'", "")
                .replaceAll(",", "");
        return slug;
    }
}
