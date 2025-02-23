package io.ethertale.reasonanddominationspringdefenseproject.item.service;

import io.ethertale.reasonanddominationspringdefenseproject.item.model.Item;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemRarity;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemType;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemWear;
import io.ethertale.reasonanddominationspringdefenseproject.item.repo.ItemRepo;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ItemDTO;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class ItemServiceImpl implements ItemService {

    ItemRepo repo;

    public ItemServiceImpl(ItemRepo repo) {
        this.repo = repo;
    }

    @Override
    public Item createItem(ItemDTO itemDTO) {
        if (!repo.existsByName(itemDTO.getName())) {
            Item newItem = Item.builder()
                    .name(itemDTO.getName())
                    .image(itemDTO.getImageLink())
                    .type(itemDTO.getType())
                    .rarity(itemDTO.getRarity())
                    .description(itemDTO.getDescription())
                    .armour(itemDTO.getArmour())
                    .strength(itemDTO.getStrength())
                    .agility(itemDTO.getAgility())
                    .intellect(itemDTO.getIntellect())
                    .stamina(itemDTO.getStamina())
                    .spirit(itemDTO.getSpirit())
                    .minDamage(itemDTO.getMinDamage())
                    .maxDamage(itemDTO.getMaxDamage())
                    .build();
            newItem.setSlug(itemDTO.getName());
            return repo.save(newItem);
        }

        return null;
    }

    @Override
    public List<Item> getAllItems() {
        return repo.findAll();
    }

    @Override
    public List<Item> getAllItemsReversed() {
        return repo.findAll().stream().sorted(Comparator.comparing(Item::getId).reversed()).toList();
    }

    @Override
    public Item getItemBySlug(String slug) {
        return repo.findBySlug(slug);
    }

}
