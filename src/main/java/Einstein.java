import java.util.ArrayList;
import java.util.Scanner;

// Imports for file reading and writing
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;
import java.util.List;

// Imports for Date and Time
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
    protected LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[D]" + super.toString() + " (by: " + by.format(formatter) + ")";
    }
}

class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mm a");
        return "[E]" + super.toString() + " (from: " + from.format(formatter) + " to: " + to.format(formatter) + ")";
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
    private ArrayList<Task> tasks = new ArrayList<>(); // Use ArrayList to store tasks

    private static final String DATA_FILE_PATH = Paths.get("data", "duke.txt")
    .toString(); // OS-independent path

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
                        " '.__.'[___][___||__][\\__) )\\__/ '.__.'[___][___||__]  v1.0\r\n" + //
                        "                                                     ";
        
        loadTasksFromFile(); // Load tasks when the chatbot starts
    }

    public void greeting() {
        System.out.println("____________________________________________________________");
        System.out.println(getGradientText(chatbotAscii));
        System.out.println(getGradientText("\nEinstein\nGuten tag, " + chatbotName + " here! What can I do for you?"));
        System.out.println("____________________________________________________________");
    }

    public void farewell() {
        System.out.println("____________________________________________________________");
        System.out.println(getGradientText("Einstein\n\tBye, hope to see you again soon!"));
        System.out.println("____________________________________________________________");
    }

    public void addTask(Task task) {
        tasks.add(task);
        saveTasksToFile(); // Save tasks after adding

        System.out.println("____________________________________________________________");
        System.out.println(getGradientText("Einstein\nGot it. I've added this task:"));
        System.out.println("  " + task);
        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
        System.out.println("____________________________________________________________");
    }

    public void listTasks() {
        System.out.println("____________________________________________________________");
        System.out.println(getGradientText("Einstein\nHere are the tasks in your list:"));

        if (tasks.isEmpty()) {
            System.out.println("Hmmm, didn't find any tasks. Add some tasks!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }

        System.out.println("____________________________________________________________");
    }

    public void markTaskAsDone(int taskIndex) throws EinsteinException {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.get(taskIndex).markAsDone();
            saveTasksToFile(); // Save tasks after marking as done

            System.out.println("____________________________________________________________");
            System.out.println(getGradientText("Einstein\nNice! I've marked this task as done:"));
            System.out.println("  " + tasks.get(taskIndex));
            System.out.println("____________________________________________________________");
        } else {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    public void markTaskAsNotDone(int taskIndex) throws EinsteinException {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            tasks.get(taskIndex).markAsNotDone();
            System.out.println("____________________________________________________________");
            System.out.println(getGradientText("Einstein\nAwesome, I've marked this task as not done yet, remember to get to it:"));
            System.out.println("  " + tasks.get(taskIndex));
            System.out.println("____________________________________________________________");
        } else {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    public void deleteTask(int taskIndex) throws EinsteinException {
        if (taskIndex >= 0 && taskIndex < tasks.size()) {
            Task removedTask = tasks.remove(taskIndex);
            saveTasksToFile();

            System.out.println("____________________________________________________________");
            System.out.println(getGradientText("Einstein\nNoted. I've atomized this task:"));
            System.out.println("  " + removedTask);
            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
            System.out.println("____________________________________________________________");
        } else {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    public void displayHelp() {
        System.out.println("____________________________________________________________");
        System.out.println(getGradientText("Einstein\nHere are the commands I understand:"));
        System.out.println("1. todo <description> - Add a todo task.");
        System.out.println("   Example: todo read book");
        System.out.println("2. deadline <description> /by <date> - Add a deadline task.");
        System.out.println("   Example: deadline return book /by Sunday");
        System.out.println("3. event <description> /from <start> /to <end> - Add an event task.");
        System.out.println("   Example: event project meeting /from Mon 2pm /to 4pm");
        System.out.println("4. list - List all tasks.");
        System.out.println("   Example: list");
        System.out.println("5. mark <task number> - Mark a task as done.");
        System.out.println("   Example: mark 1");
        System.out.println("6. unmark <task number> - Mark a task as not done.");
        System.out.println("   Example: unmark 1");
        System.out.println("7. delete <task number> - Delete a task.");
        System.out.println("   Example: delete 1");
        System.out.println("8. help - Display this help message.");
        System.out.println("   Example: help");
        System.out.println("9. bye - Exit the program.");
        System.out.println("   Example: bye");
        System.out.println("____________________________________________________________");
    }

    public void processCommands() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("User\n> ");
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

                } else if (userInput.startsWith("delete ")) {
                    handleDeleteCommand(userInput);

                } else if (userInput.equalsIgnoreCase("help")) {
                    displayHelp();

                } else {
                    throw new EinsteinException("ARGH! I do not understand you, which is weird, \nbecause I usually understand most things. Invalid command!");
                }

            } catch (EinsteinException e) {
                System.out.println("____________________________________________________________");
                System.out.println(getGradientText("Einstein\n" + e.getMessage()));
                System.out.println("____________________________________________________________");
            } catch (NumberFormatException e) {
                System.out.println("____________________________________________________________");
                System.out.println(getGradientText("Einstein\nInvalid task number! Please give me something valid!"));
                System.out.println("____________________________________________________________");
            }
        }
        scanner.close();
    }

    private void handleMarkCommand(String userInput) throws EinsteinException {
        try {
            int taskIndex = Integer.parseInt(userInput.substring(5).trim()) - 1;
            markTaskAsDone(taskIndex);
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    private void handleUnmarkCommand(String userInput) throws EinsteinException {
        try {
            int taskIndex = Integer.parseInt(userInput.substring(7).trim()) - 1;
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
        LocalDateTime by = parseDateTime(parts[1].trim());
        addTask(new Deadline(description, by));
    }

    private void handleEventCommand(String userInput) throws EinsteinException {
        String[] parts = userInput.substring(6).split("/from|/to", 3);
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new EinsteinException("Invalid event format! Use: event <description> /from <start> /to <end>");
        }
        String description = parts[0].trim();
        LocalDateTime from = parseDateTime(parts[1].trim());
        LocalDateTime to = parseDateTime(parts[2].trim());
        addTask(new Event(description, from, to));
    }

    public void handleDeleteCommand(String userInput) throws EinsteinException {
        try {
            if (tasks.size() == 0) {
                throw new EinsteinException("We don't have any tasks yet, my dear friend. Add some first!");
            }
            String taskNumberStr = userInput.substring(7).trim();
            if (taskNumberStr.isEmpty()) {
                throw new EinsteinException("Nein! You must provide a task number to delete. \nUse: delete <task number>");
            }
            int taskIndex = Integer.parseInt(taskNumberStr) - 1;
            deleteTask(taskIndex);
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    private void saveTasksToFile() {
        try {

            // Ensure the data directory exists
            Files.createDirectories(Paths.get("data"));

            // Converts tasks to a string format 
            StringBuilder data = new StringBuilder();

            for (Task task : tasks) {
                if (task instanceof Todo) {
                    data.append("T | ").append(task.isDone ? "1" : "0").append(" | ")
                    .append(task.description).append("\n");
                } else if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    data.append("D | ").append(deadline.isDone ? "1" : "0").append(" | ").append(deadline.description)
                    .append(" | ").append(deadline.by).append("\n");
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    data.append("E | ").append(event.isDone ? "1" : "0").append(" | ").append(event.description).append(" | ")
                    .append(event.from).append(" | ").append(event.to).append("\n");
                }
            }
            
            // Write to file
            Files.write(Paths.get(DATA_FILE_PATH), data.toString().getBytes());
        } catch (IOException e) {

            System.out.println("____________________________________________________________");
            System.out.println(getGradientText("Einstein\nError saving tasks to file: " + e.getMessage()));
            System.out.println("____________________________________________________________");        
        }
    }

    private void loadTasksFromFile() {
        try {
            // Check if the file exists
            if (!Files.exists(Paths.get(DATA_FILE_PATH))) {
                System.out.println(getGradientText("Einstein\nNo existing tasks found. Starting with an empty list."));
                return;
            }
    
            // Read all lines from the file
            List<String> lines = Files.readAllLines(Paths.get(DATA_FILE_PATH));
            for (String line : lines) {
                String[] parts = line.split(" \\| ");
                if (parts.length < 3) {
                    System.out.println(getGradientText("Einstein\nCorrupted data found in file. Skipping line: " + line));
                    continue;
                }
    
                String type = parts[0].trim();
                boolean isDone = parts[1].trim().equals("1");
                String description = parts[2].trim();
    
                Task task;
                switch (type) {
                    case "T":
                        task = new Todo(description);
                        break;
                    case "D":
                        if (parts.length < 4) {
                            System.out.println(getGradientText("Einstein\nCorrupted deadline data found. Skipping line: " + line));
                            continue;
                        }
                        task = new Deadline(description, parts[3].trim());
                        break;
                    case "E":
                        if (parts.length < 5) {
                            System.out.println(getGradientText("Einstein\nCorrupted event data found. Skipping line: " + line));
                            continue;
                        }
                        task = new Event(description, parts[3].trim(), parts[4].trim());
                        break;
                    default:
                        System.out.println(getGradientText("Einstein\nUnknown task type found. Skipping line: " + line));
                        continue;
                }
    
                if (isDone) {
                    task.markAsDone();
                }
                tasks.add(task);
            }
        } catch (IOException e) {
            System.out.println("____________________________________________________________");
            System.out.println(getGradientText("Einstein\nError loading tasks from file: " + e.getMessage()));
            System.out.println("____________________________________________________________");
        }
    }

    private LocalDateTime parseDateTime(String dateTimeStr) throws EinsteinException {
        try {
            // Try parsing with date and time format (e.g., "2/12/2019 1800")
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            throw new EinsteinException("Invalid date/time format! Use: dd/MM/yyyy HHmm (e.g., 2/12/2019 1800)");
        }
    }

    public void listTasksByDate(LocalDate date) {
        System.out.println("____________________________________________________________");
        System.out.println(getGradientText("Einstein\nHere are the tasks occurring on " + date.format(DateTimeFormatter.ofPattern("MMM dd yyyy")) + ":"));
    
        boolean found = false;
        for (int i = 0; i < tasks.size(); i++) {
            Task task = tasks.get(i);
            if (task instanceof Deadline) {
                Deadline deadline = (Deadline) task;
                if (deadline.by.toLocalDate().equals(date)) {
                    System.out.println((i + 1) + "." + deadline);
                    found = true;
                }
            } else if (task instanceof Event) {
                Event event = (Event) task;
                if (event.from.toLocalDate().equals(date) || event.to.toLocalDate().equals(date)) {
                    System.out.println((i + 1) + "." + event);
                    found = true;
                }
            }
        }
    
        if (!found) {
            System.out.println("No tasks found for this date.");
        }
        System.out.println("____________________________________________________________");
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