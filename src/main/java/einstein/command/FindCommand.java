package einstein.command;

import java.util.ArrayList;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.task.Task;
import einstein.ui.Ui;

/**
 * Represents a command to search for tasks containing a specific keyword.
 */
public class FindCommand implements Command {
    private String keyword;

    /**
     * Constructs a FindCommand with the given user input.
     *
     * @param fullCommand The full user command string.
     * @throws EinsteinException If the keyword is missing.
     */
    public FindCommand(String fullCommand) throws EinsteinException {
        assert fullCommand != null : "Full command cannot be null";
        assert fullCommand.startsWith("find") : "Command should start with 'find'";
        String trimmedCommand = fullCommand.substring(5).trim();
        if (trimmedCommand.isEmpty()) {
            throw new EinsteinException("Please provide a keyword to search!");
        }
        this.keyword = trimmedCommand;
        assert !this.keyword.isEmpty() : "Keyword should not be null or empty";
    }

    /**
     * Executes the search operation by finding tasks that contain the keyword.
     *
     * @param tasks   The TaskList to search in.
     * @param ui      The UI instance to display results.
     * @param storage The storage handler (not used in search operation).
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        assert tasks != null : "TaskList cannot be null";
        assert ui != null : "UI cannot be null";
        assert storage != null : "Storage cannot be null";
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks.getTasks()) {
            assert task != null : "Task in TaskList cannot be null";
            if (task.getDescription().contains(keyword)) {
                matchingTasks.add(task);
            }
        }
        String result;
        if (matchingTasks.isEmpty()) {
            result = ui.showError("No matching tasks found!");
        } else {
            result = ui.showTaskList(matchingTasks);
        }

        assert result != null && !result.isEmpty() : "Result string should not be null or empty";
        return result;
    }

    /**
     * Indicates that this command does not exit the application.
     *
     * @return false, since searching does not terminate the program.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
