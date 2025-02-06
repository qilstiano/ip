package einstein.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents a task with a specific time range. An <code>Event</code> object corresponds to a task
 * with a description and a start and end date/time.
 */
public class Event extends Task {
    public LocalDateTime from;
    public LocalDateTime to;

    /**
     * Constructs an <code>Event</code> object with a description, start date/time, and end date/time.
     *
     * @param description The description of the event.
     * @param from        The start date and time of the event.
     * @param to          The end date and time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Returns a string representation of the event, formatted as "[E] description (from: start_date to: end_date)".
     * The dates are formatted in "MMM dd yyyy, h:mm a" format.
     *
     * @return A string representation of the event.
     */
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[E]" + super.toString() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
    }
}
