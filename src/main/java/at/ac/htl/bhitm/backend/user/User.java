package at.ac.htl.bhitm.backend.user;

import at.ac.htl.bhitm.backend.item.Item;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

public class User {
    private int id;
    private String username;
    private String email;
    private LinkedList<Item> items;
    private String firstname;
    private String lastname;
    private String phonenumber;

    public User(int id, String firstname, String lastname, String username, LinkedList<Item> items) {
        this.id = id;
        setFirstname(firstname);
        setLastname(lastname);
        setUsername(username);
        setItems(items);
    }

    public User(int id, String firstname, String lastname, String username) {
        this(id, firstname, lastname, username, new LinkedList<>());
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        if (firstname == null || firstname.isEmpty()) {
            throw new IllegalArgumentException("Firstname must not be empty");
        }
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        if (lastname == null || lastname.isEmpty()) {
            throw new IllegalArgumentException("Lastname must not be empty");
        }
        this.lastname = lastname;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        if (phonenumber == null || phonenumber.isEmpty()) {
            throw new IllegalArgumentException("Phonenumber must not be empty");
        }
        if (!phonenumber.matches("[0-9]+")) {
            throw new IllegalArgumentException("Phonenumber must only contain numbers");
        }
        this.phonenumber = phonenumber;
    }

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username == null || username.isEmpty()) {
            throw new IllegalArgumentException("Username must not be empty");
        }
        if (!username.matches("[a-zA-Z0-9]+")) {
            throw new IllegalArgumentException("Username must only contain letters and numbers");
        }
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (email == null || !pattern.matcher(email).matches()) {
            throw new IllegalArgumentException("Email is not valid");
        }
        this.email = email;
    }

    public LinkedList<Item> getItems() {
        return items;
    }

    public void setItems(LinkedList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item must not be null");
        }
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public boolean containsItem(Item item) {
        return items.contains(item);
    }
}