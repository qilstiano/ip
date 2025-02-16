package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.ui.Ui;

/**
 * Represents a command to exit the Einstein task management system.
 */
public class ExitCommand implements Command {

    /**
     * Executes the exit command, displaying a farewell message.
     *
     * @param tasks The current list of tasks (not used in this command).
     * @param ui The user interface for displaying messages.
     * @param storage The storage for tasks (not used in this command).
     * @return A string containing the farewell message.
     * @throws EinsteinException If there's an error in generating or displaying the farewell message.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        return ui.showFarewell();
    }

    /**
     * Checks if this command should exit the application.
     *
     * @return true, as this command is specifically for exiting the application.
     */
    @Override
    public boolean isExit() {
        return true;
    }
}
