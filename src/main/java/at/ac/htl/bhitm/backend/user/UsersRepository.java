package at.ac.htl.bhitm.backend.user;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UsersRepository implements PanacheRepository<User> {

    public User login(String username) {
        return find("username", username).firstResult();
    }
}