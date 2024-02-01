package at.ac.htl.bhitm.backend;

public class ItemFactory {
    public Item createFromString(String line) {
        String[] parts = line.split(";");

        if (parts.length != 5 && parts.length != 4) {
            throw new IllegalArgumentException("Invalid line format");
        }

        String title = parts[0].trim();
        String description = parts[1].trim();
        ItemLevel level = ItemLevel.valueOf(parts[2]);
        String imgPath = parts[3].trim();

        Item item = new Item(level, title, description, imgPath);
        if  (parts.length == 5) {
            String dateAdded = parts[4].trim();
            item.setDate(dateAdded);
        }
        return item;
    }

}
