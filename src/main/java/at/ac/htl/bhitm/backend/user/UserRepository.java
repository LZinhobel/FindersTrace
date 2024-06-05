package at.ac.htl.bhitm.backend.user;

import at.ac.htl.bhitm.backend.item.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class UserRepository {
    private static UserRepository instance;
    private LinkedList<User> users;
    private LinkedList<String> usernames;

    private UserRepository() {
        users = new LinkedList<>();
        usernames = new LinkedList<>();
        readFromFile();
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public User login(String username) {
        if (!usernames.contains(username)) {
            return null;
        }
        return users.get(usernames.indexOf(username));
    }

    private void readFromFile() {
        try {
            boolean isHeader = true;
            List<String> lines = Files.readAllLines(Paths.get("data/user.csv"));

            for(String line : lines) {
                if(isHeader) {
                    isHeader = false;
                } else {
                    users.add(createFromString(line));
                    usernames.add(line.split(";")[0]);
                }
            }
        } catch(IOException e) {
            throw new ItemException("Something happened while loading from file!", e);
        }
    }

    private User createFromString(String line) {
        String[] parts = line.split(";");

        User user = null;
        String username = parts[0];
        String firstname = parts[1];
        String lastname = parts[2];

        user = new User(firstname, lastname, username);

        if (parts.length < 7) {

            if (parts.length == 4) {
                if (!"null".equals(parts[3])) {
                    user.setEmail(parts[3]);
                }
            }

            if (parts.length == 5) {
                if (!"null".equals(parts[4])) {
                    user.setPhonenumber(parts[4]);
                }
            }

            if (parts.length == 6) {
                int id = Integer.parseInt(parts[5]);
                user.setId(id);
            }

        }

        return user;
    }

    public void register(User user) {
        users.add(user);
        usernames.add(user.getUsername());

        writeToFile();
    }

    public void writeToFile() {
        StringBuilder sb = new StringBuilder();
        sb.append("username;firstname;lastname;email;phonenumber\n");
        System.out.println("Writing to file");
        for(User user : users) {
            sb.append(user.getUsername()).append(";");
            System.out.println("username: " + user.getUsername());

            sb.append(user.getFirstname()).append(";");
            System.out.println("firstname: " + user.getFirstname());

            sb.append(user.getLastname()).append(";");
            System.out.println("lastname: " + user.getLastname());

            sb.append(user.getEmail()).append(";");
            System.out.println("email: " + user.getEmail());

            sb.append(user.getPhonenumber()).append(";");
            System.out.println("phonenumber: " + user.getPhonenumber());

            sb.append(user.getId()).append("\n");
            System.out.println("id: " + user.getId());
        }

        try {
            Files.write(Paths.get("data/user.csv"), sb.toString().getBytes());
        } catch(IOException e) {
            throw new ItemException("Something happened while writing to file!", e);
        }
    }

    public User getUserById(Integer index) {
        for(User user : users) {
            if(user.getId() == index) {
                return user;
            }
        }
        return null;
    }
}