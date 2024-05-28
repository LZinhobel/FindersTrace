package at.ac.htl.bhitm.backend.user;

import at.ac.htl.bhitm.backend.item.Item;
import at.ac.htl.bhitm.backend.item.ItemLevel;
import org.junit.jupiter.api.Test;

import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {
    @Test
    public void test_constructor_and_getters() {
        User user = new User(1, "firstname", "lastname", "username");
        assertEquals(1, user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("firstname", user.getFirstname());
        assertEquals("lastname", user.getLastname());
        assertEquals(new LinkedList<>(), user.getItems());
    }

    @Test
    public void test_constructor_with_items() {
        Item item = new Item(ItemLevel.LOST, "name", "description");
        LinkedList<Item> items = new LinkedList<>();

        User user = new User(1, "firstname", "lastname", "username", items);
        assertEquals(1, user.getId());
        assertEquals("username", user.getUsername());
        assertEquals("firstname", user.getFirstname());
        assertEquals("lastname", user.getLastname());
        assertEquals(items, user.getItems());
    }

    @Test
    public void test_setFirstname() {
        User user = new User(1, "firstname", "lastname", "username");
        user.setFirstname("newFirstname");
        assertEquals("newFirstname", user.getFirstname());
    }

    @Test
    public void test_setFirstname_null() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setFirstname(null));
    }

    @Test
    public void test_setFirstname_empty() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setFirstname(""));
    }

    @Test
    public void test_setLastname() {
        User user = new User(1, "firstname", "lastname", "username");
        user.setLastname("newLastname");
        assertEquals("newLastname", user.getLastname());
    }

    @Test
    public void test_setLastname_null() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setLastname(null));
    }

    @Test
    public void test_setLastname_empty() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setLastname(""));
    }

    @Test
    public void test_setUsername() {
        User user = new User(1, "firstname", "lastname", "username");
        user.setUsername("newUsername");
        assertEquals("newUsername", user.getUsername());
    }

    @Test
    public void test_setUsername_null() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(null));
    }

    @Test
    public void test_setUsername_empty() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setUsername(""));
    }

    @Test
    public void test_setUsername_not_valid() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setUsername("user name"));
    }

    @Test
    public void test_setPhonenumber() {
        User user = new User(1, "firstname", "lastname", "username");
        user.setPhonenumber("1234567890");
        assertEquals("1234567890", user.getPhonenumber());
    }

    @Test
    public void test_setPhonenumber_null() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setPhonenumber(null));
    }

    @Test
    public void test_setPhonenumber_empty() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setPhonenumber(""));
    }

    @Test
    public void test_setPhonenumber_not_only_numbers() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setPhonenumber("1234567890a"));
    }

    @Test
    public void test_setPhonenumber_not_only_numbers2() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setPhonenumber("1234567890!"));
    }

    @Test
    public void test_setEmail() {
        User user = new User(1, "firstname", "lastname", "username");
        user.setEmail("mail@mail.com");

        assertEquals("mail@mail.com", user.getEmail());
    }

    @Test
    public void test_setEmail_null() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setEmail(null));
    }

    @Test
    public void test_setEmail_empty() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setEmail(""));
    }

    @Test
    public void test_setEmail_not_valid() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("mail"));
    }

    @Test
    public void test_setEmail_not_valid2() {
        User user = new User(1, "firstname", "lastname", "username");
        assertThrows(IllegalArgumentException.class, () -> user.setEmail("mail@"));
    }
}
