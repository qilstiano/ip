package einstein.storage;

import java.util.List;
import java.util.ArrayList;

// Imports for Date and Time
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

// Imports for file reading and writing
import java.nio.file.Paths;
import java.nio.file.Files;
import java.io.IOException;

import einstein.task.Todo;
import einstein.task.Task;
import einstein.task.Event;
import einstein.task.Deadline;
import einstein.exception.EinsteinException;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public ArrayList<Task> load() throws EinsteinException {
        ArrayList<Task> tasks = new ArrayList<>();
        if (!fileExists()) {
            return tasks;
        }

        try {
            List<String> lines = readLinesFromFile();
            for (String line : lines) {
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
        return switch (type) {
            case "T" -> new Todo(description);
            case "D" -> createDeadline(description, parts);
            case "E" -> createEvent(description, parts);
            default ->
                    throw new EinsteinException("Unknown task type found. Skipping line: " + String.join(" | ", parts));
        };
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

    public void save(ArrayList<Task> tasks) throws EinsteinException {
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
        return String.format("T | %s | %s\n", todo.isDone ? "1" : "0", todo.description);
    }

    private String formatDeadline(Deadline deadline) {
        return String.format("D | %s | %s | %s\n",
                deadline.isDone ? "1" : "0",
                deadline.description,
                formatDateTime(deadline.by));
    }

    private String formatEvent(Event event) {
        return String.format("E | %s | %s | %s | %s\n",
                event.isDone ? "1" : "0",
                event.description,
                formatDateTime(event.from),
                formatDateTime(event.to));
    }

    private String formatDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");
        return dateTime.format(formatter);
    }

    private void writeDataToFile(String data) throws IOException {
        Files.write(Paths.get(filePath), data.getBytes());
    }
}
