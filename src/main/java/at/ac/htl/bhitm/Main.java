package at.ac.htl.bhitm;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ItemManager itemManager = new ItemManager();
        try {
            itemManager.AddItemsFromFile("data/reportedItems.csv", new ItemFactory());
        } catch (Exception e) {
            System.out.println("Error while starting! Couldn't read file.");
        }

        System.out.println("Welcome to FindersTrace!");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        String input = "";
        while (true) {
            System.out.println("--------------------------------------");
            System.out.println("Choose an option:");
            System.out.println("A: Report an item");
            System.out.println("B: Show items");
            System.out.println("C: Exit");
            input = scanner.nextLine().trim();

            while (!validInput(input)) {
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

                    while (!validInput(input)) {
                        System.out.println("Invalid input! Try again.");
                        input = scanner.nextLine().trim();
                    }
                    input = input.toLowerCase();
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

                            System.out.println("Enter the URL of an image (type x if no img available):");
                            String imgPath = scanner.nextLine().trim();

                            Item newItem = new Item(ItemLevel.LOST, title, description, imgPath);
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

                            System.out.println("Enter the URL of an image (type x if no img available):");
                            imgPath = scanner.nextLine().trim();

                            newItem = new Item(ItemLevel.FOUND, title, description, imgPath);
                            itemManager.addItem(newItem);
                            break;
                        case "c":
                            input = "x";
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

                    while (!input.equalsIgnoreCase("d") && !validInput(input)) {
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
                    System.out.println("\n\n\n\n\n\n\n");
                    if (lostItems == null || lostItems.isEmpty()) {
                        System.out.println("No items found!");
                    } else {
                        for (Item item : lostItems) {
                            System.out.println(item.toString());
                        }
                    }
                    break;
                case "c":
                    try {
                        itemManager.AddItemsToFile("data/reportedItems.csv");
                        scanner.close();
                        System.exit(0);
                    } catch (Exception e) {
                        System.out.println("Error while exiting! Couldn't write to file.");
                    }
                    break;
            }
        }
    }

    private static boolean validInput(String input) {
        return input.equalsIgnoreCase("a") || input.equalsIgnoreCase("b") || input.equalsIgnoreCase("c");
    }
}
