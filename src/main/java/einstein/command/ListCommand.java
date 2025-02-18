package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.ui.Ui;

/**
 * Represents a command to list all tasks in the Einstein task management system.
 */
public class ListCommand implements Command {

    /**
     * Executes the list command, displaying all tasks in the task list.
     *
     * @param tasks The current list of tasks.
     * @param ui The user interface for displaying messages.
     * @param storage The storage for tasks (not used in this command).
     * @return A string representation of the current task list.
     * @throws EinsteinException If there's an error in retrieving or displaying the task list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "UI cannot be null";
        assert storage != null : "Storage cannot be null";

        String result = ui.showTaskList(tasks.getTasks());
        assert result != null : "Result string should not be null";
        assert !result.isEmpty() : "Result string should not be empty";

        return result;
    }

    /**
     * Checks if this command should exit the application.
     *
     * @return false, as listing tasks does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
