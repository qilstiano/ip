package einstein.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import einstein.exception.EinsteinException;
import einstein.task.Task;
import einstein.task.Todo;

/**
 * Test class for the Storage class.
 * This class contains unit tests to verify the correct saving and loading of tasks.
 */
class StorageTest {

    /**
     * Tests the save and load functionality of the Storage class.
     * This test creates a temporary file, saves tasks to it, loads the tasks back,
     * and verifies that the loaded tasks match the original tasks.
     *
     * @throws EinsteinException if there's an error in saving or loading tasks
     * @throws IOException if there's an error in file operations
     */
    @Test
    void saveAndLoadTasks_validTasks_tasksSavedAndLoadedSuccessfully() throws EinsteinException, IOException {
        // Create a temporary file for testing
        String testFilePath = "data/test_tasks.txt";
        Files.deleteIfExists(Paths.get(testFilePath));

        // Create a TaskList with some tasks
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(new Todo("Read book"));
        tasks.add(new Todo("Write code"));

        // Save tasks to the file
        Storage storage = new Storage(testFilePath);
        storage.save(tasks);

        // Load tasks from the file
        ArrayList<Task> loadedTasks = storage.load();

        // Verify that the loaded tasks match the original tasks
        assertEquals(tasks.size(), loadedTasks.size());
        for (int i = 0; i < tasks.size(); i++) {
            assertEquals(tasks.get(i).toString(), loadedTasks.get(i).toString());
        }

        // Clean up the temporary file
        Files.deleteIfExists(Paths.get(testFilePath));
    }
}
