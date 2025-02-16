package einstein.command;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.ui.Ui;

/**
 * Represents a command to list tasks for a specific date in the Einstein task management system.
 */
public class ListByDateCommand implements Command {
    private LocalDate date;

    /**
     * Constructs a new ListByDateCommand.
     *
     * @param fullCommand The full command string from the user input.
     * @throws EinsteinException If the date format in the command is invalid.
     */
    public ListByDateCommand(String fullCommand) throws EinsteinException {
        try {
            this.date = LocalDate.parse(fullCommand.substring(5).trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new EinsteinException("Invalid date format! Use: list yyyy-MM-dd");
        }
    }

    /**
     * Executes the list by date command, displaying tasks for the specified date.
     *
     * @param tasks The current list of tasks.
     * @param ui The user interface for displaying messages.
     * @param storage The storage for tasks (not used in this command).
     * @return A string representation of the tasks occurring on the specified date.
     * @throws EinsteinException If there's an error in retrieving or displaying the tasks.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        return ui.showTasksByDate(tasks.getTasks(), date);
    }

    /**
     * Checks if this command should exit the application.
     *
     * @return false, as listing tasks by date does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
