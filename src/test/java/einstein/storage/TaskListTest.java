package einstein.storage;

import einstein.exception.EinsteinException;
import einstein.task.Task;
import einstein.task.Todo;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    @Test
    void addTask_validTask_taskAddedSuccessfully() {
        TaskList taskList = new TaskList();
        Task task = new Todo("Read book");
        taskList.addTask(task);
        assertEquals(1, taskList.getTaskCount());
    }

    @Test
    void deleteTask_validIndex_taskDeletedSuccessfully() throws EinsteinException {
        TaskList taskList = new TaskList();
        Task task = new Todo("Read book");
        taskList.addTask(task);
        taskList.deleteTask(0);
        assertEquals(0, taskList.getTaskCount());
    }

    @Test
    void deleteTask_invalidIndex_throwsEinsteinException() {
        TaskList taskList = new TaskList();
        Task task = new Todo("Read book");
        taskList.addTask(task);
        assertThrows(EinsteinException.class, () -> taskList.deleteTask(1));
    }
}