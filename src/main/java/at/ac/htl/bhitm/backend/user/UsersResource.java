package at.ac.htl.bhitm.backend.user;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
public class UsersResource {
    @Inject
    UsersRepository usersRepository;

    public UsersResource() {
        this.usersRepository = new UsersRepository();
    }

    @GET
    public List<User> all() {
        return usersRepository.listAll();
    }

    @Path("/{id}")
    @GET
    public User getById(@PathParam("id") Long id) {
        return usersRepository.findById(id);
    }

    @Transactional
    @POST
    public Response register(User user) {
        usersRepository.persist(user);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") long id) {
        User user = usersRepository.findById(id);

        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        usersRepository.delete(user);
        return Response.ok().build();
    }

    @Path("/{id}")
    @PUT
    @Transactional
    public Response edit(@PathParam("id") long id, User user) {
        User userToEdit = usersRepository.findById(id);

        if (userToEdit == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (user.getFirstname() != null && !user.getFirstname().isEmpty()) {
            userToEdit.setFirstname(user.getFirstname());
        }
        if (user.getLastname() != null && !user.getLastname().isEmpty()) {
            userToEdit.setLastname(user.getLastname());
        }
        if (user.getEmail() != null && !user.getEmail().isEmpty()) {
            userToEdit.setEmail(user.getEmail());
        }
        if (user.getPhonenumber() != null && !user.getPhonenumber().isEmpty()) {
            userToEdit.setPhonenumber(user.getPhonenumber());
        }


        usersRepository.persist(userToEdit);

        return Response.ok().build();
    }

    @Path("/login")
    @GET
    public User login(@QueryParam("username") String username) {
        return usersRepository.login(username);
    }
}