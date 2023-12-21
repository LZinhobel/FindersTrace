package at.ac.htl.bhitm;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

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

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_with_null_title() {
        Item item = new Item(ItemLevel.LOST, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void test_constructor_with_empty_title() {
        Item item = new Item(ItemLevel.LOST, "");
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
}