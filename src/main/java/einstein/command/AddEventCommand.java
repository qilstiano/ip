package einstein.command;

// Imports for Date and Time
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import einstein.exception.EinsteinException;
import einstein.task.Task;
import einstein.task.Event;
import einstein.storage.TaskList;
import einstein.storage.Storage;
import einstein.ui.Ui;

public class AddEventCommand implements Command {
    private String description;
    private LocalDateTime from;
    private LocalDateTime to;

    public AddEventCommand(String fullCommand) throws EinsteinException {
        String[] parts = parseCommand(fullCommand);
        validateParts(parts);
        initializeFields(parts);
    }

    private String[] parseCommand(String fullCommand) {
        return fullCommand.substring(6).split("/from|/to", 3);
    }

    private void validateParts(String[] parts) throws EinsteinException {
        if (isInvalidFormat(parts)) {
            throw new EinsteinException("Invalid event format! Use: event <description> /from <start> /to <end>");
        }
    }

    private boolean isInvalidFormat(String[] parts) {
        return parts.length < 3 ||
                parts[0].trim().isEmpty() ||
                parts[1].trim().isEmpty() ||
                parts[2].trim().isEmpty();
    }

    private void initializeFields(String[] parts) throws EinsteinException {
        this.description = parts[0].trim();
        this.from = parseDateTime(parts[1].trim());
        this.to = parseDateTime(parts[2].trim());
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
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        Task task = new Event(description, from, to);
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.getTaskCount());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
