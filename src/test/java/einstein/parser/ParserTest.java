package einstein.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import einstein.exception.EinsteinException;
import einstein.command.Command;
import einstein.command.AddTodoCommand;
import einstein.command.AddDeadlineCommand;
class ParserTest {

    @Test
    void parse_todoCommand_returnsAddTodoCommand() throws EinsteinException {
        Command command = Parser.parse("todo Read book");
        assertTrue(command instanceof AddTodoCommand);
    }

    @Test
    void parse_deadlineCommand_returnsAddDeadlineCommand() throws EinsteinException {
        Command command = Parser.parse("deadline Submit report /by 2/12/2023 1800");
        assertTrue(command instanceof AddDeadlineCommand);
    }

    @Test
    void parse_invalidCommand_throwsEinsteinException() {
        assertThrows(EinsteinException.class, () -> Parser.parse("invalid command"));
    }
}