package io.ethertale.reasonanddominationspringdefenseproject.item.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(nullable = false)
    private String name;
    @Column
    private String image;
    @Column
    @Enumerated(EnumType.STRING)
    ItemType type;
    @Column(nullable = false)
    ItemRarity rarity;
    @Column
    private String description;
    @Column
    private int armour;
    @Column
    private int strength;
    @Column
    private int agility;
    @Column
    private int intellect;
    @Column
    private int stamina;
    @Column
    private int spirit;
    @Column(name = "min_damage")
    private int minDamage;
    @Column(name = "max_damage")
    private int maxDamage;
    @Column
    private String slug;

    public void setSlug(String name) {
        this.slug = name.toLowerCase()
                .replaceAll("\\s", "-")
                .replaceAll("'", "")
                .replaceAll(",", "");
    }

}
