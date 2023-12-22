package at.ac.htl.bhitm;

import java.time.LocalDateTime;

public class Item {
    private static int idCounter = 0;
    private int id;

    private String titel;
    private String description;
    private ItemLevel currentStatus;
    private String imgPath;
    private LocalDateTime dateAdded;

    private final static String DEFAULT_DESCRIPTION = "No description available";
    private final static String DEFAULT_IMGPATH = "No image available";

    public String getTitel() {
        return titel;
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

    public LocalDateTime getDateAdded() {
        return dateAdded;
    }

    public String getDateAddedPretty() {
        return dateAdded.getDayOfMonth() + "." + dateAdded.getMonthValue() + "." + dateAdded.getYear();
    }

    public void setTitel(String titel) {
        if (titel == null || titel.isEmpty()) {
            throw new IllegalArgumentException("Title must not be null or blank!");
        }
        this.titel = titel;
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
        this.imgPath = imgPath;
    }


    public Item(ItemLevel lvl, String title) {
        this(lvl, title, DEFAULT_DESCRIPTION);
    }

    public Item(ItemLevel lvl, String titel, String description) {
        this(lvl, titel, description, DEFAULT_IMGPATH);
    }

    public Item(ItemLevel lvl, String titel, String description, String imgPath) {
        setCurrentStatus(lvl);
        setTitel(titel);
        setDescription(description);
        setImgPath(imgPath);
        ++idCounter;
        id = idCounter;
        dateAdded = LocalDateTime.now();
    }

    @java.lang.Override
    public java.lang.String toString() {
        return String.format("#%d: %s (%s) - added on %s", id, titel, currentStatus, this.getDateAddedPretty());
    }
}