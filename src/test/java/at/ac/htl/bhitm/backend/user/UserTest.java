package at.ac.htl.bhitm.backend.user;

import at.ac.htl.bhitm.backend.item.Item;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    private User user;
    private Item item;

    @BeforeEach
    void setUp() {
        user = new User("John", "Doe", "johndoe");
        item = new Item();
    }

    @Test
    void testConstructor() {
        assertNotNull(user);
        assertEquals("John", user.getFirstname());
        assertEquals("Doe", user.getLastname());
        assertEquals("johndoe", user.getUsername());
        assertTrue(user.getItems().isEmpty());
    }

    @Test
    void testConstructorWithItems() {
        List<Item> items = new LinkedList<>();
        items.add(item);
        User userWithItems = new User("John", "Doe", "johndoe", items);
        assertNotNull(userWithItems);
        assertEquals(1, userWithItems.getItems().size());
    }

    @Test
    void testSettersAndGetters() {
        user.setFirstname("Jane");
        assertEquals("Jane", user.getFirstname());

        user.setLastname("Smith");
        assertEquals("Smith", user.getLastname());

        user.setUsername("janesmith");
        assertEquals("janesmith", user.getUsername());

        user.setEmail("jane@smith.com");
        assertEquals("jane@smith.com", user.getEmail());

        user.setPhonenumber("1234567890");
        assertEquals("1234567890", user.getPhonenumber());

        List<Item> items = new LinkedList<>();
        items.add(item);
        user.setItems(items);
        assertEquals(1, user.getItems().size());
    }

    @Test
    void testAddItem() {
        user.addItem(item);
        assertTrue(user.containsItem(item));
    }

    @Test
    void testRemoveItem() {
        user.addItem(item);
        user.removeItem(item);
        assertFalse(user.containsItem(item));
    }

    @Test
    void testSetId() {
        user.setId(1L);
        assertEquals(1L, user.getId());
    }

    @Test
    void testAddItemWithNull() {
        assertThrows(IllegalArgumentException.class, () -> user.addItem(null));
    }

    @Test
    void testSetFirstnameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> user.setFirstname(""));
    }

    @Test
    void testSetFirstnameNull() {
        assertThrows(IllegalArgumentException.class, () -> user.setFirstname(null));
    }

    @Test
    void testSetLastnameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> user.setLastname(""));
    }

    @Test
    void testSetLastnameNull() {
        assertThrows(IllegalArgumentException.class, () -> user.setLastname(null));
    }

    @Test
    void testSetPhonenumberEmpty() {
        assertThrows(IllegalArgumentException.class, () -> user.setPhonenumber(""));
    }

    @Test
    void testSetPhonenumberNull() {
        assertThrows(IllegalArgumentException.class, () -> user.setPhonenumber(null));
    }

    @Test
    void testSetPhonenumberInvalid() {
        assertThrows(IllegalArgumentException.class, () -> user.setPhonenumber("123ABC"));
    }

    @Test
    void testSetUsernameEmpty() {
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(""));
    }

    @Test
    void testSetUsernameNull() {
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(null));
    }

    @Test
    void testSetUsernameInvalid() {
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("username!"));
    }

    @Test
    void testSetEmailNull() {
        assertThrows(IllegalArgumentException.class, () -> user.setEmail(null));
    }

    @Test
    void testSetEmailInvalid() {
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("invalidEmail"));
    }
}