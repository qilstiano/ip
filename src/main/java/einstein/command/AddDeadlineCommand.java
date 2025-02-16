package einstein.command;

// Imports for Date and Time

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.task.Deadline;
import einstein.task.Task;
import einstein.ui.Ui;

/**
 * Represents a command to add a deadline task in the Einstein task management system.
 */
public class AddDeadlineCommand implements Command {
    private String description;
    private LocalDateTime by;

    /**
     * Constructs a new AddDeadlineCommand.
     *
     * @param fullCommand The full command string from the user input.
     * @throws EinsteinException If the command format is invalid or the date/time is improperly formatted.
     */
    public AddDeadlineCommand(String fullCommand) throws EinsteinException {
        String[] parts = fullCommand.substring(9).split("/by", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new EinsteinException("Invalid deadline format! Use: deadline <description> /by <date>");
        }
        this.description = parts[0].trim();
        this.by = parseDateTime(parts[1].trim());
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
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            throw new EinsteinException("Invalid date/time format! Use: dd/MM/yyyy HHmm (e.g., 2/12/2019 1800)");
        }
    }

    /**
     * Executes the add deadline command, adding a new deadline task to the task list.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage for saving tasks.
     * @return A string confirming the addition of the new deadline task.
     * @throws EinsteinException If there's an error in adding the task or saving the updated list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        Task task = new Deadline(description, by);
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        return ui.showTaskAdded(task, tasks.getTaskCount());
    }

    /**
     * Checks if this command should exit the application.
     *
     * @return false, as adding a deadline task does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
