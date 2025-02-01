package parser;

import einstein.exception.EinsteinException;
import einstein.command.Command;

public class Parser {
    public static Command parse(String fullCommand) throws EinsteinException {
        if (fullCommand.equalsIgnoreCase("bye")) {
            return new ExitCommand();
        } else if (fullCommand.equalsIgnoreCase("list")) {
            return new ListCommand();
        } else if (fullCommand.startsWith("list ")) {
            return new ListByDateCommand(fullCommand);
        } else if (fullCommand.startsWith("mark ")) {
            return new MarkCommand(fullCommand);
        } else if (fullCommand.startsWith("unmark ")) {
            return new UnmarkCommand(fullCommand);
        } else if (fullCommand.startsWith("todo ")) {
            return new AddTodoCommand(fullCommand);
        } else if (fullCommand.startsWith("deadline ")) {
            return new AddDeadlineCommand(fullCommand);
        } else if (fullCommand.startsWith("event ")) {
            return new AddEventCommand(fullCommand);
        } else if (fullCommand.startsWith("delete ")) {
            return new DeleteCommand(fullCommand);
        } else if (fullCommand.equalsIgnoreCase("help")) {
            return new HelpCommand();
        } else {
            throw new EinsteinException("ARGH! I do not understand you, which is weird, \nbecause I usually understand most things. Invalid command!");
        }
    }
}
