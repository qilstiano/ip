package einstein.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a deadline. A <code>Deadline</code> object corresponds to a task
 * with a description and a due date/time.
 */
public class Deadline extends Task {
    public LocalDateTime by;

    /**
     * Constructs a <code>Deadline</code> object with a description and a due date/time.
     *
     * @param description The description of the task.
     * @param by          The due date and time for the task.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Returns a string representation of the deadline task, formatted as "[D] description (by: date)".
     * The date is formatted in "MMM dd yyyy, h:mm a" format.
     *
     * @return A string representation of the deadline task.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }
}
