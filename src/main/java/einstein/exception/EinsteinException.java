package einstein.exception;

/**
 * Represents a custom exception for the Einstein task management system.
 * This exception is used to handle specific errors that may occur during
 * the execution of the Einstein application.
 */
public class EinsteinException extends Exception {

    /**
     * Constructs a new EinsteinException with the specified error message.
     *
     * @param message The error message describing the exception.
     */
    public EinsteinException(String message) {
        super(message);
    }
}
