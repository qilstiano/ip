package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.task.Task;
import einstein.task.Todo;
import einstein.ui.Ui;

/**
 * Represents a command to add a to-do task in the Einstein task management system.
 */
public class AddTodoCommand implements Command {
    private String description;

    /**
     * Constructs a new AddTodoCommand.
     *
     * @param fullCommand The full command string from the user input.
     * @throws EinsteinException If the to-do description is empty.
     */
    public AddTodoCommand(String fullCommand) throws EinsteinException {
        assert fullCommand != null : "Full command cannot be null";
        this.description = fullCommand.substring(5).trim();
        if (description.isEmpty()) {
            assert fullCommand.startsWith("todo") : "Command should start with 'todo'";
            throw new EinsteinException("Nein! You have to give a description to your todo.");
        }
        assert !description.isEmpty() : "Description should not be empty after trimming";
    }

    /**
     * Executes the add to-do command, adding a new to-do task to the task list.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage for saving tasks.
     * @return A string confirming the addition of the new to-do task.
     * @throws EinsteinException If there's an error in adding the task
     *                           or saving the updated list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";
        assert storage != null : "Storage cannot be null";
        Task task = new Todo(description);
        assert task != null : "Created task should not be null";
        int originalTaskCount = tasks.getTaskCount();
        tasks.addTask(task);
        assert tasks.getTaskCount() == originalTaskCount + 1 : "Task count should increase by 1";
        storage.save(tasks.getTasks());
        String result = ui.showTaskAdded(task, tasks.getTaskCount());
        assert result != null && !result.isEmpty() : "Result string should not be null or empty";
        return result;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
