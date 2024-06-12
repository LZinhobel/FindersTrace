// package at.ac.htl.bhitm.backend.user;

// import at.ac.htl.bhitm.backend.item.Item;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;

// import java.util.LinkedList;
// import java.util.List;

// import static org.junit.jupiter.api.Assertions.*;

// class UserTest {
//     private User user;
//     private Item item;

//     @BeforeEach
//     void setUp() {
//         user = new User("John", "Doe", "johndoe");
//         item = new Item();
//     }

//     @Test
//     void testCreateUser() {
//         assertNotNull(user);
//         assertEquals("John", user.getFirstname());
//         assertEquals("Doe", user.getLastname());
//         assertEquals("johndoe", user.getUsername());
//     }

//     @Test
//     void testSetAndGetFirstname() {
//         user.setFirstname("Jane");
//         assertEquals("Jane", user.getFirstname());
//     }

//     @Test
//     void testSetAndGetLastname() {
//         user.setLastname("Smith");
//         assertEquals("Smith", user.getLastname());
//     }

//     @Test
//     void testSetAndGetUsername() {
//         user.setUsername("janesmith");
//         assertEquals("janesmith", user.getUsername());
//     }

//     @Test
//     void testAddItem() {
//         user.addItem(item);
//         assertTrue(user.containsItem(item));
//     }

//     @Test
//     void testRemoveItem() {
//         user.addItem(item);
//         user.removeItem(item);
//         assertFalse(user.containsItem(item));
//     }
// }