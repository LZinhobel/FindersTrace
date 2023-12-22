package at.ac.htl.bhitm;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class ItemManagerTest {
    @Test
    public void test_addItem() {
        ItemManager itemManager = new ItemManager();
        Item item = new Item(ItemLevel.LOST, "Test");
        itemManager.addItem(item);
        assertEquals(item, itemManager.getItemById(item.getId()));
    }

    @Test
    public void test_removeItem() {
        ItemManager itemManager = new ItemManager();
        Item item = new Item(ItemLevel.LOST, "Test");
        itemManager.addItem(item);
        itemManager.removeItem(item);
        assertNull(itemManager.getItemById(item.getId()));
    }

    @Test
    public void test_removeItemById() {
        ItemManager itemManager = new ItemManager();
        Item item = new Item(ItemLevel.LOST, "Test");
        itemManager.addItem(item);
        itemManager.removeItemById(item.getId());
        assertNull(itemManager.getItemById(item.getId()));
    }

    @Test
    public void test_getItems() {
        ItemManager itemManager = new ItemManager();
        Item item = new Item(ItemLevel.LOST, "Test");
        itemManager.addItem(item);
        assertEquals(1, itemManager.getItems().size());
        assertEquals(item, itemManager.getItems().get(0));
    }
}