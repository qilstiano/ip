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
        try {
            if (!Files.exists(Paths.get(filePath))) {
                return tasks;
            }

            List<String> lines = Files.readAllLines(Paths.get(filePath));
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

            for (String line : lines) {
                assert line != null && !line.isEmpty() : "Line cannot be null or empty";

                String[] parts = line.split(" \\| ");
                assert parts.length >= 3 : "Each line should have at least 3 parts";
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
        assert tasks != null : "Loaded tasks list should not be null";
        return tasks;
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
            Files.createDirectories(Paths.get("data"));
            StringBuilder data = new StringBuilder();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy HHmm");

            for (Task task : tasks) {
                assert task != null : "Task in the list cannot be null";
                if (task instanceof Todo) {
                    data.append("T | ").append(task.getIsDone() ? "1" : "0").append(" | ").append(task.getDescription())
                            .append("\n");
                } else if (task instanceof Deadline) {
                    Deadline deadline = (Deadline) task;
                    data.append("D | ").append(deadline.getIsDone() ? "1" : "0")
                            .append(" | ").append(deadline.getDescription())
                            .append(" | ").append(deadline.getBy().format(formatter)).append("\n");
                } else if (task instanceof Event) {
                    Event event = (Event) task;
                    data.append("E | ").append(event.getIsDone() ? "1" : "0")
                            .append(" | ").append(event.getDescription())
                            .append(" | ").append(event.getFrom().format(formatter)).append(" | ")
                            .append(event.getTo().format(formatter)).append("\n");
                }
            }
            Files.write(Paths.get(filePath), data.toString().getBytes());
        } catch (IOException e) {
            throw new EinsteinException("Error saving tasks to file: " + e.getMessage());
        }
    }
}
