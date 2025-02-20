package einstein.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import einstein.command.AddDeadlineCommand;
import einstein.command.AddTodoCommand;
import einstein.command.Command;
import einstein.exception.EinsteinException;

/**
 * Test class for the Parser class.
 * This class contains unit tests to verify the correct parsing of various command types.
 */
class ParserTest {

    /**
     * Tests that parsing a to-do command returns an instance of AddTodoCommand.
     *
     * @throws EinsteinException if there's an error parsing the command
     */
    @Test
    void parse_todoCommand_returnsAddTodoCommand() throws EinsteinException {
        Command command = Parser.parse("todo Read book");
        assertTrue(command instanceof AddTodoCommand);
    }

    /**
     * Tests that parsing a deadline command returns an instance of AddDeadlineCommand.
     *
     * @throws EinsteinException if there's an error parsing the command
     */
    @Test
    void parse_deadlineCommand_returnsAddDeadlineCommand() throws EinsteinException {
        Command command = Parser.parse("deadline Submit report /by 2/12/2023 1800");
        assertTrue(command instanceof AddDeadlineCommand);
    }

    /**
     * Tests that parsing an invalid command throws an EinsteinException.
     */
    @Test
    void parse_invalidCommand_throwsEinsteinException() {
        assertThrows(EinsteinException.class, () -> Parser.parse("invalid command"));
    }
}
