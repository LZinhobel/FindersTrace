package at.ac.htl.bhitm;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemManager {
    private HashMap<Integer, Item> items;

    public ItemManager() {
        items = new HashMap<>();
    }

    public void addItem(Item item) {
        items.put(item.getId(), item);
    }

    public void removeItem(Item item) {
        items.remove(item.getId());
    }

    public void removeItemById(int id) {
        items.remove(id);
    }

    public Item getItemById(int id) {
        return items.get(id);
    }

    public ArrayList<Item> getItems() {
        return new ArrayList<>(items.values());
    }
}