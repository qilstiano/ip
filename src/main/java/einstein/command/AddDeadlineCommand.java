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
        assert fullCommand != null : "Full command cannot be null";
        assert fullCommand.startsWith("deadline") : "Command should start with 'deadline'";
        String[] parts = fullCommand.substring(9).split("/by", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new EinsteinException("Invalid deadline format! Use: deadline <description> /by <date>");
        }
        this.description = parts[0].trim();
        this.by = parseDateTime(parts[1].trim());
        assert this.description != null && !this.description.isEmpty() : "Description should not be null or empty";
    }

    /**
     * Parses the date and time string into a LocalDateTime object.
     *
     * @param dateTimeStr The date and time string to parse.
     * @return A LocalDateTime object representing the parsed date and time.
     * @throws EinsteinException If the date/time format is invalid.
     */
    private LocalDateTime parseDateTime(String dateTimeStr) throws EinsteinException {
        assert dateTimeStr != null && !dateTimeStr.isEmpty() : "Date/time string should not be null or empty";
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
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";
        assert storage != null : "Storage cannot be null";
        Task task = new Deadline(description, by);
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
     * @return false, as adding a deadline task does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
