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
        } else if (parts.length > 3 && parts.length < 7) {
            String itemids = parts[3];
            LinkedList<Item> itemList = new LinkedList<>();

            ItemManager mng = new ItemManager();
            mng.AddItemsFromFile("data/reportedItems.csv", new ItemFactory());

            for (String itemid : itemids.split(",")) {
                itemList.add(mng.getItemById(Integer.parseInt(itemid)));
            }

            user = new User(firstname, lastname, username, itemList);

            if (parts.length == 5) {
                user.setEmail(parts[4]);
            } else if (parts.length == 6) {
                user.setPhonenumber(parts[5]);
            }

        }

        return user;
    }
}
