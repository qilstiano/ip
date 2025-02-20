package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.task.Priority;
import einstein.ui.Ui;

/**
 * Represents a command to set the priority of a task.
 */
public class SetPriorityCommand implements Command {
    private int taskIndex;
    private Priority priority;

    /**
     * Constructs a SetPriorityCommand with the specified task index and priority.
     *
     * @param fullCommand The full command string containing the task index and priority.
     * @throws EinsteinException If the command is invalid.
     */
    public SetPriorityCommand(String fullCommand) throws EinsteinException {
        assert fullCommand != null : "Full command cannot be null";
        String[] parts = fullCommand.split(" ", 3);
        if (parts.length < 3) {
            throw new EinsteinException("Invalid priority command. Usage: priority <task_index> "
                    + "<uber_high|high|medium|low>");
        }
        try {
            this.taskIndex = Integer.parseInt(parts[1]) - 1;
            this.priority = Priority.valueOf(parts[2].toUpperCase());
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task index. Please provide a valid number.");
        } catch (IllegalArgumentException e) {
            throw new EinsteinException("Invalid priority. Please use 'uber_high', 'high', 'medium', or 'low'.");
        }
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        if (taskIndex < 0 || taskIndex >= tasks.getTaskCount()) {
            throw new EinsteinException("Invalid task index. Please provide a valid task number.");
        }
        tasks.getTasks().get(taskIndex).setPriority(priority);
        storage.save(tasks.getTasks());
        return ui.showPrioritySet(tasks.getTasks().get(taskIndex), priority);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
