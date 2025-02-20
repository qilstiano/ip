package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.ui.Ui;

/**
 * Represents a command to mark a task as not done in the Einstein task management system.
 */
public class UnmarkCommand implements Command {
    private int taskIndex;

    /**
     * Constructs a new UnmarkCommand.
     *
     * @param fullCommand The full command string from the user input.
     * @throws EinsteinException If the task number in the command is invalid.
     */
    public UnmarkCommand(String fullCommand) throws EinsteinException {
        assert fullCommand != null : "Full command cannot be null";
        assert fullCommand.startsWith("unmark") : "Command should start with 'unmark'";
        try {
            this.taskIndex = Integer.parseInt(fullCommand.substring(7).trim()) - 1;
            assert this.taskIndex >= 0 : "Task index must be non-negative";
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    /**
     * Executes the unmark command, marking the specified task as not done.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage for saving tasks.
     * @return A string representation of the updated task list.
     * @throws EinsteinException If there's an error in unmarking the task or saving the updated list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "UI cannot be null";
        assert storage != null : "Storage cannot be null";
        assert taskIndex < tasks.getTaskCount() : "Task index out of bounds";
        tasks.markTaskAsNotDone(taskIndex);
        storage.save(tasks.getTasks());
        String result = ui.showTaskList(tasks.getTasks());
        assert result != null : "Result string should not be null";
        assert !result.isEmpty() : "Result string should not be empty";
        return result;
    }

    /**
     * Checks if this command should exit the application.
     *
     * @return false, as unmarking a task does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
