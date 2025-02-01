package einstein.storage;

import java.util.ArrayList;

import einstein.task.Task;
import einstein.exception.EinsteinException;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(int index) throws EinsteinException {
        if (index < 0 || index >= tasks.size()) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
        tasks.remove(index);
    }

    public void markTaskAsDone(int index) throws EinsteinException {
        if (index < 0 || index >= tasks.size()) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
        tasks.get(index).markAsDone();
    }

    public void markTaskAsNotDone(int index) throws EinsteinException {
        if (index < 0 || index >= tasks.size()) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
        tasks.get(index).markAsNotDone();
    }

    public ArrayList<Task> getTasks() {
        return tasks;
    }

    public int getTaskCount() {
        return tasks.size();
    }
}
