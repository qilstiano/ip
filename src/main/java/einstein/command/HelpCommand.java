package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.TaskList;
import einstein.storage.Storage;
import einstein.ui.Ui;

public class HelpCommand implements Command {
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
        output.append("6. mark <task number> - Mark a task as done.\n");
        output.append("   Example: mark 1\n");
        output.append("7. unmark <task number> - Mark a task as not done.\n");
        output.append("   Example: unmark 1\n");
        output.append("8. delete <task number> - Delete a task.\n");
        output.append("   Example: delete 1\n");
        output.append("9. help - Display this help message.\n");
        output.append("   Example: help\n");
        output.append("10. bye - Exit the program.\n");
        output.append("   Example: bye\n");
        output.append(ui.showLine());

        return output.toString();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}