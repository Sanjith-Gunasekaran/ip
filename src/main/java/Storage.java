import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.util.List;
import java.util.Locale;

/** Loads and saves tasks using the application's local storage file. */
public class Storage {
    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormatter
            .ofPattern("uuuu-MM-dd HHmm", Locale.ENGLISH)
            .withResolverStyle(ResolverStyle.STRICT);

    private final Path file = Path.of("data", "storage.txt");

    /** Populates the task list with tasks previously saved on disk. */
    public void loadTasks(List<Task> list) {
        if (!Files.exists(file)) {
            return;
        }

        try {
            for (String line : Files.readAllLines(file)) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);
                Task task = switch (parts[0]) {
                case "T" -> new ToDo(parts[2]);
                case "D" -> new Deadline(parts[2],
                        LocalDateTime.parse(parts[3], DATE_TIME_FORMAT));
                case "E" -> new Event(parts[2],
                        LocalDateTime.parse(parts[3], DATE_TIME_FORMAT),
                        LocalDateTime.parse(parts[4], DATE_TIME_FORMAT));
                default -> throw new IllegalArgumentException("Unknown stored task type.");
                };

                if (parts[1].equals("1")) {
                    task.mark();
                }
                list.add(task);
            }
        } catch (IOException e) {
            System.out.println("Unable to load saved tasks.");
        }
    }

    /** Replaces the storage file with the current contents of the task list. */
    public void saveTasks(List<Task> list) {
        try {
            Files.createDirectories(file.getParent());
            try (var writer = Files.newBufferedWriter(file,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING)) {
                for (Task task : list) {
                    String status = task.isDone() ? "1" : "0";
                    if (task instanceof Deadline deadline) {
                        writer.write("D|" + status + "|" + task.getDescription()
                                + "|" + deadline.getBy().format(DATE_TIME_FORMAT));
                    } else if (task instanceof Event event) {
                        writer.write("E|" + status + "|" + task.getDescription()
                                + "|" + event.getFrom().format(DATE_TIME_FORMAT)
                                + "|" + event.getTo().format(DATE_TIME_FORMAT));
                    } else {
                        writer.write("T|" + status + "|" + task.getDescription());
                    }
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Unable to save tasks.");
        }
    }
}
