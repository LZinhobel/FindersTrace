package at.ac.htl.bhitm;


import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemTest {
    private final static String DEFAULT_DESCRIPTION = "No description available";
    private final static String DEFAULT_IMGPATH = "No image available";

    @Test
    public void test_constructor() {
        Item item = new Item(ItemLevel.LOST, "Test");
        assertEquals(ItemLevel.LOST, item.getCurrentStatus());
        assertEquals("Test", item.getTitel());
        assertEquals(DEFAULT_DESCRIPTION, item.getDescription());
        assertEquals(DEFAULT_IMGPATH, item.getImgPath());
        assertEquals(item.getIdCounter(), item.getId());
    }

    @Test
    public void test_constructor_with_description() {
        Item item = new Item(ItemLevel.LOST, "Test", "Test");
        assertEquals(ItemLevel.LOST, item.getCurrentStatus());
        assertEquals("Test", item.getTitel());
        assertEquals("Test", item.getDescription());
        assertEquals(DEFAULT_IMGPATH, item.getImgPath());
    }

    @Test
    public void test_constructor_with_description_and_imgpath() {
        Item item = new Item(ItemLevel.LOST, "Test", "Test", "Test");
        assertEquals(ItemLevel.LOST, item.getCurrentStatus());
        assertEquals("Test", item.getTitel());
        assertEquals("Test", item.getDescription());
        assertEquals("Test", item.getImgPath());
    }

    @Test
    public void test_constructor_with_null_description() {
        Item item = new Item(ItemLevel.LOST, "Test", null);
        assertEquals(DEFAULT_DESCRIPTION, item.getDescription());
    }

    @Test
    public void test_constructor_with_empty_description() {
        Item item = new Item(ItemLevel.LOST, "Test", "");
        assertEquals(DEFAULT_DESCRIPTION, item.getDescription());
    }

    @Test
    void test_constructor_with_null_title() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Item item = new Item(ItemLevel.LOST, null);
        });

        assertEquals("Title must not be null or blank!", ex.getMessage());
    }

    @Test
    void test_constructor_with_empty_title() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            Item item = new Item(ItemLevel.LOST, "");
        });

        assertEquals("Title must not be null or blank!", ex.getMessage());
    }

    @Test
    public void test_setter() {
        Item item = new Item(ItemLevel.LOST, "Test");
        item.setCurrentStatus(ItemLevel.FOUND);
        item.setTitel("Test2");
        item.setDescription("Test2");
        item.setImgPath("Test2");
        assertEquals(ItemLevel.FOUND, item.getCurrentStatus());
        assertEquals("Test2", item.getTitel());
        assertEquals("Test2", item.getDescription());
        assertEquals("Test2", item.getImgPath());
    }

    @Test
    public void test_getId() {
        Item item = new Item(ItemLevel.LOST, "Test");
        assertEquals(item.getIdCounter(), item.getId());
        Item item2 = new Item(ItemLevel.LOST, "Test");
        assertEquals(item2.getIdCounter(), item2.getId());
    }

    @Test
    public void test_getDateAdded() {
        Item item = new Item(ItemLevel.LOST, "Test");
        assertEquals(LocalDate.now(), item.getDateAdded().toLocalDate());
    }

    @Test
    public void test_getDateAddedPretty() {
        Item item = new Item(ItemLevel.LOST, "Test");
        assertEquals(LocalDate.now().getDayOfMonth() + "." + LocalDate.now().getMonthValue() + "." + LocalDate.now().getYear(), item.getDateAddedPretty());
    }

    @Test
    public void test_toString() {
        Item item = new Item(ItemLevel.LOST, "Test");
        assertEquals(String.format("#%d: Test (LOST) - added on %s", item.getIdCounter(), LocalDate.now().getDayOfMonth() + "." + LocalDate.now().getMonthValue() + "." + LocalDate.now().getYear()), item.toString());
    }
}