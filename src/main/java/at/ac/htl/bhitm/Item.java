package at.ac.htl.bhitm;

public class Item {
    private String titel;
    private String description;
    private ItemLevel currentStatus;
    private String imgPath;
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

    public void setTitel(String titel) {
        if (titel == null || titel.isEmpty()) {
            throw new IllegalArgumentException("Titel must not be empty");
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
    }
}