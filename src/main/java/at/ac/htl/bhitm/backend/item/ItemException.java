package at.ac.htl.bhitm.backend.item;

public class ItemException extends RuntimeException {
    public ItemException(String message) {
        super(message);
    }

    public ItemException(String message, Throwable cause) {
        super(message, cause);
    }
}

