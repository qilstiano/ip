package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.ui.Ui;

/**
 * Represents a command to display help information in the Einstein task management system.
 */
public class HelpCommand implements Command {
    /**
     * Executes the help command, displaying a list of all available commands and their usage.
     *
     * @param tasks The current list of tasks (not used in this command).
     * @param ui The user interface for displaying messages.
     * @param storage The storage for tasks (not used in this command).
     * @return A string containing the help information for all available commands.
     * @throws EinsteinException If there's an error in generating or displaying the help information.
     */
    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        StringBuilder output = new StringBuilder();
        output.append(ui.showLine()).append("\n");
        output.append("Here are the commands I understand:\n");
        output.append("1. todo <description> - Add a todo task.\n");
        output.append("   Example: todo read book\n");
        output.append("2. deadline <description> /by <date> - Add a deadline task.\n");
        output.append("   Example: deadline return book /by 2/12/2019 1800\n");
        output.append("3. event <description> /from <start> /to <end> - Add an event task.\n");
        output.append("   Example: event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600\n");
        output.append("4. list - List all tasks.\n");
        output.append("   Example: list\n");
        output.append("5. list <date> - List tasks occurring on a specific date (format: yyyy-MM-dd).\n");
        output.append("   Example: list 2019-12-02\n");
        output.append("6. find <task name> - List tasks matching the searched name\n");
        output.append("   Example: list read books\n");
        output.append("7. mark <task number> - Mark a task as done.\n");
        output.append("   Example: mark 1\n");
        output.append("8. unmark <task number> - Mark a task as not done.\n");
        output.append("   Example: unmark 1\n");
        output.append("9. delete <task number> - Delete a task.\n");
        output.append("   Example: delete 1\n");
        output.append("10. help - Display this help message.\n");
        output.append("   Example: help\n");
        output.append("11. bye - Exit the program.\n");
        output.append("   Example: bye\n");
        output.append(ui.showLine());

        return output.toString();
    }

    /**
     * Checks if this command should exit the application.
     *
     * @return false, as displaying help information does not exit the application.
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
