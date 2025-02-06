package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.TaskList;
import einstein.storage.Storage;
import einstein.ui.Ui;

public class HelpCommand implements Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        ui.showLine();
        System.out.println("Here are the commands I understand:");
        System.out.println("1. todo <description> - Add a todo task.");
        System.out.println("   Example: todo read book");
        System.out.println("2. deadline <description> /by <date> - Add a deadline task.");
        System.out.println("   Example: deadline return book /by 2/12/2019 1800");
        System.out.println("3. event <description> /from <start> /to <end> - Add an event task.");
        System.out.println("   Example: event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600");
        System.out.println("4. list - List all tasks.");
        System.out.println("   Example: list");
        System.out.println("5. list <date> - List tasks occurring on a specific date (format: yyyy-MM-dd).");
        System.out.println("   Example: list 2019-12-02");
        System.out.println("6. mark <task number> - Mark a task as done.");
        System.out.println("   Example: mark 1");
        System.out.println("7. unmark <task number> - Mark a task as not done.");
        System.out.println("   Example: unmark 1");
        System.out.println("8. delete <task number> - Delete a task.");
        System.out.println("   Example: delete 1");
        System.out.println("9. help - Display this help message.");
        System.out.println("   Example: help");
        System.out.println("10. bye - Exit the program.");
        System.out.println("   Example: bye");

        ui.showLine();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
