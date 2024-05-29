package at.ac.htl.bhitm.backend.item;

public class ItemFactory {
    public Item createFromString(String line) {
        String[] parts = line.split(";");

        if (parts.length != 5 && parts.length != 4 && parts.length != 6) {
            throw new IllegalArgumentException("Invalid line format");
        }

        String title = parts[0].trim();
        String description = parts[1].trim();
        ItemLevel level = ItemLevel.valueOf(parts[2]);
        String imgPath = parts[3].trim();

        if ("null".equals(imgPath)) {
            imgPath = null;
        }

        Item item = new Item(level, title, description, imgPath);
        if  (parts.length == 5) {
            String dateAdded = parts[4].trim();
            item.setDate(dateAdded);
        }

        if (parts.length == 6) {
            int ownerId = Integer.parseInt(parts[5].trim());
            item.setOwnerId(ownerId);
        }
        return item;
    }

}
