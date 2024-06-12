package at.ac.htl.bhitm;

import java.util.ArrayList;
import java.util.Scanner;


import at.ac.htl.bhitm.backend.item.*;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static ItemsResource itemManager = new ItemsResource();

    public static void main(String[] args) {
        initialize();
        mainMenu();
    }

    private static void initialize() {
        clearConsole();
        System.out.println("Welcome to FindersTrace!");

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            scanner.close();
            throw new RuntimeException(e);
        }
    }

    private static void mainMenu() {
        String input = "";
        while (true) {
            System.out.println("--------------------------------------");
            System.out.println("Choose an option:");
            System.out.println("A: Report an item");
            System.out.println("B: Show items");
            System.out.println("C: Exit");
            input = getValidInput(false);

            switch (input.toLowerCase()) {
                case "a":
                    reportItem();
                    break;
                case "b":
                    showItems();
                    break;
                case "c":
                    exit();
                    break;
            }
        }
    }

    private static void reportItem() {
        clearConsole();
        System.out.println("Choose an option:");
        System.out.println("A: Report a lost item");
        System.out.println("B: Report a found item");
        System.out.println("C: Back");
        String input = getValidInput(false);

        switch (input.toLowerCase()) {
            case "a":
                reportLostItem();
                break;
            case "b":
                reportFoundItem();
                break;
        }
    }

    private static void reportLostItem() {
        Item newItem = getItemDetails(ItemLevel.LOST);
        itemManager.add(newItem);
    }

    private static void reportFoundItem() {
        Item newItem = getItemDetails(ItemLevel.FOUND);
        itemManager.add(newItem);
    }

    private static Item getItemDetails(ItemLevel level) {
        System.out.println("Enter the title of the item:");
        String title = getNonEmptyInput();

        System.out.println("Enter the description of the item (optional):");
        String description = scanner.nextLine().trim();

        System.out.println("Enter the URL of an image (type x if no img available):");
        String imgPath = scanner.nextLine().trim();

        return new Item(level, title, description, imgPath);
    }

    private static void showItems() {
        clearConsole();
        System.out.println("Choose an option:");
        System.out.println("A: Show all lost items");
        System.out.println("B: Show all found items");
        System.out.println("C: Show all items");
        System.out.println("D: Back");

        String input = getValidInput(true);

        ArrayList<Item> items = null;
        switch (input.toLowerCase()) {
            case "a":
                items = (ArrayList<Item>) itemManager.getByStatus(ItemLevel.LOST);
                break;
            case "b":
                items = (ArrayList<Item>) itemManager.getByStatus(ItemLevel.FOUND);
                break;
            case "c":
                items = (ArrayList<Item>) itemManager.all();
                break;
            case "d":
                return;
        }
        displayItems(items);
    }

    private static void displayItems(ArrayList<Item> items) {
        clearConsole();
        if (items == null || items.isEmpty()) {
            System.out.println("No items found!");
        } else {
            for (Item item : items) {
                System.out.println(item.toString());
            }
        }
    }

    private static void exit() {
        try {
            scanner.close();
            System.exit(0);
        } catch (Exception e) {
            System.out.println("Error while exiting! Couldn't write to file.");
        }
    }

    private static String getValidInput(boolean hasFourOptions) {
        String input = scanner.nextLine().trim();
        if (!(hasFourOptions && input.equalsIgnoreCase("d"))) {
            while (!validInput(input)) {
                System.out.println("Invalid input! Try again.");
                input = scanner.nextLine().trim();
            }
        }
        return input;
    }

    private static String getNonEmptyInput() {
        String input = scanner.nextLine().trim();
        while (input.isEmpty()) {
            System.out.println("Invalid input! Try again.");
            input = scanner.nextLine().trim();
        }
        return input;
    }

    private static boolean validInput(String input) {
        return input.equalsIgnoreCase("a") || input.equalsIgnoreCase("b") || input.equalsIgnoreCase("c");
    }

    private static void clearConsole() {
        System.out.print("\033[H\033[2J");  
        System.out.flush(); 
    }
}