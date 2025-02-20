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
        assert !fullCommand.trim().isEmpty() : "Full command cannot be empty";

        String[] parts = fullCommand.split(" ", 3);
        assert parts.length >= 1 : "Command parts cannot be empty";

        if (parts.length < 3) {
            throw new EinsteinException("Invalid priority command. Usage: priority <task_index> "
                    + "<uber_high|high|medium|low>");
        }

        try {
            this.taskIndex = Integer.parseInt(parts[1]) - 1;
            assert this.taskIndex >= 0 : "Task index must be non-negative after subtracting 1";

            this.priority = Priority.valueOf(parts[2].toUpperCase());
            assert this.priority != null : "Priority cannot be null after parsing";
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task index. Please provide a valid number.");
        } catch (IllegalArgumentException e) {
            throw new EinsteinException("Invalid priority. Please use 'uber_high', 'high', 'medium', or 'low'.");
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

        tasks.getTasks().get(taskIndex).setPriority(priority);
        storage.save(tasks.getTasks());

        String result = ui.showPrioritySet(tasks.getTasks().get(taskIndex), priority);
        assert result != null : "Result string cannot be null";
        return result;
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
