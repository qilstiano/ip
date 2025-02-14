package einstein.ui;

import java.util.ArrayList;
import java.util.Scanner;
import einstein.task.Task;
import einstein.task.Event;
import einstein.task.Deadline;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents the user interface for interacting with the Einstein task manager.
 * The <code>Ui</code> class provides methods to display information and read user input in a console-based environment.
 */
public class Ui {

    private static final String[] ORANGE_GRADIENT = {
            "\u001B[38;5;202m", // Light orange
            "\u001B[38;5;208m", // Medium orange
            "\u001B[38;5;214m", // Bright orange
            "\u001B[38;5;220m"  // Yellow-orange
    };
    private static final String RESET = "\u001B[0m"; // Reset color

    /**
     * Displays the welcome message along with a chatbot ASCII art.
     */
    public String showWelcome() {
        String chatbotAscii = "         _                   _           _            \r\n" + //
                "        (_)                 / |_        (_)           \r\n" + //
                " .---.  __   _ .--.   .--. `| |-'.---.  __   _ .--.   \r\n" + //
                "/ /__\\\\[  | [ `.-. | ( (`\\] | | / /__\\\\[  | [ `.-. |  \r\n" + //
                "| \\_., | |  | | | |  `'.'. | |,| \\_., | |  | | | |  \r\n" + //
                " '.__.'[___][___||__][\\__) )\\__/ '.__.'[___][___||__]  v0.1.1\r\n" + //
                "                                                     ";
        return "____________________________________________________________\n"
                + chatbotAscii + "\n"
                + "\nEinstein\nGuten tag, Einstein here! What can I do for you?" + "\n"
                + "____________________________________________________________";
    }

    /**
     * Displays the farewell message when the user exits.
     */
    public String showFarewell() {
        return "____________________________________________________________\n"
                + "Einstein\n\tBye, hope to see you again soon!" + "\n"
                + "____________________________________________________________";
    }

    /**
     * Displays a line to separate sections of output.
     */
    public String showLine() {
        return "____________________________________________________________";
    }

    /**
     * Displays an error message.
     *
     * @param message The error message to be displayed.
     */
    public String showError(String message) {
        return "Einstein\n" + message;
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
     * @param task The task that was added.
     * @param taskCount The total number of tasks in the list after adding the task.
     */
    public String showTaskAdded(Task task, int taskCount) {
        return "Einstein\nGot it. I've added this task:" + "\n"
                + "  " + task + "\n"
                + "Now you have " + taskCount + " tasks in the list.";
    }

    /**
     * Displays the list of tasks.
     *
     * @param tasks The list of tasks to be displayed.
     */
    public String showTaskList(ArrayList<Task> tasks) {
        StringBuilder output = new StringBuilder("Einstein\nHere are the tasks in your list:\n");
        if (tasks.isEmpty()) {
            output.append("Hmmm, didn't find any tasks. Add some tasks!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                output.append((i + 1)).append(".").append(tasks.get(i)).append("\n");
            }
        }
        return output.toString();
    }

    /**
     * Displays the tasks occurring on a specific date.
     *
     * @param tasks The list of tasks to be checked for the specified date.
     * @param date The date to filter the tasks by.
     */
    public String showTasksByDate(ArrayList<Task> tasks, LocalDate date) {
        StringBuilder output = new StringBuilder("Einstein\nHere are the tasks occurring on "
                + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":\n");
        boolean found = false;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.by.toLocalDate().equals(date)) {
                    output.append((i + 1)).append(".").append(deadline).append("\n");
                    found = true;
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (event.from.toLocalDate().equals(date) || event.to.toLocalDate().equals(date)) {
                    output.append((i + 1)).append(".").append(event).append("\n");
                    found = true;
                }
            }
        }
        if (!found) {
            output.append("No tasks found for this date.");
        }
        return output.toString();
    }
}
