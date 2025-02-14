package einstein.command;

import einstein.exception.EinsteinException;
import einstein.storage.TaskList;
import einstein.ui.Ui;
import einstein.storage.Storage;

public class ExitCommand implements Command {
  @Override
  public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
      return ui.showFarewell();
  }

  @Override
  public boolean isExit() {
      return true;
  }
}
