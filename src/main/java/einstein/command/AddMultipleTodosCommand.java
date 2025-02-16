package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.task.Task;
import einstein.task.Todo;
import einstein.ui.Ui;

/**
 * Represents a command to add multiple to-do tasks in the Einstein task management system.
 */
public class AddMultipleTodosCommand implements Command {
    private String[] descriptions;

    /**
     * Constructs a new AddMultipleTodosCommand.
     *
     * @param descriptions An array of task descriptions.
     */
    public AddMultipleTodosCommand(String... descriptions) {
        this.descriptions = descriptions;
    }

    /**
     * Executes the add multiple todos command, adding new to-do tasks to the task list.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage for saving tasks.
     * @return A string confirming the addition of the new to-do tasks.
     * @throws EinsteinException If there's an error in adding the tasks or saving the updated list.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        StringBuilder result = new StringBuilder();
        for (String description : descriptions) {
            Task task = new Todo(description.trim());
            tasks.addTask(task);
            result.append(ui.showTaskAdded(task, tasks.getTaskCount())).append("\n");
        }
        storage.save(tasks.getTasks());
        return result.append("\n").toString();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
