package at.ac.htl.bhitm.backend.item;

import at.ac.htl.bhitm.backend.user.User;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "items")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private User owner;

    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private ItemLevel currentStatus;
    private String imgPath;
    private LocalDate dateAdded;

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

    public String getDate() {
        return dateAdded.toString();
    }

    public String getDatePretty() {
        String[] date = dateAdded.toString().split("-");
        return String.format("%s.%s.%s", date[2], date[1], date[0]);
    }

    public void setDate(String dateAdded) {
        this.dateAdded = LocalDate.parse(dateAdded);
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

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public Item() {
        this(ItemLevel.LOST, "No title");
    }

    public Item(ItemLevel lvl, String title) {
        this(lvl, title, DEFAULT_DESCRIPTION);
    }

    public Item(ItemLevel lvl, String title, String description) {
        this(lvl, title, description, DEFAULT_IMGPATH);
    }

    public Item(ItemLevel lvl, String title, String description, String imgPath) {
        setCurrentStatus(lvl);
        setTitle(title);
        setDescription(description);
        setImgPath(imgPath);
        dateAdded = LocalDate.now();
    }

    @Override
    public String toString() {
        return String.format("#%d: %s (%s) %s - hinzugefügt am %s | %s", id, title, currentStatus, description, getDatePretty(), imgPath);
    }

    public void editItem(ItemLevel lvl, String titel, String description, String imgPath) {
        setCurrentStatus(lvl);
        setTitle(titel);
        setDescription(description);
        setImgPath(imgPath);
    }

    public void setOwner(User user) {
        owner = user;
    }

    public User getOwner() {
        return owner;
    }

    public void addItemToUser(User user) {
        user.addItem(this);
    }
}