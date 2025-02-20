package einstein.task;

import java.util.HashSet;
import java.util.Set;

import einstein.exception.EinsteinException;

/**
 * Represents a generic task in the Einstein task management system.
 * A Task object corresponds to a task with a description, a status
 * indicating whether the task is done or not, and a set of tags.
 */
public class Task {
    private String description;
    private boolean isDone;
    private Set<String> tags;

    /**
     * Constructs a Task object with a description. The task is initially marked as not done.
     *
     * @param description The description of the task.
     */
    public Task(String description) {
        assert description != null && !description.trim().isEmpty() : "Description cannot be null or empty";
        this.description = description;
        this.isDone = false;
        this.tags = new HashSet<>();
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
     * Adds a tag to the task.
     *
     * @param tag The tag to add.
     */
    public void addTag(String tag) {
        assert tag != null && !tag.trim().isEmpty() : "Tag cannot be null or empty";
        tags.add(tag);
    }

    /**
     * Removes a tag from the task.
     *
     * @param tag The tag to remove.
     */
    public void removeTag(String tag) throws EinsteinException {
        assert tag != null && !tag.trim().isEmpty() : "Tag cannot be null or empty";
        if (!tags.contains(tag)) {
            throw new EinsteinException("Tag does not exist!");
        }
        tags.remove(tag);
    }

    /**
     * Gets the tags associated with the task.
     *
     * @return A set of tags.
     */
    public Set<String> getTags() {
        return tags;
    }

    /**
     * Returns a string representation of the task, formatted as "[status_icon] description #tags".
     * The status icon represents whether the task is done or not.
     *
     * @return A string representation of the task.
     */
    @Override
    public String toString() {
        StringBuilder tagsString = new StringBuilder();
        for (String tag : tags) {
            tagsString.append(" #").append(tag);
        }
        return "[" + getStatusIcon() + "] " + description + tagsString.toString();
    }
}
