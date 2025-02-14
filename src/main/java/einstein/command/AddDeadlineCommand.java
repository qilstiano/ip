package einstein.command;

// Imports for Date and Time
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import einstein.exception.EinsteinException;
import einstein.task.Task;
import einstein.task.Deadline;
import einstein.storage.TaskList;
import einstein.storage.Storage;
import einstein.ui.Ui;

public class AddDeadlineCommand implements Command {
    private String description;
    private LocalDateTime by;

    public AddDeadlineCommand(String fullCommand) throws EinsteinException {
        String[] parts = fullCommand.substring(9).split("/by", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new EinsteinException("Invalid deadline format! Use: deadline <description> /by <date>");
        }
        this.description = parts[0].trim();
        this.by = parseDateTime(parts[1].trim());
    }

    private LocalDateTime parseDateTime(String dateTimeStr) throws EinsteinException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            throw new EinsteinException("Invalid date/time format! Use: dd/MM/yyyy HHmm (e.g., 2/12/2019 1800)");
        }
    }

    @Override
    public String execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        Task task = new Deadline(description, by);
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        return ui.showTaskAdded(task, tasks.getTaskCount());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
