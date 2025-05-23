package einstein.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import einstein.exception.EinsteinException;
import einstein.task.Deadline;
import einstein.task.Event;
import einstein.task.Task;
import einstein.task.Todo;

/**
 * Manages the storage of tasks in the Einstein task management system.
 * This class handles loading tasks from and saving tasks to a file.
 */
public class Storage {
    private String filePath;

    /**
     * Constructs a new Storage object with the specified file path.
     *
     * @param filePath The path to the file used for storing tasks.
     */
    public Storage(String filePath) {
        assert filePath != null && !filePath.isEmpty() : "File path cannot be null or empty";
        this.filePath = filePath;
    }

    /**
     * Loads tasks from the file specified in the constructor.
     *
     * @return An ArrayList of Task objects loaded from the file.
     * @throws EinsteinException If there's an error reading from the file, parsing the data,
     *                           or if corrupted data is found.
     */
    public ArrayList<Task> load() throws EinsteinException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!fileExists()) {
            return tasks;
        }

        try {
            List<String> lines = readLinesFromFile();
            for (String line : lines) {
                assert line != null && !line.isEmpty() : "Line cannot be null or empty";
                Task task = parseTask(line);
                tasks.add(task);
            }
        } catch (IOException e) {
            throw new EinsteinException("Error loading tasks from file: " + e.getMessage());
        }
        return tasks;
    }

    private boolean fileExists() {
        return Files.exists(Paths.get(filePath));
    }

    private List<String> readLinesFromFile() throws IOException {
        return Files.readAllLines(Paths.get(filePath));
    }

    private Task parseTask(String line) throws EinsteinException {
        String[] parts = line.split(" \\| ");
        validateParts(parts, line);
        String type = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String description = parts[2].trim();
        Task task = createTask(type, description, parts);
        if (isDone) {
            task.markAsDone();
        }
        return task;
    }

    private void validateParts(String[] parts, String line) throws EinsteinException {
        if (parts.length < 3) {
            throw new EinsteinException("Corrupted data found in file. Skipping line: " + line);
        }
    }

    private Task createTask(String type, String description, String[] parts) throws EinsteinException {
        switch (type) {
        case "T":
            return new Todo(description);
        case "D":
            return createDeadline(description, parts);
        case "E":
            return createEvent(description, parts);
        default:
            throw new EinsteinException("Unknown task type found. Skipping line: " + String.join(" | ", parts));
        }
    }

    private Task createDeadline(String description, String[] parts) throws EinsteinException {
        validateDeadlineParts(parts);
        LocalDateTime by = parseDateTime(parts[3].trim());
        return new Deadline(description, by);
    }

    private Task createEvent(String description, String[] parts) throws EinsteinException {
        validateEventParts(parts);
        LocalDateTime from = parseDateTime(parts[3].trim());
        LocalDateTime to = parseDateTime(parts[4].trim());
        return new Event(description, from, to);
    }

    private void validateDeadlineParts(String[] parts) throws EinsteinException {
        if (parts.length < 4) {
            throw new EinsteinException("Corrupted deadline data found. Skipping line: " + String.join(" | ", parts));
        }
    }

    private void validateEventParts(String[] parts) throws EinsteinException {
        if (parts.length < 5) {
            throw new EinsteinException("Corrupted event data found. Skipping line: " + String.join(" | ", parts));
        }
    }

    private LocalDateTime parseDateTime(String dateTimeString) throws EinsteinException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (DateTimeParseException e) {
            throw new EinsteinException("Invalid date/time format: " + dateTimeString);
        }
    }

    /**
     * Saves the given list of tasks to the file specified in the constructor.
     *
     * @param tasks The ArrayList of Task objects to be saved.
     * @throws EinsteinException If there's an error writing to the file.
     */
    public void save(ArrayList<Task> tasks) throws EinsteinException {
        assert tasks != null : "Tasks list cannot be null";
        try {
            createDataDirectory();
            String data = buildTaskData(tasks);
            writeDataToFile(data);
        } catch (IOException e) {
            throw new EinsteinException("Error saving tasks to file: " + e.getMessage());
        }
    }

    private void createDataDirectory() throws IOException {
        Files.createDirectories(Paths.get("data"));
    }

    private String buildTaskData(ArrayList<Task> tasks) {
        StringBuilder data = new StringBuilder();
        for (Task task : tasks) {
            assert task != null : "Task in the list cannot be null";
            data.append(formatTask(task));
        }
        return data.toString();
    }

    private String formatTask(Task task) {
        if (task instanceof Todo) {
            return formatTodo((Todo) task);
        } else if (task instanceof Deadline) {
            return formatDeadline((Deadline) task);
        } else if (task instanceof Event) {
            return formatEvent((Event) task);
        } else {
            return ""; // Handle unknown task types
        }
    }

    private String formatTodo(Todo todo) {
        return String.format("T | %s | %s\n", todo.getIsDone() ? "1" : "0", todo.getDescription());
    }

    private String formatDeadline(Deadline deadline) {
        return String.format("D | %s | %s | %s\n",
                deadline.getIsDone() ? "1" : "0",
                deadline.getDescription(),
                formatDateTime(deadline.getBy()));
    }

    private String formatEvent(Event event) {
        return String.format("E | %s | %s | %s | %s\n",
                event.getIsDone() ? "1" : "0",
                event.getDescription(),
                formatDateTime(event.getFrom()),
                formatDateTime(event.getTo()));
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return dateTime.format(formatter);
    }

    private void writeDataToFile(String data) throws IOException {
        Files.write(Paths.get(filePath), data.getBytes());
    }
}
