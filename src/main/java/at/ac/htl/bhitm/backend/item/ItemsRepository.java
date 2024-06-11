package at.ac.htl.bhitm.backend.item;

import java.util.List;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ItemsRepository implements PanacheRepository<Item> {

    public List<Item> findByStatus(ItemLevel status) {
        return list("currentStatus", status);
    }
}