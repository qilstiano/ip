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
        this.description = fullCommand.substring(5).trim();
        if (description.isEmpty()) {
            throw new EinsteinException("Nein! You have to give a description to your todo.");
        }
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
        Task task = new Todo(description);
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        return ui.showTaskAdded(task, tasks.getTaskCount());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
