package heisenberg;

/** Indicates that tasks could not be loaded from or saved to disk. */
public class StorageException extends RuntimeException {
    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
