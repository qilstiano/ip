package einstein.task;

/**
 * Represents a generic task in the Einstein task management system.
 * A Task object corresponds to a task with a description and a status
 * indicating whether the task is done or not.
 */
public class Task {
    private String description;
    private boolean isDone;

    /**
     * Constructs a Task object with a description. The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null && !description.trim().isEmpty() : "Description cannot be null or empty";
        this.description = description;
        this.isDone = false;
    }

    /**
     * Gets the description of the task.
     *
     * @return The description of the task.
     */
    public String getDescription() {
        assert description != null : "Description should not be null";
        return description;
    }

    /**
     * Sets the description of the task.
     *
     * @param description The new description of the task.
     */
    public void setDescription(String description) {
        assert description != null && !description.trim().isEmpty() : "New description cannot be null or empty";
        this.description = description;
    }

    /**
     * Checks if the task is done.
     *
     * @return true if the task is done, false otherwise.
     */
    public boolean getIsDone() {
        return isDone;
    }

    /**
     * Sets the done status of the task.
     *
     * @param done The new done status of the task.
     */
    public void setDone(boolean done) {
        isDone = done;
    }

    /**
     * Returns the status icon for the task. The icon is "X" if the task is done, and a space (" ") if it is not done.
     *
     * @return A string representing the task's status icon.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
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
