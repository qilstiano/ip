package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.ui.Ui;

/**
 * Represents a command to mark a task as done in the Einstein task management system.
 */
public class MarkCommand implements Command {
    private int taskIndex;

    /**
     * Constructs a new MarkCommand.
     *
     * @param fullCommand The full command string from the user input.
     * @throws EinsteinException If the task number in the command is invalid.
     */
    public MarkCommand(String fullCommand) throws EinsteinException {
        assert fullCommand != null : "Full command cannot be null";
        assert fullCommand.startsWith("mark") : "Command should start with 'mark'";
        try {
            this.taskIndex = Integer.parseInt(fullCommand.substring(5).trim()) - 1;
            assert this.taskIndex >= 0 : "Task index must be non-negative";
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    /**
     * Executes the mark command, marking the specified task as done.
     *
     * @param tasks The current list of tasks.
     * @param ui The user interface for displaying messages.
     * @param storage The storage for saving tasks.
     * @return A string representation of the updated task list.
     * @throws EinsteinException If there's an error in marking the task or saving the updated list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "UI cannot be null";
        assert storage != null : "Storage cannot be null";
        assert taskIndex < tasks.getTaskCount() : "Task index out of bounds";
        tasks.markTaskAsDone(taskIndex);
        storage.save(tasks.getTasks());
        String result = ui.showTaskList(tasks.getTasks());
        assert result != null : "Result string should not be null";
        assert !result.isEmpty() : "Result string should not be empty";
        return result;
    }

    /**
     * Checks if this command should exit the application.
     *
     * @return false, as marking a task does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
