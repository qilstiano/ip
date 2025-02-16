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
        assert fullCommand != null : "Full command cannot be null";
        assert fullCommand.startsWith("list") : "Command should start with 'list'";

        String dateString = fullCommand.substring(5).trim();
        assert !dateString.isEmpty() : "Date string should not be empty";

        try {
            this.date = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new EinsteinException("Invalid date format! Use: list yyyy-MM-dd");
        }

        assert this.date != null : "Parsed date should not be null";
    }

    /**
     * Executes the list by date command, displaying tasks for the specified date.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage for tasks (not used in this command).
     * @return A string representation of the tasks occurring on the specified date.
     * @throws EinsteinException If there's an error in retrieving or displaying the tasks.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "UI cannot be null";
        assert storage != null : "Storage cannot be null";

        String result = ui.showTasksByDate(tasks.getTasks(), date);
        assert result != null : "Result string should not be null";
        assert !result.isEmpty() : "Result string should not be empty";

        return result;
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
