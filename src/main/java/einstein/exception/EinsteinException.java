package einstein.exception;

/**
 * Represents an exception specific to the Einstein application.
 * This exception is thrown when an invalid command or unexpected behavior occurs.
 */
public class EinsteinException extends Exception {

    /**
     * Constructs a new {@code EinsteinException} with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public EinsteinException(String message) {
        super(message);
    }
}
