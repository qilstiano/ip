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
        assert descriptions != null : "Descriptions array cannot be null";
        assert descriptions.length > 0 : "At least one description must be provided";
        this.descriptions = descriptions;
        for (String description : descriptions) {
            assert description != null : "Individual description cannot be null";
            assert !description.trim().isEmpty() : "Description cannot be empty after trimming";
        }
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
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";
        assert storage != null : "Storage cannot be null";
        StringBuilder result = new StringBuilder();
        int initialTaskCount = tasks.getTaskCount();
        for (String description : descriptions) {
            Task task = new Todo(description.trim());
            assert task != null : "Created task should not be null";
            int beforeAddCount = tasks.getTaskCount();
            tasks.addTask(task);
            assert tasks.getTaskCount() == beforeAddCount + 1 : "Task count should increase by 1 for each added task";
            String addedMessage = ui.showTaskAdded(task, tasks.getTaskCount());
            assert addedMessage != null && !addedMessage.isEmpty() : "Task added message should not be null or empty";
            result.append(addedMessage).append("\n");
        }
        storage.save(tasks.getTasks());

        assert tasks.getTaskCount() == initialTaskCount + descriptions.length
                : "Final task count should match initial count plus number of added tasks";

        String finalResult = result.append("\n").toString();
        assert !finalResult.isEmpty() : "Final result string should not be empty";

        return finalResult;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
