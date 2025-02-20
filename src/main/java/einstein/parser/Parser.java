package einstein.parser;

import einstein.command.AddDeadlineCommand;
import einstein.command.AddEventCommand;
import einstein.command.AddMultipleTodosCommand;
import einstein.command.AddTagCommand;
import einstein.command.Command;
import einstein.command.DeleteCommand;
import einstein.command.ExitCommand;
import einstein.command.FindCommand;
import einstein.command.HelpCommand;
import einstein.command.ListByDateCommand;
import einstein.command.ListCommand;
import einstein.command.MarkCommand;
import einstein.command.RemoveTagCommand;
import einstein.command.UnmarkCommand;
import einstein.exception.EinsteinException;

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
        assert fullCommand != null : "Full command cannot be null";
        assert !fullCommand.trim().isEmpty() : "Full command cannot be empty";
        Command command;

        if (fullCommand.equalsIgnoreCase("bye")) {
            command = new ExitCommand();
        } else if (fullCommand.equalsIgnoreCase("list")) {
            command = new ListCommand();
        } else if (fullCommand.startsWith("list ")) {
            command = new ListByDateCommand(fullCommand);
        } else if (fullCommand.startsWith("mark ")) {
            command = new MarkCommand(fullCommand);
        } else if (fullCommand.startsWith("unmark ")) {
            command = new UnmarkCommand(fullCommand);
        } else if (fullCommand.startsWith("todo ")) {
            command = parseTodoCommand(fullCommand);
        } else if (fullCommand.startsWith("deadline ")) {
            command = new AddDeadlineCommand(fullCommand);
        } else if (fullCommand.startsWith("event ")) {
            command = new AddEventCommand(fullCommand);
        } else if (fullCommand.startsWith("delete ")) {
            command = new DeleteCommand(fullCommand);
        } else if (fullCommand.equalsIgnoreCase("help")) {
            command = new HelpCommand();
        } else if (fullCommand.startsWith("find ")) {
            command = new FindCommand(fullCommand);
        } else if (fullCommand.startsWith("tag ")) {
            command = new AddTagCommand(fullCommand);
        } else if (fullCommand.startsWith("untag ")) {
            command = new RemoveTagCommand(fullCommand);
        } else {
            throw new EinsteinException("ARGH! I do not understand you, which is weird, "
                    + "\nbecause I usually understand most things. Invalid command!");
        }

        assert command != null : "Parsed command should not be null";
        return command;
    }

    /**
     * Parses the to-do command for multiple todos.
     *
     * @param fullCommand The full command string entered by the user.
     * @return A {@code Command} object corresponding to the user input.
     * @throws EinsteinException If the input command is invalid or not recognized.
     */
    private static Command parseTodoCommand(String fullCommand) throws EinsteinException {
        assert fullCommand != null : "Full command cannot be null";
        assert fullCommand.startsWith("todo ") : "Command should start with 'todo '";

        String args = fullCommand.substring(5).trim();
        assert !args.isEmpty() : "Todo command should have arguments";

        // Handle multiple todos
        String[] todos = args.split(","); // Split by commas
        if (todos.length == 0) {
            throw new EinsteinException("Nein! You must provide at least one task description.");
        }

        return new AddMultipleTodosCommand(todos);
    }
}
