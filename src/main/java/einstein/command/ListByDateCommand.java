package einstein.command;

// Imports for Date and Time

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import einstein.exception.EinsteinException;
import einstein.storage.TaskList;
import einstein.ui.Ui;
import einstein.storage.Storage;

public class ListByDateCommand implements Command {
    private LocalDate date;

    public ListByDateCommand(String fullCommand) throws EinsteinException {
        try {
            this.date = LocalDate.parse(fullCommand.substring(5).trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new EinsteinException("Invalid date format! Use: list yyyy-MM-dd");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        ui.showTasksByDate(tasks.getTasks(), date);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
