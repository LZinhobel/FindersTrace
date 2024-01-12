package at.ac.htl.bhitm;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ItemFactory {
    public Item createFromString(String line) {
        String[] parts = line.split(";");
        String title = parts[0].trim();
        String description = parts[1].trim();
        ItemLevel level = ItemLevel.valueOf(parts[2]);
        String imgPath = parts[3].trim();
        String dateAdded = parts[4].trim();

        Item item = new Item(level, title, description, imgPath);

        item.setDate(dateAdded);

        return item;
    }

}
