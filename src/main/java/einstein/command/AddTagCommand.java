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
        String[] parts = fullCommand.split(" ", 3);
        if (parts.length < 3) {
            throw new EinsteinException("Invalid tag command. Usage: tag <task_index> <tag>");
        }
        try {
            this.taskIndex = Integer.parseInt(parts[1]) - 1;
            this.tag = parts[2];
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task index. Please provide a valid number.");
        }
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        if (taskIndex < 0 || taskIndex >= tasks.getTaskCount()) {
            throw new EinsteinException("Invalid task index. Please provide a valid task number.");
        }
        tasks.getTasks().get(taskIndex).addTag(tag);
        storage.save(tasks.getTasks());
        return ui.showTagAdded(tasks.getTasks().get(taskIndex), tag);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
