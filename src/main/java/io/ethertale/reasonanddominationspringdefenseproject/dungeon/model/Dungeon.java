package io.ethertale.reasonanddominationspringdefenseproject.dungeon.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Dungeon {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column(length = 2555)
    private String description;
    @Column
    private int level;
    @Column(name = "dungeon_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private DungeonType dungeonType;
    @Column(name = "dungeon_size", nullable = false)
    @Enumerated(EnumType.STRING)
    private DungeonSize dungeonSize;
    @Column
    private String image;
    @Column
    private String imageMap;
    @Column
    private String slug;


    public String setSlug(String slug) {
        this.slug = slug.toLowerCase()
                .replaceAll("\\s", "-")
                .replaceAll("'", "")
                .replaceAll(",", "");
        return slug;
    }
}
