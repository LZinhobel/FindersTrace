// package at.ac.htl.bhitm.backend.item;

// import at.ac.htl.bhitm.backend.user.User;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;

// import static org.junit.jupiter.api.Assertions.*;

// class ItemTest {
//     private Item item;
//     private User user;

//     @BeforeEach
//     void setUp() {
//         user = Mockito.mock(User.class);
//         Mockito.when(user.getFirstname()).thenReturn("Test Firstname");
//         item = new Item(ItemLevel.LOST, "Test Item", "Test Description", "Test Image Path");
//         item.setOwner(user);
//     }

//     @Test
//     void getTitle() {
//         assertEquals("Test Item", item.getTitle());
//     }

//     @Test
//     void getDescription() {
//         assertEquals("Test Description", item.getDescription());
//     }

//     @Test
//     void getCurrentStatus() {
//         assertEquals(ItemLevel.LOST, item.getCurrentStatus());
//     }

//     @Test
//     void getImgPath() {
//         assertEquals("Test Image Path", item.getImgPath());
//     }

//     @Test
//     void getDate() {
//         assertNotNull(item.getDate());
//     }

//     @Test
//     void getDatePretty() {
//         assertNotNull(item.getDatePretty());
//     }

//     @Test
//     void setDate() {
//         item.setDate("2022-01-01");
//         assertEquals("2022-01-01", item.getDate());
//     }

//     @Test
//     void setTitle() {
//         item.setTitle("New Title");
//         assertEquals("New Title", item.getTitle());
//     }

//     @Test
//     void setDescription() {
//         item.setDescription("New Description");
//         assertEquals("New Description", item.getDescription());
//     }

//     @Test
//     void setCurrentStatus() {
//         item.setCurrentStatus(ItemLevel.LOST);
//         assertEquals(ItemLevel.LOST, item.getCurrentStatus());
//     }

//     @Test
//     void setImgPath() {
//         item.setImgPath("New Image Path");
//         assertEquals("New Image Path", item.getImgPath());
//     }

//     @Test
//     void toStringTest() {
//         assertNotNull(item.toString());
//     }

//     @Test
//     void editItem() {
//         item.editItem(ItemLevel.FOUND, "Edited Title", "Edited Description", "Edited Image Path");
//         assertEquals(ItemLevel.FOUND, item.getCurrentStatus());
//         assertEquals("Edited Title", item.getTitle());
//         assertEquals("Edited Description", item.getDescription());
//         assertEquals("Edited Image Path", item.getImgPath());
//     }

//     @Test
//     void addItemToUser() {
//         User newUser = Mockito.mock(User.class);
//         item.addItemToUser(newUser);
//         Mockito.verify(newUser).addItem(item);
//     }
// }