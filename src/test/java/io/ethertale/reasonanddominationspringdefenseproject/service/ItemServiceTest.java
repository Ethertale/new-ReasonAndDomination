package io.ethertale.reasonanddominationspringdefenseproject.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import io.ethertale.reasonanddominationspringdefenseproject.item.model.Item;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemRarity;
import io.ethertale.reasonanddominationspringdefenseproject.item.model.ItemType;
import io.ethertale.reasonanddominationspringdefenseproject.item.repo.ItemRepo;
import io.ethertale.reasonanddominationspringdefenseproject.item.service.ItemServiceImpl;
import io.ethertale.reasonanddominationspringdefenseproject.web.dto.ItemDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Arrays;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @Mock
    private ItemRepo itemRepo;

    @InjectMocks
    private ItemServiceImpl itemService;

    private Item testItem;
    private ItemDTO testItemDTO;

    @BeforeEach
    void setUp() {
        testItem = new Item(1L, "Sword of Valor", "image.png", ItemType.ONE_HAND, ItemRarity.EPIC, "A powerful sword", 50, 10, 5, 3, 8, 2, 20, 30, "sword-of-valor");
        testItemDTO = new ItemDTO("Sword of Valor", "image.png", ItemType.ONE_HAND, ItemRarity.EPIC, "A powerful sword", 50, 10, 5, 3, 8, 2, 20, 30);
    }

    @Test
    void testCreateItem_Success() {
        when(itemRepo.existsByName(testItemDTO.getName())).thenReturn(false);
        when(itemRepo.save(any(Item.class))).thenReturn(testItem);

        Item savedItem = itemService.createItem(testItemDTO);

        assertNotNull(savedItem);
        assertEquals(testItem.getName(), savedItem.getName());
        verify(itemRepo, times(1)).save(any(Item.class));
    }

    @Test
    void testCreateItem_AlreadyExists() {
        when(itemRepo.existsByName(testItemDTO.getName())).thenReturn(true);

        Item savedItem = itemService.createItem(testItemDTO);

        assertNull(savedItem);
        verify(itemRepo, never()).save(any(Item.class));
    }

    @Test
    void testGetAllItems() {
        when(itemRepo.findAll()).thenReturn(List.of(testItem));

        List<Item> items = itemService.getAllItems();

        assertFalse(items.isEmpty());
        assertEquals(1, items.size());
        verify(itemRepo, times(1)).findAll();
    }

    @Test
    void testGetAllItemsReversed() {
        Item item2 = new Item(2L, "Shield of Light", "shield.png", ItemType.SHIELD, ItemRarity.RARE, "A sturdy shield", 75, 5, 3, 2, 10, 4, 15, 25, "shield-of-light");
        when(itemRepo.findAll()).thenReturn(Arrays.asList(testItem, item2));

        List<Item> reversedItems = itemService.getAllItemsReversed();

        assertEquals(2, reversedItems.size());
        assertEquals(item2.getId(), reversedItems.get(0).getId());
        verify(itemRepo, times(1)).findAll();
    }

    @Test
    void testGetItemBySlug_Found() {
        when(itemRepo.findBySlug(testItem.getSlug())).thenReturn(testItem);

        Item foundItem = itemService.getItemBySlug("sword-of-valor");

        assertNotNull(foundItem);
        assertEquals("sword-of-valor", foundItem.getSlug());
        verify(itemRepo, times(1)).findBySlug("sword-of-valor");
    }

    @Test
    void testGetItemBySlug_NotFound() {
        when(itemRepo.findBySlug("non-existent-item")).thenReturn(null);

        Item foundItem = itemService.getItemBySlug("non-existent-item");

        assertNull(foundItem);
        verify(itemRepo, times(1)).findBySlug("non-existent-item");
    }
}
