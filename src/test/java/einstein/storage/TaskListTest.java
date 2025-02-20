package einstein.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import einstein.exception.EinsteinException;
import einstein.task.Task;
import einstein.task.Todo;

/**
 * Test class for the TaskList class.
 * This class contains unit tests to verify the correct functionality of adding and deleting tasks.
 */
class TaskListTest {

    /**
     * Tests that a valid task can be added to the TaskList successfully.
     * Verifies that the task count increases by one after adding a task.
     */
    @Test
    void addTask_validTask_taskAddedSuccessfully() {
        TaskList taskList = new TaskList();
        Task task = new Todo("Read book");
        taskList.addTask(task);
        assertEquals(1, taskList.getTaskCount());
    }

    /**
     * Tests that a task can be deleted from the TaskList successfully using a valid index.
     * Verifies that the task count decreases by one after deleting a task.
     *
     * @throws EinsteinException if there's an error deleting the task
     */
    @Test
    void deleteTask_validIndex_taskDeletedSuccessfully() throws EinsteinException {
        TaskList taskList = new TaskList();
        Task task = new Todo("Read book");
        taskList.addTask(task);
        taskList.deleteTask(0);
        assertEquals(0, taskList.getTaskCount());
    }

    /**
     * Tests that attempting to delete a task with an invalid index throws an EinsteinException.
     * Verifies that an exception is thrown when trying to delete a task at an index that doesn't exist.
     */
    @Test
    void deleteTask_invalidIndex_throwsEinsteinException() {
        TaskList taskList = new TaskList();
        Task task = new Todo("Read book");
        taskList.addTask(task);
        assertThrows(EinsteinException.class, () -> taskList.deleteTask(1));
    }
}
