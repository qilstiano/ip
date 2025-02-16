package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.ui.Ui;

/**
 * Represents a command to delete a task in the Einstein task management system.
 */
public class DeleteCommand implements Command {
    private int taskIndex;

    /**
     * Constructs a new DeleteCommand.
     *
     * @param fullCommand The full command string from the user input.
     * @throws EinsteinException If the task number in the command is invalid.
     */
    public DeleteCommand(String fullCommand) throws EinsteinException {
        try {
            this.taskIndex = Integer.parseInt(fullCommand.substring(7).trim()) - 1;
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    /**
     * Executes the delete command, removing the specified task from the task list.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage for saving tasks.
     * @return A string representation of the updated task list.
     * @throws EinsteinException If there's an error in deleting the task or saving the updated list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        tasks.deleteTask(taskIndex);
        storage.save(tasks.getTasks());
        return ui.showTaskList(tasks.getTasks());
    }

    /**
     * Checks if this command should exit the application.
     *
     * @return false, as deleting a task does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
