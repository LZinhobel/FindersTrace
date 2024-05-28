package at.ac.htl.bhitm.backend.item;

import at.ac.htl.bhitm.backend.item.ItemException;
import at.ac.htl.bhitm.backend.item.ItemLevel;

import java.time.LocalDate;

public class Item {
    private static int idCounter = 0;
    private int id;

    private String title;
    private String description;
    private ItemLevel currentStatus;
    private String imgPath;
    private String dateAdded;

    private final static int MAX_TITLE_LENGTH = 20;
    private final static String DEFAULT_DESCRIPTION = "No description available";
    private final static String DEFAULT_IMGPATH = "https://upload.wikimedia.org/wikipedia/commons/thumb/a/ac/No_image_available.svg/1200px-No_image_available.svg.png";

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public ItemLevel getCurrentStatus() {
        return currentStatus;
    }

    public String getImgPath() {
        return imgPath;
    }

    public int getId() {
        return id;
    }

    public int getIdCounter() {
        return idCounter;
    }

    public String getDate() {
        return dateAdded;
    }

    public String getDatePretty() {
        String[] date = dateAdded.split("-");
        return String.format("%s.%s.%s", date[2], date[1], date[0]);
    }

    public void setDate(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    public void setTitle(String title) {
        if (title == null || title.isEmpty()) {
            throw new ItemException("Title must not be null or blank!");
        }
        if (title.length() >= MAX_TITLE_LENGTH) {
            throw new ItemException("Title must not be longer than " + MAX_TITLE_LENGTH + " characters!");
        }
        this.title = title;
    }

    public void setDescription(String description) {
        if (description == null || description.isEmpty()) {
            description = DEFAULT_DESCRIPTION;
        }
        this.description = description;
    }

    public void setCurrentStatus(ItemLevel currentStatus) {
        this.currentStatus = currentStatus;
    }

    public void setImgPath(String imgPath) {
        if  (imgPath == null) {
            this.imgPath = DEFAULT_IMGPATH;
        } else {
            this.imgPath = imgPath;
        }
    }


    public Item(ItemLevel lvl, String title) {
        this(lvl, title, DEFAULT_DESCRIPTION);
    }

    public Item(ItemLevel lvl, String titel, String description) {
        this(lvl, titel, description, DEFAULT_IMGPATH);
    }

    public Item(ItemLevel lvl, String titel, String description, String imgPath) {
        setCurrentStatus(lvl);
        setTitle(titel);
        setDescription(description);
        setImgPath(imgPath);
        id = idCounter;
        ++idCounter;
        dateAdded = LocalDate.now().toString();
    }

    @java.lang.Override
    public java.lang.String toString() {
        return String.format("#%d: %s (%s) %s - hinzugefügt am %s | %s", id, title, currentStatus, description, getDatePretty(), imgPath);
    }

    public void editItem(ItemLevel lvl, String titel, String description, String imgPath) {
        setCurrentStatus(lvl);
        setTitle(titel);
        setDescription(description);
        setImgPath(imgPath);
    }
}