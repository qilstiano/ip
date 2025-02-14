package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.TaskList;
import einstein.ui.Ui;
import einstein.storage.Storage;

public class UnmarkCommand implements Command {
    private int taskIndex;

    public UnmarkCommand(String fullCommand) throws EinsteinException {
        try {
            this.taskIndex = Integer.parseInt(fullCommand.substring(7).trim()) - 1;
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        tasks.markTaskAsNotDone(taskIndex);
        storage.save(tasks.getTasks());
        return ui.showTaskList(tasks.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}