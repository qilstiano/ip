package einstein.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Represents an event task in the Einstein task management system.
 * An event task is a task that occurs during a specific time period.
 */
public class Event extends Task {

    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructs a new Event task with the given description, start time, and end time.
     *
     * @param description The description of the event task.
     * @param from The start date and time of the event.
     * @param to The end date and time of the event.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        assert !description.trim().isEmpty() : "Description cannot be null or empty";
        assert from != null : "Start date/time cannot be null";
        assert to != null : "End date/time cannot be null";
        assert !to.isBefore(from) : "End date/time cannot be before start date/time";
        this.from = from;
        this.to = to;
    }

    /**
     * Gets the start date and time of the event.
     *
     * @return The LocalDateTime object representing the start of the event.
     */
    public LocalDateTime getFrom() {
        assert from != null : "Start date/time should not be null";
        return from;
    }

    /**
     * Sets the start date and time of the event.
     *
     * @param from The new LocalDateTime object representing the start of the event.
     */
    public void setFrom(LocalDateTime from) {
        assert from != null : "New start date/time cannot be null";
        assert !to.isBefore(from) : "New start date/time cannot be after end date/time";
        this.from = from;
    }

    /**
     * Gets the end date and time of the event.
     *
     * @return The LocalDateTime object representing the end of the event.
     */
    public LocalDateTime getTo() {
        assert to != null : "End date/time should not be null";
        return to;
    }

    /**
     * Sets the end date and time of the event.
     *
     * @param to The new LocalDateTime object representing the end of the event.
     */
    public void setTo(LocalDateTime to) {
        assert to != null : "New end date/time cannot be null";
        assert !to.isBefore(from) : "New end date/time cannot be before start date/time";
        this.to = to;
    }

    /**
     * Returns a string representation of the Event task.
     *
     * @return A string that represents the Event task, including its description,
     *         status (done or not done), and the start and end date/time.
     */
    @Override
    public String toString() {
        assert from != null && to != null : "Start and end date/time should not be null when converting to string";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[E]" + super.toString() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
    }
}
