package at.ac.htl.bhitm.backend.item;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/items")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ItemsResource {
    @Inject
    ItemsRepository itemsRepository;

    public ItemsResource() {
        this.itemsRepository = new ItemsRepository();
    }

    @GET
    public List<Item> all() {
        return itemsRepository.listAll();
    }

    @Path("/{id}")
    @GET
    public Item getById(@PathParam("id") Long id) {
        return itemsRepository.findById(id);
    }

    @Transactional
    @POST
    public Response add(Item item) {
        itemsRepository.persist(item);

        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    public Response delete(@PathParam("id") long id) {
        Item item = itemsRepository.findById(id);

        if (item == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        itemsRepository.delete(item);
        return Response.ok().build();
    }

    @Path("/status/{status}")
    @GET
    public List<Item> getByStatus(@PathParam("status") ItemLevel status) {
        return itemsRepository.findByStatus(status);
    }

    @Path("/{id}")
    @PUT
    @Transactional
    public Response edit(@PathParam("id") long id, Item item) {
        Item itemToEdit = itemsRepository.findById(id);

        if (itemToEdit == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        itemToEdit.setTitle(item.getTitle());
        itemToEdit.setDescription(item.getDescription());
        if (item.getCurrentStatus() != null) itemToEdit.setCurrentStatus(item.getCurrentStatus());
        if (!item.getImgPath().isBlank()) itemToEdit.setImgPath(item.getImgPath());

        itemToEdit = itemsRepository.getEntityManager().merge(itemToEdit);
        itemsRepository.persist(itemToEdit);

        return Response.ok().build();
    }
}