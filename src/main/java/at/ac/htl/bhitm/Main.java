package at.ac.htl.bhitm;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;


/*
User Inputs werden nicht richtig gelesen
auch bei richtigen input wird trotzdem "Invalid input! Try again." ausgegeben
einfach ausprobieren und schauen was passiert
 */

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ItemManager itemManager = new ItemManager();
//        itemManager.AddItemsFromFile("data/reportedItems.csv", new ItemFactory()); funktioniert nicht, Exception wird imma geworfen
        
        System.out.println("Welcome to FindersTrace!");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Choose an option:");
        System.out.println("A: Report an item");
        System.out.println("B: All items");
        System.out.println("C: Exit");
        String input = scanner.nextLine().trim();

        while (!(input.equalsIgnoreCase("a") || input.equalsIgnoreCase("b") || input.equalsIgnoreCase("c"))) {
            System.out.println("Invalid input! Try again.");
            input = scanner.nextLine().trim();
        }
        input = input.toLowerCase();
        switch (input) {
            case "a":
                System.out.println("Choose an option:");
                System.out.println("A: Report a lost item");
                System.out.println("B: Report a found item");
                System.out.println("C: Back");
                input = scanner.nextLine().trim();

                while (!input.equalsIgnoreCase("a") || !input.equalsIgnoreCase("b") || !input.equalsIgnoreCase("c")) {
                    System.out.println("Invalid input! Try again.");
                    input = scanner.nextLine().trim();
                }

                switch (input) {
                    case "a":
                        System.out.println("Enter the title of the item:");
                        String title = scanner.nextLine().trim();
                        while (title.isEmpty()) {
                            System.out.println("Invalid input! Try again.");
                            title = scanner.nextLine().trim();
                        }

                        System.out.println("Enter the description of the item (optional):");
                        String description = scanner.nextLine().trim();

                        Item newItem = new Item(ItemLevel.LOST, title, description);
                        itemManager.addItem(newItem);
                        break;
                    case "b":
                        System.out.println("Enter the title of the item:");
                        title = scanner.nextLine().trim();
                        while (title.isEmpty()) {
                            System.out.println("Invalid input! Try again.");
                            title = scanner.nextLine().trim();
                        }

                        System.out.println("Enter the description of the item (optional):");
                        description = scanner.nextLine().trim();

                        newItem = new Item(ItemLevel.FOUND, title, description);
                        itemManager.addItem(newItem);
                        break;
                    case "c":
                        break;
                }
                break;
            case "b":
                System.out.println("Choose an option:");
                System.out.println("A: Show all lost items");
                System.out.println("B: Show all found items");
                System.out.println("C: Show all items");
                System.out.println("D: Back");
                input = scanner.nextLine().trim();
                while (!input.equalsIgnoreCase("a") || !input.equalsIgnoreCase("b") || !input.equalsIgnoreCase("c") || !input.equalsIgnoreCase("d")) {
                    System.out.println("Invalid input! Try again.");
                    input = scanner.nextLine().trim();
                }
                ArrayList<Item> lostItems = null;
                switch (input) {
                    case "a":
                        lostItems =
                        itemManager.getItemsByStatus(ItemLevel.LOST);
                        break;
                    case "b":
                        lostItems =
                        itemManager.getItemsByStatus(ItemLevel.FOUND);
                        break;
                    case "c":
                        lostItems =
                        itemManager.getItems();
                        break;
                    case "d":
                        break;
                }
                if (lostItems != null && lostItems.isEmpty()) {
                    System.out.println("No items found!");
                } else {
                    for (Item item : lostItems) {
                        System.out.println(item.toString());
                    }
                }
                break;
            case "c":
                System.exit(0);
                break;
        }
        scanner.close();
    }
}
