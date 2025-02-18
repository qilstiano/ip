package einstein.command;

// Imports for Date and Time

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.task.Event;
import einstein.task.Task;
import einstein.ui.Ui;

/**
 * Represents a command to add an event task in the Einstein task management system.
 */
public class AddEventCommand implements Command {
    private String description;
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Constructs a new AddEventCommand.
     *
     * @param fullCommand The full command string from the user input.
     * @throws EinsteinException If the command format is invalid or the date/time is improperly formatted.
     */
    public AddEventCommand(String fullCommand) throws EinsteinException {
        assert fullCommand != null : "Full command cannot be null";
        assert fullCommand.startsWith("event") : "Command should start with 'event'";
        String[] parts = fullCommand.substring(6).split("/from|/to", 3);
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new EinsteinException("Invalid event format! Use: event <description> /from <start> /to <end>");
        }
        this.description = parts[0].trim();
        this.from = parseDateTime(parts[1].trim());
        this.to = parseDateTime(parts[2].trim());
        assert this.description != null && !this.description.isEmpty() : "Description should not be null or empty";
        assert this.from != null : "Start time should not be null";
        assert this.to != null : "End time should not be null";
        assert !this.to.isBefore(this.from) : "End time should not be before start time";
    }

    /**
     * Parses the date and time string into a LocalDateTime object.
     *
     * @param dateTimeStr The date and time string to parse.
     * @return A LocalDateTime object representing the parsed date and time.
     * @throws EinsteinException If the date/time format is invalid.
     */
    private LocalDateTime parseDateTime(String dateTimeStr) throws EinsteinException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            LocalDateTime parsedDateTime = LocalDateTime.parse(dateTimeStr, formatter);
            assert parsedDateTime != null : "Parsed date/time should not be null";
            return parsedDateTime;
        } catch (DateTimeParseException e) {
            throw new EinsteinException("Invalid date/time format! Use: dd/MM/yyyy HHmm (e.g., 2/12/2019 1800)");
        }
    }

    /**
     * Executes the add event command, adding a new event task to the task list.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage for saving tasks.
     * @return A string confirming the addition of the new event task.
     * @throws EinsteinException If there's an error in adding the task or saving the updated list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";
        assert storage != null : "Storage cannot be null";
        Task task = new Event(description, from, to);
        assert task != null : "Created task should not be null";
        int originalTaskCount = tasks.getTaskCount();
        tasks.addTask(task);
        assert tasks.getTaskCount() == originalTaskCount + 1 : "Task count should increase by 1";
        storage.save(tasks.getTasks());
        String result = ui.showTaskAdded(task, tasks.getTaskCount());
        assert result != null && !result.isEmpty() : "Result string should not be null or empty";
        return result;
    }

    /**
     * Checks if this command should exit the application.
     *
     * @return false, as adding an event task does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
