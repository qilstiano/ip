package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.TaskList;
import einstein.storage.Storage;
import einstein.ui.Ui;
import einstein.task.Task;

import java.util.ArrayList;

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
        String trimmedCommand = fullCommand.substring(5).trim();
        if (trimmedCommand.isEmpty()) {
            throw new EinsteinException("Please provide a keyword to search!");
        }
        this.keyword = trimmedCommand;
    }

    /**
     * Executes the search operation by finding tasks that contain the keyword.
     *
     * @param tasks The TaskList to search in.
     * @param ui The UI instance to display results.
     * @param storage The storage handler (not used in search operation).
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        for (Task task : tasks.getTasks()) {
            if (task.description.contains(keyword)) {
                matchingTasks.add(task);
            }
        }

        if (matchingTasks.isEmpty()) {
            return ui.showError("No matching tasks found!");
        } else {
            return ui.showTaskList(matchingTasks);
        }
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
