package einstein.task;

/**
 * Represents a to-do task in the Einstein task management system.
 * A to-do task is a simple task with a description and no specific deadline or time frame.
 */
public class Todo extends Task {

    /**
     * Constructs a new To-do task with the given description.
     *
     * @param description The description of the to-do task.
     */
    public Todo(String description) {
        super(description);
        assert !description.trim().isEmpty() : "Todo description cannot be null or empty";
    }

    /**
     * Gets the description of the to-do task.
     *
     * @return The description of the to-do task.
     */
    @Override
    public String getDescription() {
        String description = super.getDescription();
        assert description != null && !description.isEmpty() : "Todo description should not be null or empty";
        return description;
    }

    /**
     * Sets the description of the to-do task.
     *
     * @param description The new description of the to-do task.
     */
    @Override
    public void setDescription(String description) {
        assert description != null && !description.trim().isEmpty() : "New todo description cannot be null or empty";
        super.setDescription(description);
    }

    /**
     * Sets the done status of the to-do task.
     *
     * @param done The new done status of the to-do task.
     */
    @Override
    public void setDone(boolean done) {
        super.setDone(done);
        assert getIsDone() == done : "Todo done status should be correctly set";
    }

    /**
     * Returns a string representation of the To-do task.
     *
     * @return A string that represents the To-do task, including its type identifier [T],
     *         status (done or not done), and description.
     */
    @Override
    public String toString() {
        String result = "[T]" + super.toString();
        assert result.startsWith("[T]") : "Todo string representation should start with [T]";
        assert result.length() > 3 : "Todo string representation should include more than just the [T] prefix";
        return result;
    }
}
