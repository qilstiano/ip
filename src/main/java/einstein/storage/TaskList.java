package einstein.storage;

import java.util.ArrayList;

import einstein.task.Task;
import einstein.exception.EinsteinException;

/**
 * Manages a list of tasks. This class provides methods to add, delete, and modify tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    /**
     * Creates a task list initialized with the given tasks.
     *
     * @param tasks The list of tasks to initialize with.
     */
    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task from the task list by its index.
     *
     * @param index The index of the task to delete (zero-based).
     * @throws EinsteinException If the index is invalid.
     */
    public void deleteTask(int index) throws EinsteinException {
        if (index < 0 || index >= tasks.size()) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
        tasks.remove(index);
    }

    /**
     * Marks a task as done by its index.
     *
     * @param index The index of the task to mark as done (zero-based).
     * @throws EinsteinException If the index is invalid.
     */
    public void markTaskAsDone(int index) throws EinsteinException {
        if (index < 0 || index >= tasks.size()) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
        tasks.get(index).markAsDone();
    }

    /**
     * Marks a task as not done by its index.
     *
     * @param index The index of the task to mark as not done (zero-based).
     * @throws EinsteinException If the index is invalid.
     */
    public void markTaskAsNotDone(int index) throws EinsteinException {
        if (index < 0 || index >= tasks.size()) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
        tasks.get(index).markAsNotDone();
    }

    /**
     * Retrieves the list of tasks.
     *
     * @return An {@code ArrayList<Task>} containing all tasks.
     */
    public ArrayList<Task> getTasks() {
        return tasks;
    }

    /**
     * Retrieves the number of tasks in the list.
     *
     * @return The number of tasks.
     */
    public int getTaskCount() {
        return tasks.size();
    }
}
