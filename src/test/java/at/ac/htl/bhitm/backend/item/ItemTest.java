package at.ac.htl.bhitm.backend.item;

import at.ac.htl.bhitm.backend.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class ItemTest {

    @Test
    public void testConstructors() {
        Item item = new Item();
        assertNotNull(item);

        item = new Item(ItemLevel.LOST, "title");
        assertEquals(ItemLevel.LOST, item.getCurrentStatus());
        assertEquals("title", item.getTitle());

        item = new Item(ItemLevel.LOST, "title", "description");
        assertEquals(ItemLevel.LOST, item.getCurrentStatus());
        assertEquals("title", item.getTitle());
        assertEquals("description", item.getDescription());

        item = new Item(ItemLevel.LOST, "title", "description", "imgPath");
        assertEquals(ItemLevel.LOST, item.getCurrentStatus());
        assertEquals("title", item.getTitle());
        assertEquals("description", item.getDescription());
        assertEquals("imgPath", item.getImgPath());
    }

    @Test
    public void testGettersAndSetters() {
        Item item = new Item();
        item.setTitle("title");
        assertEquals("title", item.getTitle());

        item.setDescription("description");
        assertEquals("description", item.getDescription());

        item.setCurrentStatus(ItemLevel.FOUND);
        assertEquals(ItemLevel.FOUND, item.getCurrentStatus());

        item.setImgPath("imgPath");
        assertEquals("imgPath", item.getImgPath());

        User user = new User();
        item.setOwner(user);
        assertEquals(user, item.getOwner());
    }

    @Test
    public void testToString() {
        Item item = new Item(ItemLevel.LOST, "title", "description", "imgPath");
        item.setId(1L); // Set the id
        String expected = String.format("#%d: %s (%s) %s - hinzugefügt am %s | %s", 
                                        item.getId(), item.getTitle(), item.getCurrentStatus(), 
                                        item.getDescription(), item.getDatePretty(), item.getImgPath());
        assertEquals(expected, item.toString());
    }

    @Test
    public void testEditItem() {
        Item item = new Item();
        item.editItem(ItemLevel.FOUND, "newTitle", "newDescription", "newImgPath");
        assertEquals(ItemLevel.FOUND, item.getCurrentStatus());
        assertEquals("newTitle", item.getTitle());
        assertEquals("newDescription", item.getDescription());
        assertEquals("newImgPath", item.getImgPath());
    }

    @Test
    public void testAddItemToUser() {
        Item item = new Item();
        User user = new User();
        user.setItems(new ArrayList<>()); // Initialize the items list
        item.addItemToUser(user);
        assertTrue(user.getItems().contains(item));
    }

    @Test
    public void testSetDescription() {
        Item item = new Item();
        item.setDescription("description");
        assertEquals("description", item.getDescription());
        item.setDescription(null);
        assertEquals("No description available", item.getDescription());
        item.setDescription("");
        assertEquals("No description available", item.getDescription());
    }

    @Test
    public void testSetTitle() {
        Item item = new Item();
        item.setTitle("title");
        assertEquals("title", item.getTitle());
        assertThrows(ItemException.class, () -> item.setTitle(null));
        assertThrows(ItemException.class, () -> item.setTitle(""));
        assertThrows(ItemException.class, () -> item.setTitle("this title will be to long and will throw an exception"));
    }
}