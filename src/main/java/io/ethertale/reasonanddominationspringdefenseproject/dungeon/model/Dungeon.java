package io.ethertale.reasonanddominationspringdefenseproject.dungeon.model;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
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

    public Dungeon() {
    }

    public Dungeon(long id, String name, String description, int level, DungeonType dungeonType, DungeonSize dungeonSize, String image, String imageMap) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.level = level;
        this.dungeonType = dungeonType;
        this.dungeonSize = dungeonSize;
        this.image = image;
        this.imageMap = imageMap;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public DungeonType getDungeonType() {
        return dungeonType;
    }

    public void setDungeonType(DungeonType dungeonType) {
        this.dungeonType = dungeonType;
    }

    public DungeonSize getDungeonSize() {
        return dungeonSize;
    }

    public void setDungeonSize(DungeonSize dungeonSize) {
        this.dungeonSize = dungeonSize;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageMap() {
        return imageMap;
    }

    public void setImageMap(String imageMap) {
        this.imageMap = imageMap;
    }
}
