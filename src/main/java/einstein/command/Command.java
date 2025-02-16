package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.ui.Ui;

/**
 * Represents a command in the Einstein task management system.
 * All concrete Command classes should implement this interface.
 */
public interface Command {

    /**
     * Executes the command.
     *
     * @param tasks   The current list of tasks.
     * @param ui      The user interface for displaying messages.
     * @param storage The storage for saving tasks.
     * @return A string containing the result of the command execution.
     * @throws EinsteinException If there's an error during command execution.
     */
    String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException;

    /**
     * Checks if this command should exit the application.
     *
     * @return true if the command should exit the application, false otherwise.
     */
    boolean isExit();
}
