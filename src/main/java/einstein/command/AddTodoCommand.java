package einstein.command;

import einstein.exception.EinsteinException;
import einstein.task.Todo;
import einstein.task.Task;
import einstein.storage.TaskList;
import einstein.storage.Storage;
import einstein.ui.Ui;

public class AddTodoCommand implements Command {
    private String description;

    public AddTodoCommand(String fullCommand) throws EinsteinException {
        this.description = fullCommand.substring(5).trim();
        if (description.isEmpty()) {
            throw new EinsteinException("Nein! You have to give a description to your todo.");
        }
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        Task task = new Todo(description);
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        return ui.showTaskAdded(task, tasks.getTaskCount());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
