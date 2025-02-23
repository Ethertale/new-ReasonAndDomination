package io.ethertale.reasonanddominationspringdefenseproject.item.service;

import io.ethertale.reasonanddominationspringdefenseproject.item.model.Item;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemRarity;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemType;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemWear;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ItemDTO;

import java.util.List;

public interface ItemService {
    Item createItem(ItemDTO itemDTO);

    List<Item> getAllItems();
    List<Item> getAllItemsReversed();
    Item getItemBySlug(String slug);
}
