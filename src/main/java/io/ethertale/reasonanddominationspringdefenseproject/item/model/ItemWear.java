package io.ethertale.reasonanddominationspringdefenseproject.item.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemWear {

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

    public String getSlug() {
        return name.toLowerCase().replaceAll("\\s", "-").replaceAll("'", "");
    }
}
