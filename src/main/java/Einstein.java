import java.util.Scanner;

class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markAsDone() {
        this.isDone = true;
    }

    public void markAsNotDone() {
        this.isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}

class Todo extends Task {
    public Todo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

class Deadline extends Task {
    protected String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + by + ")";
    }
}

class Event extends Task {
    protected String from;
    protected String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + from + " to: " + to + ")";
    }
}

class EinsteinException extends Exception {
    public EinsteinException(String message) {
        super(message);
    }
}

public class Einstein {
    public String chatbotAscii;
    public String chatbotName = "Einstein";
    private Task[] tasks = new Task[100]; // Array to store Task objects
    private int taskCount = 0;

    // ANSI color codes for orange gradient
    private static final String[] ORANGE_GRADIENT = {
        "\u001B[38;5;202m", // Light orange
        "\u001B[38;5;208m", // Medium orange
        "\u001B[38;5;214m", // Bright orange
        "\u001B[38;5;220m"  // Yellow-orange
    };
    private static final String RESET = "\u001B[0m"; // Reset color

    public Einstein() {
        chatbotAscii =  "         _                   _           _            \r\n" + //
                        "        (_)                 / |_        (_)           \r\n" + //
                        " .---.  __   _ .--.   .--. `| |-'.---.  __   _ .--.   \r\n" + //
                        "/ /__\\\\[  | [ `.-. | ( (`\\] | | / /__\\\\[  | [ `.-. |  \r\n" + //
                        "| \\__., | |  | | | |  `'.'. | |,| \\__., | |  | | | |  \r\n" + //
                        " '.__.'[___][___||__][\\__) )\\__/ '.__.'[___][___||__] \r\n" + //
                        "                                                     ";
    }

    public void greeting() {
        System.out.println("____________________________________________________________");
        System.out.println(getGradientText(chatbotAscii));
        System.out.println("\nGuten tag, " + chatbotName + " here! What can I do for you?");
        System.out.println("____________________________________________________________");
    }

    public void farewell() {
        System.out.println("____________________________________________________________");
        System.out.println("\tBye, hope to see you again soon!");
        System.out.println("____________________________________________________________");
    }

    public void addTask(Task task) {
        if (taskCount < 100) {
            tasks[taskCount] = task;
            taskCount++;
            System.out.println("____________________________________________________________");
            System.out.println("Got it. I've added this task:");
            System.out.println("  " + task);
            System.out.println("Now you have " + taskCount + " tasks in the list.");
            System.out.println("____________________________________________________________");
        } else {
            System.out.println("____________________________________________________________");
            System.out.println("Sorry! As much as I'm a genius and REALLY smart, I can't store more than 100 tasks :(");
            System.out.println("____________________________________________________________");
        }
    }

    public void listTasks() {
        System.out.println("____________________________________________________________");

        if (taskCount == 0) {
            System.out.println("Hmmm, didn't find any tasks. Add some tasks!");
        } else {
            System.out.println("Here are the tasks in your list:");
            for (int i = 0; i < taskCount; i++) {
                System.out.println((i + 1) + "." + tasks[i]);
            }
        }

        System.out.println("____________________________________________________________");
    }

    public void markTaskAsDone(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsDone();
            System.out.println("____________________________________________________________");
            System.out.println("Nice! I've marked this task as done:");
            System.out.println("  " + tasks[taskIndex]);
            System.out.println("____________________________________________________________");
        } else {
            System.out.println("____________________________________________________________");
            System.out.println("Invalid task number!");
            System.out.println("____________________________________________________________");
        }
    }

    public void markTaskAsNotDone(int taskIndex) {
        if (taskIndex >= 0 && taskIndex < taskCount) {
            tasks[taskIndex].markAsNotDone();
            System.out.println("____________________________________________________________");
            System.out.println("Awesome, I've marked this task as not done yet, remember to get to it:");
            System.out.println("  " + tasks[taskIndex]);
            System.out.println("____________________________________________________________");
        } else {
            System.out.println("____________________________________________________________");
            System.out.println("Invalid task number!");
            System.out.println("____________________________________________________________");
        }
    }

    public void processCommands() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String userInput = scanner.nextLine().trim();

            try {
                if (userInput.equalsIgnoreCase("bye")) {
                    farewell();
                    break;

                } else if (userInput.equalsIgnoreCase("list")) {
                    listTasks();

                } else if (userInput.startsWith("mark ")) {
                    handleMarkCommand(userInput);

                } else if (userInput.startsWith("unmark ")) {
                    handleUnmarkCommand(userInput);

                } else if (userInput.startsWith("todo ")) {
                    handleTodoCommand(userInput);

                } else if (userInput.startsWith("deadline ")) {
                    handleDeadlineCommand(userInput);

                } else if (userInput.startsWith("event ")) {
                    handleEventCommand(userInput);

                } else {
                    throw new EinsteinException("ARGH! I do not understand you, which is weird, \nbecause I usually understand most things. Invalid command!");
                }

            } catch (EinsteinException e) {
                System.out.println("____________________________________________________________");
                System.out.println(e.getMessage());
                System.out.println("____________________________________________________________");
            } catch (NumberFormatException e) {
                System.out.println("____________________________________________________________");
                System.out.println("Invalid task number! Please give me something valid!");
                System.out.println("____________________________________________________________");
            }
        }
        scanner.close();
    }

    private void handleMarkCommand(String userInput) throws EinsteinException {
        try {
            int taskIndex = Integer.parseInt(userInput.substring(5).trim()) - 1;
            if (taskIndex < 0 || taskIndex >= taskCount) {
                throw new EinsteinException("Invalid task number! Please give me something valid!");
            }
            markTaskAsDone(taskIndex);
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    private void handleUnmarkCommand(String userInput) throws EinsteinException {
        try {
            int taskIndex = Integer.parseInt(userInput.substring(7).trim()) - 1;
            if (taskIndex < 0 || taskIndex >= taskCount) {
                throw new EinsteinException("Invalid task number! Please give me something valid!");
            }
            markTaskAsNotDone(taskIndex);
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    private void handleTodoCommand(String userInput) throws EinsteinException {
        String description = userInput.substring(5).trim();
        if (description.isEmpty()) {
            throw new EinsteinException("Nein! You have to give a description to your todo.");
        }
        addTask(new Todo(description));
    }

    private void handleDeadlineCommand(String userInput) throws EinsteinException {
        String[] parts = userInput.substring(9).split("/by", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new EinsteinException("Invalid deadline format! Use: deadline <description> /by <date>");
        }
        String description = parts[0].trim();
        String by = parts[1].trim();
        addTask(new Deadline(description, by));
    }

    private void handleEventCommand(String userInput) throws EinsteinException {
        String[] parts = userInput.substring(6).split("/from|/to", 3);
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new EinsteinException("Invalid event format! Use: event <description> /from <start> /to <end>");
        }
        String description = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();
        addTask(new Event(description, from, to));
    }

    // helper method to apply gradient colour to text (only for ASCII art) - created by DeepSeek
    private String getGradientText(String text) {
        StringBuilder gradientText = new StringBuilder();
        int length = text.length();
        for (int i = 0; i < length; i++) {
            int colorIndex = (i * ORANGE_GRADIENT.length) / length;
            gradientText.append(ORANGE_GRADIENT[colorIndex]).append(text.charAt(i));
        }
        gradientText.append(RESET);
        return gradientText.toString();
    }

    public static void main(String[] args) {
        Einstein einstein = new Einstein();
        einstein.greeting();
        einstein.processCommands();
    }
}