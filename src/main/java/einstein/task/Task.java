package einstein.task;

/**
 * Represents a generic task. A <code>Task</code> object corresponds to a task with a description
 * and a status indicating whether the task is done or not.
 */
public class Task {
    public String description;
    public boolean isDone;

    /**
     * Constructs a <code>Task</code> object with a description. The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns the status icon for the task. The icon is "X" if the task is done, and a space (" ") if it is not done.
     *
     * @return A string representing the task's status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks the task as done by setting the status to true.
     */
    public void markAsDone() {
        this.isDone = true;
    }

    /**
     * Marks the task as not done by setting the status to false.
     */
    public void markAsNotDone() {
        this.isDone = false;
    }

    /**
     * Returns a string representation of the task, formatted as "[status_icon] description".
     * The status icon represents whether the task is done or not.
     *
     * @return A string representation of the task.
     */
    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
