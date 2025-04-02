package io.ethertale.reasonanddominationspringdefenseproject.item.service;

import io.ethertale.reasonanddominationspringdefenseproject.exceptions.ItemDoesNotExistException;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.Item;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemRarity;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemType;
import io.ethertale.reasonanddominationspringdefenseproject.item.repo.ItemRepo;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ItemDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemServiceImplUTest {

    @Mock
    private ItemRepo itemRepo;
    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void givenData_createItem_ReturnsOK() {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setName("test");
        itemDTO.setImageLink("test");
        itemDTO.setType(ItemType.FINGER);
        itemDTO.setRarity(ItemRarity.COMMON);
        itemDTO.setDescription("description");
        itemDTO.setArmour(1);
        itemDTO.setStrength(1);
        itemDTO.setAgility(1);
        itemDTO.setIntellect(1);
        itemDTO.setStamina(1);
        itemDTO.setSpirit(1);
        itemDTO.setMinDamage(1);
        itemDTO.setMaxDamage(1);

        Item item = Item.builder()
                .id(1)
                .name("test")
                .image("test")
                .type(ItemType.FINGER)
                .rarity(ItemRarity.COMMON)
                .description("description")
                .armour(1)
                .strength(1)
                .agility(1)
                .intellect(1)
                .stamina(1)
                .spirit(1)
                .minDamage(1)
                .maxDamage(1)
                .build();

        itemService.createItem(itemDTO);

        when(itemRepo.findByName("test")).thenReturn(item);

        assertThat(itemRepo.findByName("test")).isNotNull();
        assertThat(itemRepo.findByName("test").getId()).isEqualTo(1);
        assertThat(itemRepo.findByName("test").getName()).isEqualTo(itemDTO.getName());
        assertThat(itemRepo.findByName("test").getType()).isEqualTo(itemDTO.getType());
        assertThat(itemRepo.findByName("test").getRarity()).isEqualTo(itemDTO.getRarity());
        assertThat(itemRepo.findByName("test").getDescription()).isEqualTo(itemDTO.getDescription());
        assertThat(itemRepo.findByName("test").getArmour()).isEqualTo(itemDTO.getArmour());
        assertThat(itemRepo.findByName("test").getStrength()).isEqualTo(itemDTO.getStrength());
        assertThat(itemRepo.findByName("test").getAgility()).isEqualTo(itemDTO.getAgility());
        assertThat(itemRepo.findByName("test").getIntellect()).isEqualTo(itemDTO.getIntellect());
        assertThat(itemRepo.findByName("test").getStamina()).isEqualTo(itemDTO.getStamina());
        assertThat(itemRepo.findByName("test").getSpirit()).isEqualTo(itemDTO.getSpirit());
        assertThat(itemRepo.findByName("test").getMinDamage()).isEqualTo(itemDTO.getMinDamage());
        assertThat(itemRepo.findByName("test").getMaxDamage()).isEqualTo(itemDTO.getMaxDamage());
    }

    @Test
    void givenTwoItems_getAllItems_ReturnsTwoItems() {
        Item item1 = Item.builder().id(1).name("test1").build();
        Item item2 = Item.builder().id(2).name("test2").build();

        itemRepo.save(item1);
        itemRepo.save(item2);

        when(itemRepo.findAll()).thenReturn(List.of(item1, item2));
        List<Item> allItems = itemService.getAllItems();

        assertThat(allItems).isNotNull();
        assertThat(allItems.size()).isEqualTo(2);
        assertThat(allItems.get(0).getName()).isEqualTo(item1.getName());
        assertThat(allItems.get(1).getName()).isEqualTo(item2.getName());
    }

    @Test
    void givenZeroItems_getAllItems_ReturnsEmptyList() {
        when(itemRepo.findAll()).thenReturn(List.of());
        List<Item> allItems = itemService.getAllItems();

        assertThat(allItems).isNotNull();
        assertThat(allItems.size()).isEqualTo(0);
    }

    @Test
    void givenTwoItems_getAllItemsReversed_ReturnsTwoItemsReversed() {
        Item item1 = Item.builder().id(1).name("test1").build();
        Item item2 = Item.builder().id(2).name("test2").build();

        itemRepo.save(item1);
        itemRepo.save(item2);

        when(itemRepo.findAll()).thenReturn(List.of(item1, item2));
        List<Item> allItems = itemService.getAllItemsReversed();

        assertThat(allItems).isNotNull();
        assertThat(allItems.size()).isEqualTo(2);
        assertThat(allItems.get(0).getName()).isEqualTo(item2.getName());
        assertThat(allItems.get(1).getName()).isEqualTo(item1.getName());
    }

    @Test
    void givenOneItem_getItemBySlug_ReturnsItemBySlug() {
        Item item = Item.builder().id(1).slug("test").build();
        itemRepo.save(item);

        when(itemRepo.findBySlug("test")).thenReturn(item);
        Item itemBySlug = itemService.getItemBySlug("test");

        assertThat(itemBySlug).isNotNull();
        assertThat(itemBySlug.getId()).isEqualTo(item.getId());
        assertThat(itemBySlug.getSlug()).isEqualTo(item.getSlug());

        verify(itemRepo, times(2)).findBySlug("test");
    }

    @Test
    void givenWrongItem_getItemBySlug_ShouldThrowException() {
        Item item = Item.builder().id(1).slug("test").build();
        itemRepo.save(item);

        when(itemRepo.findBySlug("test")).thenReturn(null);

        assertThrows(ItemDoesNotExistException.class, () -> itemService.getItemBySlug("test"));

        verify(itemRepo, times(1)).findBySlug("test");
    }
}