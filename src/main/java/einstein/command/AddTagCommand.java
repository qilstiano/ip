package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.ui.Ui;

/**
 * Represents a command to add a tag to a task.
 */
public class AddTagCommand implements Command {
    private int taskIndex;
    private String tag;

    /**
     * Constructs an AddTagCommand with the specified task index and tag.
     *
     * @param fullCommand The full command string containing the task index and tag.
     * @throws EinsteinException If the command is invalid.
     */
    public AddTagCommand(String fullCommand) throws EinsteinException {
        assert fullCommand != null : "Full command cannot be null";
        assert !fullCommand.trim().isEmpty() : "Full command cannot be empty";

        String[] parts = fullCommand.split(" ", 3);
        assert parts.length >= 1 : "Command parts cannot be empty";

        if (parts.length < 3) {
            throw new EinsteinException("Invalid tag command. Usage: tag <task_index> <tag>");
        }

        try {
            this.taskIndex = Integer.parseInt(parts[1]) - 1;
            assert this.taskIndex >= 0 : "Task index must be non-negative after subtracting 1";

            this.tag = parts[2];
            assert this.tag != null && !this.tag.trim().isEmpty() : "Tag cannot be null or empty";
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task index. Please provide a valid number.");
        }
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "Ui cannot be null";
        assert storage != null : "Storage cannot be null";

        if (taskIndex < 0 || taskIndex >= tasks.getTaskCount()) {
            throw new EinsteinException("Invalid task index. Please provide a valid task number.");
        }

        assert tasks.getTasks() != null : "Task list cannot be null";
        assert tasks.getTasks().get(taskIndex) != null : "Task at given index cannot be null";

        tasks.getTasks().get(taskIndex).addTag(tag);
        storage.save(tasks.getTasks());

        String result = ui.showTagAdded(tasks.getTasks().get(taskIndex), tag);
        assert result != null : "Result string cannot be null";
        return result;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
