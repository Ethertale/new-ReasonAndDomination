package io.ethertale.reasonanddominationspringdefenseproject.web.dto;

import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemRarity;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ItemWearDTO {
    private String name;
    private String imageLink;
    private ItemType type;
    private ItemRarity rarity;
    private String description;
    private int armour;
    private int strength;
    private int agility;
    private int intellect;
    private int stamina;
    private int spirit;
}
