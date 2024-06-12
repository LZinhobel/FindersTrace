package at.ac.htl.bhitm.backend.user;

import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class UsersResourceTest {

    @Mock
    private UsersRepository usersRepository;

    @InjectMocks
    private UsersResource usersResource;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAll() {
        // Arrange
        List<User> expectedUsers = Collections.singletonList(new User());
        when(usersRepository.listAll()).thenReturn(expectedUsers);

        // Act
        List<User> users = usersResource.all();

        // Assert
        assertEquals(expectedUsers, users);
    }

    @Test
    public void testGetById() {
        // Arrange
        User expectedUser = new User();
        when(usersRepository.findById(anyLong())).thenReturn(expectedUser);

        // Act
        User user = usersResource.getById(1L);

        // Assert
        assertEquals(expectedUser, user);
    }

    @Test
    public void testRegister() {
        // Arrange
        User user = new User();
        doNothing().when(usersRepository).persist(any(User.class));

        // Act
        Response response = usersResource.register(user);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testDelete() {
        // Arrange
        User user = new User();
        when(usersRepository.findById(anyLong())).thenReturn(user);

        // Act
        Response response = usersResource.delete(1L);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        verify(usersRepository, times(1)).delete(user);
    }

    @Test
    public void testEdit() {
        // Arrange
        User user = new User();
        when(usersRepository.findById(anyLong())).thenReturn(user);
        doNothing().when(usersRepository).persist(user);

        // Act
        Response response = usersResource.edit(1L, user);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
    }

    @Test
    public void testLogin() {
        // Arrange
        User user = new User();
        when(usersRepository.login(anyString())).thenReturn(user);

        // Act
        User result = usersResource.login("testUser");

        // Assert
        assertEquals(user, result);
    }

    @Test
    public void testDeleteUserNotFound() {
        // Arrange
        when(usersRepository.findById(anyLong())).thenReturn(null);

        // Act
        Response response = usersResource.delete(1L);

        // Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testEditUserNotFound() {
        // Arrange
        User user = new User();
        when(usersRepository.findById(anyLong())).thenReturn(null);

        // Act
        Response response = usersResource.edit(1L, user);

        // Assert
        assertEquals(Response.Status.NOT_FOUND.getStatusCode(), response.getStatus());
    }

    @Test
    public void testEditUserWithFirstname() {
        // Arrange
        User user = new User();
        user.setFirstname("John");
        User userToEdit = new User();
        when(usersRepository.findById(anyLong())).thenReturn(userToEdit);

        // Act
        Response response = usersResource.edit(1L, user);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("John", userToEdit.getFirstname());
    }

    @Test
    public void testEditUserWithLastname() {
        // Arrange
        User user = new User();
        user.setLastname("Doe");
        User userToEdit = new User();
        when(usersRepository.findById(anyLong())).thenReturn(userToEdit);

        // Act
        Response response = usersResource.edit(1L, user);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("Doe", userToEdit.getLastname());
    }

    @Test
    public void testEditUserWithEmail() {
        // Arrange
        User user = new User();
        user.setEmail("john.doe@example.com");
        User userToEdit = new User();
        when(usersRepository.findById(anyLong())).thenReturn(userToEdit);

        // Act
        Response response = usersResource.edit(1L, user);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("john.doe@example.com", userToEdit.getEmail());
    }

    @Test
    public void testEditUserWithPhonenumber() {
        // Arrange
        User user = new User();
        user.setPhonenumber("1234567890");
        User userToEdit = new User();
        when(usersRepository.findById(anyLong())).thenReturn(userToEdit);

        // Act
        Response response = usersResource.edit(1L, user);

        // Assert
        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals("1234567890", userToEdit.getPhonenumber());
    }
}