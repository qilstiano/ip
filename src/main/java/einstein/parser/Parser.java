package einstein.parser;

import einstein.command.FindCommand;
import einstein.exception.EinsteinException;
import einstein.command.Command;
import einstein.command.ExitCommand;
import einstein.command.ListCommand;
import einstein.command.ListByDateCommand;
import einstein.command.MarkCommand;
import einstein.command.UnmarkCommand;
import einstein.command.AddTodoCommand;
import einstein.command.AddDeadlineCommand;
import einstein.command.AddEventCommand;
import einstein.command.DeleteCommand;
import einstein.command.HelpCommand;
import einstein.command.FindCommand;

/**
 * Parses user input and returns the corresponding command object.
 * This class is responsible for interpreting commands entered by the user
 * and mapping them to their respective execution logic.
 */
public class Parser {

    /**
     * Parses the user input command and returns the appropriate {@code Command} object.
     * The method matches the input with predefined commands and creates an instance of
     * the respective command class.
     *
     * @param fullCommand The full command string entered by the user.
     * @return A {@code Command} object corresponding to the user input.
     * @throws EinsteinException If the input command is invalid or not recognized.
     */
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
        } else if (fullCommand.startsWith("find ")) {
            return new FindCommand(fullCommand);
        } else {
            throw new EinsteinException("ARGH! I do not understand you, which is weird, \nbecause I usually understand most things. Invalid command!");
        }
    }
}