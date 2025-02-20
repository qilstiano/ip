package einstein.ui;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

import einstein.task.Deadline;
import einstein.task.Event;
import einstein.task.Priority;
import einstein.task.Task;

/**
 * Represents the user interface for interacting with the Einstein task manager.
 * The <code>Ui</code> class provides methods to display information and read user input in a console-based environment.
 */
public class Ui {

    /**
     * Displays the welcome message when the user enters.
     */
    public String showWelcome() {
        return "Guten tag! I'm Einstein, how can I help you today? \n\n"
                + "Hint: If you need help type 'help'!";
    }

    /**
     * Displays the farewell message when the user exits.
     */
    public String showFarewell() {
        return "Bye, hope to see you again soon!";
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to be displayed.
     */
    public String showError(String message) {
        assert message != null && !message.isEmpty() : "Error message should not be null or empty";
        return message;
    }

    /**
     * Reads a command input from the user.
     *
     * @return The command entered by the user.
     */
    public String readCommand() {
        System.out.print("User\n> ");
        return new Scanner(System.in).nextLine().trim();
    }

    /**
     * Displays a message confirming that a task has been added.
     *
     * @param task      The task that was added.
     * @param taskCount The total number of tasks in the list after adding the task.
     */
    public String showTaskAdded(Task task, int taskCount) {
        assert task != null : "Added task should not be null";
        assert taskCount > 0 : "Task count should be positive after adding a task";
        return "Got it. I've added this task:" + "\n"
                + "  " + task + "\n"
                + "Now you have " + taskCount + " tasks in the list.";
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks The list of tasks to be displayed.
     */
    public String showTaskList(ArrayList<Task> tasks) {
        assert tasks != null : "Task list should not be null";
        StringBuilder output = new StringBuilder("Here are the tasks in your list:\n\n");
        if (tasks.isEmpty()) {
            output.append("Hmmm, didn't find any tasks. Add some tasks!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                assert tasks.get(i) != null : "Task in the list should not be null";
                output.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
            }
        }
        String result = output.toString();
        assert !result.isEmpty() : "Task list output should not be null or empty";
        return result;
    }

    /**
     * Displays the tasks occurring on a specific date.
     *
     * @param tasks The list of tasks to be checked for the specified date.
     * @param date  The date to filter the tasks by.
     */
    public String showTasksByDate(ArrayList<Task> tasks, LocalDate date) {
        assert tasks != null : "Task list should not be null";
        assert date != null : "Date should not be null";
        StringBuilder output = new StringBuilder("Here are the tasks occurring on "
                + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":\n");
        boolean found = false;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            assert task != null : "Task in the list should not be null";
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                LocalDateTime time = deadline.getBy();
                if (time.toLocalDate().equals(date)) {
                    output.append((i + 1)).append(".").append(deadline).append("\n");
                    found = true;
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                LocalDateTime from = event.getFrom();
                LocalDateTime to = event.getTo();
                if (from.toLocalDate().equals(date) || to.toLocalDate().equals(date)) {
                    output.append((i + 1)).append(".").append(event).append("\n");
                    found = true;
                }
            }
        }
        if (!found) {
            output.append("No tasks found for this date.");
        }
        String result = output.toString();
        assert !result.isEmpty() : "Tasks by date output should not be null or empty";
        return result;
    }

    /**
     * Displays a message when a tag is added to a task.
     *
     * @param task The task to which the tag was added.
     * @param tag  The tag that was added.
     * @return A string containing the message.
     */
    public String showTagAdded(Task task, String tag) {
        return "Tag added: " + tag + " to task " + task;
    }

    /**
     * Displays a message when a tag is removed from a task.
     *
     * @param task The task from which the tag was removed.
     * @param tag  The tag that was removed.
     * @return A string containing the message.
     */
    public String showTagRemoved(Task task, String tag) {
        return "Tag removed: " + tag + " from task " + task;
    }

    /**
     * Displays a message when the priority of a task is set.
     *
     * @param task The task whose priority was set.
     * @param priority The new priority.
     * @return A string containing the message.
     */
    public String showPrioritySet(Task task, Priority priority) {
        return "Priority set to " + priority + " for task: " + task;
    }
}
