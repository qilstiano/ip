package einstein.task;

/**
 * Represents a task that does not have a specific deadline or event time. A <code>Todo</code> object
 * corresponds to a task with a description and a status indicating whether the task is done or not.
 */
public class Todo extends Task {

    /**
     * Constructs a <code>Todo</code> object with a description. The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Todo(String description) {
        super(description);
    }

    /**
     * Returns a string representation of the todo task, formatted as "[T] description".
     * The status of the task is determined by its completion status.
     *
     * @return A string representation of the todo task.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
