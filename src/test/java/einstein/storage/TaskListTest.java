package einstein.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import einstein.exception.EinsteinException;
import einstein.task.Task;
import einstein.task.Todo;
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
