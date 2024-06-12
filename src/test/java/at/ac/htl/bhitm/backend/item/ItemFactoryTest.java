package at.ac.htl.bhitm.backend.item;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ItemFactoryTest {

    @Test
    public void testCreateFromString() {
        ItemFactory factory = new ItemFactory();

        String line = "title;description;LOST;imgPath;2022-01-01";
        Item item = factory.createFromString(line);
        assertEquals("title", item.getTitle());
        assertEquals("description", item.getDescription());
        assertEquals(ItemLevel.LOST, item.getCurrentStatus());
        assertEquals("imgPath", item.getImgPath());
        assertEquals("2022-01-01", item.getDate());

        line = "title;description;FOUND;null";
        item = factory.createFromString(line);
        assertEquals("title", item.getTitle());
        assertEquals("description", item.getDescription());
        assertEquals(ItemLevel.FOUND, item.getCurrentStatus());
        assertEquals("https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1200px-No_image_available.svg.png", item.getImgPath());
    }

    @Test
    public void testCreateFromStringInvalidFormat() {
        ItemFactory factory = new ItemFactory();

        String line = "title;description;LOST";
        assertThrows(IllegalArgumentException.class, () -> factory.createFromString(line));

        assertThrows(IllegalArgumentException.class, () -> factory.createFromString("title;description;LOST;imgPath;2022-01-01;extra"));
    }
}