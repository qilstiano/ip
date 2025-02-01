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

class EinsteinException extends Exception {
    public EinsteinException(String message) {
        super(message);
    }
}

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

class Ui {
    private static final String[] ORANGE_GRADIENT = {
        "\u001B[38;5;202m", // Light orange
        "\u001B[38;5;208m", // Medium orange
        "\u001B[38;5;214m", // Bright orange
        "\u001B[38;5;220m"  // Yellow-orange
    };
    private static final String RESET = "\u001B[0m"; // Reset color

    public void showWelcome() {
        String chatbotAscii = "         _                   _           _            \r\n" + //
                              "        (_)                 / |_        (_)           \r\n" + //
                              " .---.  __   _ .--.   .--. `| |-'.---.  __   _ .--.   \r\n" + //
                              "/ /__\\\\[  | [ `.-. | ( (`\\] | | / /__\\\\[  | [ `.-. |  \r\n" + //
                              "| \\__., | |  | | | |  `'.'. | |,| \\__., | |  | | | |  \r\n" + //
                              " '.__.'[___][___||__][\\__) )\\__/ '.__.'[___][___||__]  v1.0\r\n" + //
                              "                                                     ";
        System.out.println("____________________________________________________________");
        System.out.println(getGradientText(chatbotAscii));
        System.out.println(getGradientText("\nEinstein\nGuten tag, Einstein here! What can I do for you?"));
        System.out.println("____________________________________________________________");
    }

    public void showFarewell() {
        System.out.println("____________________________________________________________");
        System.out.println(getGradientText("Einstein\n\tBye, hope to see you again soon!"));
        System.out.println("____________________________________________________________");
    }

    public void showLine() {
        System.out.println("____________________________________________________________");
    }

    public void showError(String message) {
        System.out.println(getGradientText("Einstein\n" + message));
    }

    public String readCommand() {
        System.out.print("User\n> ");
        return new Scanner(System.in).nextLine().trim();
    }

    public void showTaskAdded(Task task, int taskCount) {
        System.out.println(getGradientText("Einstein\nGot it. I've added this task:"));
        System.out.println("  " + task);
        System.out.println("Now you have " + taskCount + " tasks in the list.");
    }

    public void showTaskList(ArrayList<Task> tasks) {
        System.out.println(getGradientText("Einstein\nHere are the tasks in your list:"));
        if (tasks.isEmpty()) {
            System.out.println("Hmmm, didn't find any tasks. Add some tasks!");
        } else {
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + "." + tasks.get(i));
            }
        }
    }

    public void showTasksByDate(ArrayList<Task> tasks, LocalDate date) {
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
    }

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
}

class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws EinsteinException {
        ArrayList<Task> tasks = new ArrayList<>();
        try {
            if (!Files.exists(Paths.get(filePath))) {
                return tasks;
            }

            List<String> lines = Files.readAllLines(Paths.get(filePath));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

            for (String line : lines) {
                String[] parts = line.split(" \\| ");
                if (parts.length < 3) {
                    throw new EinsteinException("Corrupted data found in file. Skipping line: " + line);
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
                            throw new EinsteinException("Corrupted deadline data found. Skipping line: " + line);
                        }
                        LocalDateTime by = LocalDateTime.parse(parts[3].trim(), formatter);
                        task = new Deadline(description, by);
                        break;
                    case "E":
                        if (parts.length < 5) {
                            throw new EinsteinException("Corrupted event data found. Skipping line: " + line);
                        }
                        LocalDateTime from = LocalDateTime.parse(parts[3].trim(), formatter);
                        LocalDateTime to = LocalDateTime.parse(parts[4].trim(), formatter);
                        task = new Event(description, from, to);
                        break;
                    default:
                        throw new EinsteinException("Unknown task type found. Skipping line: " + line);
                }

                if (isDone) {
                    task.markAsDone();
                }
                tasks.add(task);
            }
        } catch (IOException | DateTimeParseException e) {
            throw new EinsteinException("Error loading tasks from file: " + e.getMessage());
        }
        return tasks;
    }

    public void save(ArrayList<Task> tasks) throws EinsteinException {
        try {
            Files.createDirectories(Paths.get("data"));
            StringBuilder data = new StringBuilder();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

            for (Task task : tasks) {
                if (task instanceof Todo) {
                    data.append("T | ").append(task.isDone ? "1" : "0").append(" | ").append(task.description).append("\n");
                } else if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    data.append("D | ").append(deadline.isDone ? "1" : "0").append(" | ").append(deadline.description)
                        .append(" | ").append(deadline.by.format(formatter)).append("\n");
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    data.append("E | ").append(event.isDone ? "1" : "0").append(" | ").append(event.description)
                        .append(" | ").append(event.from.format(formatter)).append(" | ").append(event.to.format(formatter)).append("\n");
                }
            }
            Files.write(Paths.get(filePath), data.toString().getBytes());
        } catch (IOException e) {
            throw new EinsteinException("Error saving tasks to file: " + e.getMessage());
        }
    }
}

class TaskList {
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

interface Command {
    void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException;
    boolean isExit();
}

class ExitCommand implements Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        ui.showFarewell();
    }

    @Override
    public boolean isExit() {
        return true;
    }
}

class ListCommand implements Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        ui.showTaskList(tasks.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

class ListByDateCommand implements Command {
    private LocalDate date;

    public ListByDateCommand(String fullCommand) throws EinsteinException {
        try {
            this.date = LocalDate.parse(fullCommand.substring(5).trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (DateTimeParseException e) {
            throw new EinsteinException("Invalid date format! Use: list yyyy-MM-dd");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        ui.showTasksByDate(tasks.getTasks(), date);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

class MarkCommand implements Command {
    private int taskIndex;

    public MarkCommand(String fullCommand) throws EinsteinException {
        try {
            this.taskIndex = Integer.parseInt(fullCommand.substring(5).trim()) - 1;
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        tasks.markTaskAsDone(taskIndex);
        storage.save(tasks.getTasks());
        ui.showLine();
        ui.showTaskList(tasks.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

class UnmarkCommand implements Command {
    private int taskIndex;

    public UnmarkCommand(String fullCommand) throws EinsteinException {
        try {
            this.taskIndex = Integer.parseInt(fullCommand.substring(7).trim()) - 1;
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        tasks.markTaskAsNotDone(taskIndex);
        storage.save(tasks.getTasks());
        ui.showLine();
        ui.showTaskList(tasks.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

class AddTodoCommand implements Command {
    private String description;

    public AddTodoCommand(String fullCommand) throws EinsteinException {
        this.description = fullCommand.substring(5).trim();
        if (description.isEmpty()) {
            throw new EinsteinException("Nein! You have to give a description to your todo.");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        Task task = new Todo(description);
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.getTaskCount());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

class AddDeadlineCommand implements Command {
    private String description;
    private LocalDateTime by;

    public AddDeadlineCommand(String fullCommand) throws EinsteinException {
        String[] parts = fullCommand.substring(9).split("/by", 2);
        if (parts.length < 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            throw new EinsteinException("Invalid deadline format! Use: deadline <description> /by <date>");
        }
        this.description = parts[0].trim();
        this.by = parseDateTime(parts[1].trim());
    }

    private LocalDateTime parseDateTime(String dateTimeStr) throws EinsteinException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            throw new EinsteinException("Invalid date/time format! Use: dd/MM/yyyy HHmm (e.g., 2/12/2019 1800)");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        Task task = new Deadline(description, by);
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.getTaskCount());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

class AddEventCommand implements Command {
    private String description;
    private LocalDateTime from;
    private LocalDateTime to;

    public AddEventCommand(String fullCommand) throws EinsteinException {
        String[] parts = fullCommand.substring(6).split("/from|/to", 3);
        if (parts.length < 3 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            throw new EinsteinException("Invalid event format! Use: event <description> /from <start> /to <end>");
        }
        this.description = parts[0].trim();
        this.from = parseDateTime(parts[1].trim());
        this.to = parseDateTime(parts[2].trim());
    }

    private LocalDateTime parseDateTime(String dateTimeStr) throws EinsteinException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateTimeStr, formatter);
        } catch (DateTimeParseException e) {
            throw new EinsteinException("Invalid date/time format! Use: dd/MM/yyyy HHmm (e.g., 2/12/2019 1800)");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        Task task = new Event(description, from, to);
        tasks.addTask(task);
        storage.save(tasks.getTasks());
        ui.showTaskAdded(task, tasks.getTaskCount());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

class DeleteCommand implements Command {
    private int taskIndex;

    public DeleteCommand(String fullCommand) throws EinsteinException {
        try {
            this.taskIndex = Integer.parseInt(fullCommand.substring(7).trim()) - 1;
        } catch (NumberFormatException e) {
            throw new EinsteinException("Invalid task number! Please give me something valid!");
        }
    }

    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        Task removedTask = tasks.getTasks().get(taskIndex);
        tasks.deleteTask(taskIndex);
        storage.save(tasks.getTasks());
        ui.showLine();
        ui.showTaskList(tasks.getTasks());
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

class HelpCommand implements Command {
    @Override
    public void execute(TaskList tasks, Ui ui, Storage storage) throws EinsteinException {
        ui.showLine();
        System.out.println("Here are the commands I understand:");
        System.out.println("1. todo <description> - Add a todo task.");
        System.out.println("   Example: todo read book");
        System.out.println("2. deadline <description> /by <date> - Add a deadline task.");
        System.out.println("   Example: deadline return book /by 2/12/2019 1800");
        System.out.println("3. event <description> /from <start> /to <end> - Add an event task.");
        System.out.println("   Example: event project meeting /from 2/12/2019 1400 /to 2/12/2019 1600");
        System.out.println("4. list - List all tasks.");
        System.out.println("   Example: list");
        System.out.println("5. list <date> - List tasks occurring on a specific date (format: yyyy-MM-dd).");
        System.out.println("   Example: list 2019-12-02");
        System.out.println("6. mark <task number> - Mark a task as done.");
        System.out.println("   Example: mark 1");
        System.out.println("7. unmark <task number> - Mark a task as not done.");
        System.out.println("   Example: unmark 1");
        System.out.println("8. delete <task number> - Delete a task.");
        System.out.println("   Example: delete 1");
        System.out.println("9. help - Display this help message.");
        System.out.println("   Example: help");
        System.out.println("10. bye - Exit the program.");
        System.out.println("   Example: bye");
        ui.showLine();
    }

    @Override
    public boolean isExit() {
        return false;
    }
}

 class Parser {
    public static Command parse(String fullCommand) throws EinsteinException {
        if (fullCommand.equalsIgnoreCase("bye")) {
            return new ExitCommand();
        } else if (fullCommand.equalsIgnoreCase("list")) {
            return new ListCommand();
        } else if (fullCommand.startsWith("list ")) {
            return new ListByDateCommand(fullCommand);
        } else if (fullCommand.startsWith("mark ")) {
            return new MarkCommand(fullCommand);
        } else if (fullCommand.startsWith("unmark ")) {
            return new UnmarkCommand(fullCommand);
        } else if (fullCommand.startsWith("todo ")) {
            return new AddTodoCommand(fullCommand);
        } else if (fullCommand.startsWith("deadline ")) {
            return new AddDeadlineCommand(fullCommand);
        } else if (fullCommand.startsWith("event ")) {
            return new AddEventCommand(fullCommand);
        } else if (fullCommand.startsWith("delete ")) {
            return new DeleteCommand(fullCommand);
        } else if (fullCommand.equalsIgnoreCase("help")) {
            return new HelpCommand();
        } else {
            throw new EinsteinException("ARGH! I do not understand you, which is weird, \nbecause I usually understand most things. Invalid command!");
        }
    }
}

public class Einstein {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    public Einstein(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        try {
            tasks = new TaskList(storage.load());
        } catch (EinsteinException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    public void run() {
        ui.showWelcome();
        boolean isExit = false;
        while (!isExit) {
            try {
                String fullCommand = ui.readCommand();
                ui.showLine();
                Command c = Parser.parse(fullCommand);
                c.execute(tasks, ui, storage);
                isExit = c.isExit();
            } catch (EinsteinException e) {
                ui.showError(e.getMessage());
            } finally {
                ui.showLine();
            }
        }
    }

    public static void main(String[] args) {
        new Einstein("data/duke.txt").run();
    }
}