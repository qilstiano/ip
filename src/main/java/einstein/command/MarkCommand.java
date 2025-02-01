package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.TaskList;
import einstein.ui.Ui;
import einstein.storage.Storage;

public class MarkCommand implements Command {
    private int taskIndex;

    public MarkCommand(String fullCommand) throws EinsteinException {
        try {
            this.taskIndex = Integer.parseInt(fullCommand.substring(5).trim()) - 1;
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        tasks.markTaskAsDone(taskIndex);
        storage.save(tasks.getTasks());
        ui.showLine();
        ui.showTaskList(tasks.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
