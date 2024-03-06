package at.ac.htl.bhitm.backend;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ItemTest {
    private final static String DEFAULT_DESCRIPTION = "No description available";
    private final static String DEFAULT_IMGPATH = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1200px-No_image_available.svg.png";

    @Test
    public void test_constructor() {
        Item item = new Item(ItemLevel.LOST, "Test");
        assertEquals(ItemLevel.LOST, item.getCurrentStatus());
        assertEquals("Test", item.getTitle());
        assertEquals(DEFAULT_DESCRIPTION, item.getDescription());
        assertEquals(DEFAULT_IMGPATH, item.getImgPath());
        assertEquals(item.getIdCounter()-1, item.getId());
    }

    @Test
    public void test_constructor_with_description() {
        Item item = new Item(ItemLevel.LOST, "Test", "Test");
        assertEquals(ItemLevel.LOST, item.getCurrentStatus());
        assertEquals("Test", item.getTitle());
        assertEquals("Test", item.getDescription());
        assertEquals(DEFAULT_IMGPATH, item.getImgPath());
    }

    @Test
    public void test_constructor_with_description_and_imgpath() {
        Item item = new Item(ItemLevel.LOST, "Test", "Test", "Test");
        assertEquals(ItemLevel.LOST, item.getCurrentStatus());
        assertEquals("Test", item.getTitle());
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
    public void test_constructor_with_null_title() {
        ItemException ex = assertThrows(ItemException.class, () -> {
            new Item(ItemLevel.LOST, null);
        });

        assertEquals("Title must not be null or blank!", ex.getMessage());
    }

    @Test
    public void test_constructor_with_empty_title() {
        ItemException ex = assertThrows(ItemException.class, () -> {
            new Item(ItemLevel.LOST, "");
        });

        assertEquals("Title must not be null or blank!", ex.getMessage());
    }

    @Test
    public void test_setTitle_with_String_to_long() {
        ItemException ex = assertThrows(ItemException.class, () -> {
            new Item(ItemLevel.LOST, "more than 20 characters");
        });

        assertEquals("Title must not be longer than 20 characters!", ex.getMessage());
    }

    @Test
    public void test_setter() {
        Item item = new Item(ItemLevel.LOST, "Test");
        item.setCurrentStatus(ItemLevel.FOUND);
        item.setTitle("Test2");
        item.setDescription("Test2");
        item.setImgPath("Test2");
        assertEquals(ItemLevel.FOUND, item.getCurrentStatus());
        assertEquals("Test2", item.getTitle());
        assertEquals("Test2", item.getDescription());
        assertEquals("Test2", item.getImgPath());
        item.setImgPath(null);
        assertEquals(DEFAULT_IMGPATH, item.getImgPath());
    }

    @Test
    public void test_getId() {
        Item item = new Item(ItemLevel.LOST, "Test");
        assertEquals(item.getIdCounter()-1, item.getId());
        Item item2 = new Item(ItemLevel.LOST, "Test");
        assertEquals(item2.getIdCounter()-1, item2.getId());
    }

    @Test
    public void test_toString() {
        Item item = new Item(ItemLevel.LOST, "Test");
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        assertEquals(String.format("#%d: Test (LOST) %s - hinzugefügt am %s | %s", item.getIdCounter()-1, DEFAULT_DESCRIPTION, date, DEFAULT_IMGPATH), item.toString());
    }

    @Test
    public void test_getDate() {
        Item item = new Item(ItemLevel.LOST, "Test");
        LocalDate date = LocalDate.now();
        assertEquals(date.toString(), item.getDate());
        assertEquals(date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), item.getDatePretty());
    }

    @Test
    public void test_editItem() {
        Item item = new Item(ItemLevel.LOST, "Test");
        item.editItem(ItemLevel.FOUND, "title", "desc", "img");
        assertEquals("title", item.getTitle());
        assertEquals("desc", item.getDescription());
        assertEquals("img", item.getImgPath());
        assertEquals(ItemLevel.FOUND, item.getCurrentStatus());
    }
}