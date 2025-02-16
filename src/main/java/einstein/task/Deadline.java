package einstein.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a deadline task in the Einstein task management system.
 * A deadline task is a task that needs to be completed by a specific date and time.
 */
public class Deadline extends Task {
    /** The date and time by which the task needs to be completed. */
    private LocalDateTime by;

    /**
     * Constructs a new Deadline task with the given description and due date/time.
     *
     * @param description The description of the deadline task.
     * @param by The date and time by which the task needs to be completed.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Gets the date and time by which the task needs to be completed.
     *
     * @return The LocalDateTime object representing the deadline.
     */
    public LocalDateTime getBy() {
        return by;
    }

    /**
     * Sets the date and time by which the task needs to be completed.
     *
     * @param by The new LocalDateTime object representing the deadline.
     */
    public void setBy(LocalDateTime by) {
        this.by = by;
    }

    /**
     * Returns a string representation of the Deadline task.
     *
     * @return A string that represents the Deadline task, including its description,
     *         status (done or not done), and the due date/time.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }
}
