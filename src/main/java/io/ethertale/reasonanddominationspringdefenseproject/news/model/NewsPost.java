package io.ethertale.reasonanddominationspringdefenseproject.news.model;

import jakarta.persistence.*;
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
@Table(name = "news_post")
public class NewsPost {
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
