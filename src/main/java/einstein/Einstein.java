package einstein;

import einstein.command.Command;
import einstein.exception.EinsteinException;
import einstein.parser.Parser;
import einstein.storage.Storage;
import einstein.storage.TaskList;
import einstein.ui.Ui;

/**
 * The main class for the Einstein task management application.
 */
public class Einstein {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Constructs a new Einstein instance.
     *
     * @param filePath The file path for storing tasks.
     */
    public Einstein(String filePath) {
        assert filePath != null && !filePath.isEmpty() : "File path cannot be null or empty";

        ui = new Ui();

        storage = new Storage(filePath);

        try {
            tasks = new TaskList(storage.load());
        } catch (EinsteinException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    /**
     * Runs the main loop of the Einstein chatbot.
     * Continuously reads user input, executes commands, and displays results until exit.
     */
    public void run() {
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                assert fullCommand != null && !fullCommand.isEmpty() : "Command should not be null or empty";

                ui.showLine();
                Command c = Parser.parse(fullCommand);

                String result = c.execute(tasks, ui, storage);
                assert result != null : "Command execution result should not be null";

                isExit = c.isExit();
            } catch (EinsteinException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        assert input != null && !input.isEmpty() : "Input should not be null or empty";

        try {
            Command c = Parser.parse(input);

            String response = c.execute(tasks, ui, storage);
            assert response != null : "Response should not be null";
            return response;
        } catch (EinsteinException e) {
            return ui.showError(e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Einstein("data/einstein.txt").run();
    }
}
