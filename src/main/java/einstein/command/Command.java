package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.ui.Ui;

public interface Command {
    String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException;

    boolean isExit();
}
