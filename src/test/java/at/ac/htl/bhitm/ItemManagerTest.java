package at.ac.htl.bhitm;


import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

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

    @Test
    public void test_getItemsByStatus() {
        ItemManager itemManager = new ItemManager();
        Item item = new Item(ItemLevel.LOST, "Test");
        itemManager.addItem(item);
        assertEquals(1, itemManager.getItemsByStatus(ItemLevel.LOST).size());
        assertEquals(item, itemManager.getItemsByStatus(ItemLevel.LOST).get(0));
    }

    @Test
    public void test_getItemById() {
        ItemManager itemManager = new ItemManager();
        Item item = new Item(ItemLevel.LOST, "Test");
        itemManager.addItem(item);
        assertEquals(item, itemManager.getItemById(item.getId()));
    }

    @Test
    public void test_getItemById_null() {
        ItemManager itemManager = new ItemManager();
        assertNull(itemManager.getItemById(0));
    }

    @Test
    public void test_addItemsFromFile() {
        ItemManager itemManager = new ItemManager();
        itemManager.AddItemsFromFile("data/testing.csv", new ItemFactory());
        assertEquals(2, itemManager.getItems().size());
    }

    @Test
    public void test_addItemsToFile() {
        ItemManager itemManager = new ItemManager();
        itemManager.AddItemsFromFile("data/testing.csv", new ItemFactory());
        try {
            Files.write(Paths.get("data/testing.csv"), new ArrayList<>());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        itemManager.AddItemsToFile("data/testing.csv");
        assertEquals(2, itemManager.getItems().size());
    }

    @Test
    public void test_addItemsToFile_with_wrong_path() {
        ItemManager itemManager = new ItemManager();

        ItemException ex = assertThrows(ItemException.class, () -> {
            itemManager.AddItemsToFile("notExistingFile.csv");
        });

        assertEquals("Something happened while loading to file!", ex.getMessage());
    }

    @Test
    public void test_addItemsFromFile_with_wrong_path() {
        ItemManager itemManager = new ItemManager();

        ItemException ex = assertThrows(ItemException.class, () -> {
            itemManager.AddItemsFromFile("notExistingFile.csv", new ItemFactory());
        });

        assertEquals("Something happened while loading from file!", ex.getMessage());
    }
}