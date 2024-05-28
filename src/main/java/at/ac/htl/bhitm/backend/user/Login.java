package at.ac.htl.bhitm.backend.user;

import at.ac.htl.bhitm.backend.item.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Login {
    private static Login instance;
    private LinkedList<User> users;
    private LinkedList<String> usernames;

    private Login() {
        users = new LinkedList<>();
        usernames = new LinkedList<>();
        readFromFile();
    }

    public static Login getInstance() {
        if (instance == null) {
            instance = new Login();
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


        if (parts.length == 3) {
            user = new User(firstname, lastname, username);
        } else if (parts.length < 7) {
            String itemids = parts[3];
            LinkedList<Item> itemList = new LinkedList<>();

            ItemManager mng = new ItemManager();
            mng.AddItemsFromFile("data/reportedItems.csv", new ItemFactory());

            if (!itemids.isBlank()) {
                for (String itemid : itemids.split(",")) {
                    itemList.add(mng.getItemById(Integer.parseInt(itemid)));
                }
            }

            user = new User(firstname, lastname, username, itemList);

            if (parts.length == 5) {
                if (!"null".equals(parts[4])) {
                    user.setEmail(parts[4]);
                }
            } else if (parts.length == 6) {
                if (!"null".equals(parts[5])) {
                    user.setPhonenumber(parts[5]);
                }
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
        sb.append("username;firstname;lastname;itemids;email;phonenumber\n");
        System.out.println("Writing to file");
        for(User user : users) {
            sb.append(user.getUsername()).append(";");
            System.out.println("username: " + user.getUsername());

            sb.append(user.getFirstname()).append(";");
            System.out.println("firstname: " + user.getFirstname());

            sb.append(user.getLastname()).append(";");
            System.out.println("lastname: " + user.getLastname());

            LinkedList<Item> items = user.getItems();
            if (!items.isEmpty()) {
                System.out.println("adding items");
                for(Item item : items) {
                    sb.append(item.getId()).append(",");
                    System.out.println("item: " + item.getId());
                }
                sb.deleteCharAt(sb.length() - 1);
            }
            sb.append(";");
            sb.append(user.getEmail()).append(";");
            System.out.println("email: " + user.getEmail());

            sb.append(user.getPhonenumber()).append("\n");
            System.out.println("phonenumber: " + user.getPhonenumber());
        }

        try {
            Files.write(Paths.get("data/user.csv"), sb.toString().getBytes());
        } catch(IOException e) {
            throw new ItemException("Something happened while writing to file!", e);
        }
    }
}