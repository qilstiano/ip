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
