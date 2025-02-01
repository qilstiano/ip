package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.TaskList;
import einstein.ui.Ui;
import einstein.storage.Storage;

public class ListCommand implements Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        ui.showTaskList(tasks.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
