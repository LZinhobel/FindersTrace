package at.ac.htl.bhitm.backend.item;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ItemsResourceTest {

    @Mock
    private ItemsRepository itemsRepository;

    @InjectMocks
    private ItemsResource itemsResource;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAll() {
        // Arrange
        List<Item> expectedItems = Arrays.asList(new Item(), new Item());
        when(itemsRepository.listAll()).thenReturn(expectedItems);

        // Act
        List<Item> items = itemsResource.all();

        // Assert
        assertEquals(expectedItems, items);
    }

    @Test
    public void testGetById() {
        // Arrange
        Item expectedItem = new Item();
        when(itemsRepository.findById(anyLong())).thenReturn(expectedItem);

        // Act
        Item item = itemsResource.getById(1L);

        // Assert
        assertEquals(expectedItem, item);
    }

    @Test
    public void testAdd() {
        // Act
        Response response = itemsResource.add(new Item());

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(itemsRepository, times(1)).persist(any(Item.class));
    }

    @Test
    public void testDelete() {
        // Arrange
        Item item = new Item();
        when(itemsRepository.findById(anyLong())).thenReturn(item);

        // Act
        Response response = itemsResource.delete(1L);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(itemsRepository, times(1)).delete(item);
    }

    @Test
    public void testDeleteNotFound() {
        // Arrange
        when(itemsRepository.findById(anyLong())).thenReturn(null);

        // Act
        Response response = itemsResource.delete(1L);

        // Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testGetByStatus() {
        // Arrange
        List<Item> expectedItems = Arrays.asList(new Item(), new Item());
        when(itemsRepository.findByStatus(any(ItemLevel.class))).thenReturn(expectedItems);

        // Act
        List<Item> items = itemsResource.getByStatus(ItemLevel.LOST);

        // Assert
        assertEquals(expectedItems, items);
    }

    @Test
    public void testEdit() {
        // Arrange
        Item itemToEdit = new Item();
        when(itemsRepository.findById(anyLong())).thenReturn(itemToEdit);

        // Act
        Response response = itemsResource.edit(1L, new Item());

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(itemsRepository, times(1)).persist(itemToEdit);
    }

    @Test
    public void testEditNotFound() {
        // Arrange
        when(itemsRepository.findById(anyLong())).thenReturn(null);

        // Act
        Response response = itemsResource.edit(1L, new Item());

        // Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testEditTitle() {
        // Arrange
        Item itemToEdit = new Item();
        itemToEdit.setTitle("Old Title");
        when(itemsRepository.findById(anyLong())).thenReturn(itemToEdit);

        Item newItem = new Item();
        newItem.setTitle("New Title");

        // Act
        Response response = itemsResource.edit(1L, newItem);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("New Title", itemToEdit.getTitle());
    }

    @Test
    public void testEditDescription() {
        // Arrange
        Item itemToEdit = new Item();
        itemToEdit.setDescription("Old Description");
        when(itemsRepository.findById(anyLong())).thenReturn(itemToEdit);

        Item newItem = new Item();
        newItem.setDescription("New Description");

        // Act
        Response response = itemsResource.edit(1L, newItem);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("New Description", itemToEdit.getDescription());
    }

    @Test
    public void testEditCurrentStatus() {
        // Arrange
        Item itemToEdit = new Item();
        itemToEdit.setCurrentStatus(ItemLevel.LOST);
        when(itemsRepository.findById(anyLong())).thenReturn(itemToEdit);

        Item newItem = new Item();
        newItem.setCurrentStatus(ItemLevel.GIVENBACK);

        // Act
        Response response = itemsResource.edit(1L, newItem);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ItemLevel.GIVENBACK, itemToEdit.getCurrentStatus());
    }

    @Test
    public void testEditImgPath() {
        // Arrange
        Item itemToEdit = new Item();
        itemToEdit.setImgPath("Old Path");
        when(itemsRepository.findById(anyLong())).thenReturn(itemToEdit);

        Item newItem = new Item();
        newItem.setImgPath("New Path");

        // Act
        Response response = itemsResource.edit(1L, newItem);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("New Path", itemToEdit.getImgPath());
    }

    @Test
    public void testEditCurrentStatusNull() {
        // Arrange
        Item itemToEdit = new Item();
        itemToEdit.setCurrentStatus(ItemLevel.LOST);
        when(itemsRepository.findById(anyLong())).thenReturn(itemToEdit);

        Item newItem = new Item();
        newItem.setCurrentStatus(null);

        // Act
        Response response = itemsResource.edit(1L, newItem);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(ItemLevel.LOST, itemToEdit.getCurrentStatus());
    }

    @Test
    public void testEditImgPathBlank() {
        // Arrange
        Item itemToEdit = new Item();
        itemToEdit.setImgPath("Old Path");
        when(itemsRepository.findById(anyLong())).thenReturn(itemToEdit);

        Item newItem = new Item();
        newItem.setImgPath("");

        // Act
        Response response = itemsResource.edit(1L, newItem);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Old Path", itemToEdit.getImgPath());
    }
}