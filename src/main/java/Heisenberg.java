import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;


public class Heisenberg {
    public static void main(String[] args) {
        String heisenberg = """
                ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠿⠿⠿⠿⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                ⣿⣿⣿⣿⣿⣿⣿⣿⠟⠋⠁⠀⠀⠀⠀⠀⠀⠀⠀⠉⠻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                ⣿⣿⣿⣿⣿⣿⣿⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢺⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                ⣿⣿⣿⣿⣿⣿⣿⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠆⠜⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                ⣿⣿⣿⣿⠿⠿⠛⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠻⣿⣿⣿⣿⣿
                ⣿⣿⡏⠁⠀⠀⠀⠀⠀⣀⣠⣤⣤⣶⣶⣶⣶⣶⣦⣤⡄⠀⠀⠀⠀⢀⣴⣿⣿⣿⣿⣿
                ⣿⣿⣷⣄⠀⠀⠀⢠⣾⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⢿⡧⠇⢀⣤⣶⣿⣿⣿⣿⣿⣿⣿
                ⣿⣿⣿⣿⣿⣿⣾⣮⣭⣿⡻⣽⣒⠀⣤⣜⣭⠐⢐⣒⠢⢰⢸⣿⣿⣿⣿⣿⣿⣿⣿⣿
                ⣿⣿⣿⣿⣿⣿⣿⣏⣿⣿⣿⣿⣿⣿⡟⣾⣿⠂⢈⢿⣷⣞⣸⣿⣿⣿⣿⣿⣿⣿⣿⣿
                ⣿⣿⣿⣿⣿⣿⣿⣿⣽⣿⣿⣷⣶⣾⡿⠿⣿⠗⠈⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                ⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⡿⠻⠋⠉⠑⠀⠀⢘⢻⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                ⣿⣿⣿⣿⣿⣿⣿⡿⠟⢹⣿⣿⡇⢀⣶⣶⠴⠶⠀⠀⢽⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                ⣿⣿⣿⣿⣿⣿⡿⠀⠀⢸⣿⣿⠀⠀⠣⠀⠀⠀⠀⠀⡟⢿⣿⣿⣿⣿⣿⣿⣿⣿⣿⣿
                ⣿⣿⣿⡿⠟⠋⠀⠀⠀⠀⠹⣿⣧⣀⠀⠀⠀⠀⡀⣴⠁⢘⡙⢿⣿⣿⣿⣿⣿⣿⣿⣿
                ⠉⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠈⠙⢿⠗⠂⠄⠀⣴⡟⠀⠀⡃⠀⠉⠉⠟⡿⣿⣿⣿⣿
                ⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢷⠾⠛⠂⢹⠀⠀⠀⢡⠀⠀⠀⠀⠀⠙⠛⠿⢿
                My name is Walter Hartwell White. 
                I live at 308 Negra Arroyo Lane, Albuquerque, New Mexico, 87104. 
                What can I do for you?
             """;

        System.out.print(heisenberg);
        List<Task> list = new ArrayList<>();
        readTasks(list);

        Scanner scanner = new Scanner(System.in);
        String input;
        boolean running = true;
        while(running) {
            input = scanner.nextLine();
            try {
                if (input.trim().isEmpty()) {
                    throw new InvalidCommandException("Unknown command was given.");
                }
                String[] parts = input.trim().split("\\s+");
                CommandType command;
                try {
                    command = CommandType.valueOf(parts[0].toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new InvalidCommandException("Unknown command was given.");
                }

                switch (command) {
                    case MARK: {
                        if (checkMarkNDelete(parts)) {
                            int index = Integer.parseInt(parts[1]);
                            if (index > list.size() || index < 1) {
                                throw new InvalidTaskNumberException("This task does not exist!");
                            }
                            list.get(index - 1).mark();
                            saveTasks(list);
                            System.out.println("Nice! I've marked this task as done: \n" + list.get(index - 1));
                        } else {
                            throw new InvalidFormatException("Command is formatted incorrectly.");
                        }
                        break;
                    }

                    case LIST: {
                        if (parts.length != 1) {
                            throw new InvalidFormatException("Command is formatted incorrectly.");
                        }
                        System.out.println("Here are the tasks in your list:");
                        for (int i = 0; i < list.size(); i++) {
                            System.out.printf("%d. %s%n", i + 1, list.get(i));
                        }
                        break;
                    }

                    case BYE: {
                        if (parts.length != 1) {
                            throw new InvalidFormatException("Command is formatted incorrectly.");
                        }
                        System.out.print("Goodbye!");
                        running = false;
                        break;
                    }

                    case DEADLINE: {
                        String description = getDescription(parts);
                        if (description.isEmpty()) {
                            throw new InvalidFormatException("Task is formatted incorrectly.");
                        }
                        String by = getByOrFrom(parts, CommandType.DEADLINE);
                        Deadline curr = new Deadline(description, by);
                        list.add(curr);
                        saveTasks(list);
                        printTask(curr, list.size());
                        break;
                    }

                    case TODO: {
                        String description = getDescription(parts);
                        if (description.isEmpty()) {
                            throw new InvalidFormatException("Task is formatted incorrectly.");
                        }
                        ToDo curr = new ToDo(description);
                        list.add(curr);
                        saveTasks(list);
                        printTask(curr, list.size());
                        break;
                    }

                    case EVENT: {
                        String description = getDescription(parts);
                        if (description.isEmpty()) {
                            throw new InvalidFormatException("Task is formatted incorrectly.");
                        }
                        String from = getByOrFrom(parts, CommandType.EVENT);
                        String to = getTo(parts);
                        Event curr = new Event(description, from, to);
                        list.add(curr);
                        saveTasks(list);
                        printTask(curr, list.size());
                        break;
                    }
                    case DELETE: {
                        if (checkMarkNDelete(parts)) {
                            int index = Integer.parseInt(parts[1]);
                            if (index > list.size() || index < 1) {
                                throw new InvalidTaskNumberException("This task does not exist!");
                            }
                            Task toRemove = list.get(index - 1);
                            list.remove(index - 1);
                            saveTasks(list);
                            System.out.printf("Noted. I've removed this task: \n %s \n Now you have %d tasks in the list. \n", toRemove, list.size());
                        } else {
                            throw new InvalidFormatException("Command is formatted incorrectly.");
                        }
                        break;
                    }
                }
            } catch(InvalidCommandException
                    | InvalidFormatException
                    | InvalidTaskNumberException e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private static boolean checkMarkNDelete(String[] parts) {
        if(parts.length != 2) {
            return false;
        }
        try {
            Integer.parseInt(parts[1]);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void readTasks(List<Task> list) {
        Path file = Path.of("data", "storage.txt");

        if (!Files.exists(file)) {
            return;
        }

        try {
            for (String line : Files.readAllLines(file)) {
                if (line.isBlank()) {
                    continue;
                }

                String[] parts = line.split("\\|", -1);
                if (parts.length < 2 || (!parts[1].equals("0") && !parts[1].equals("1"))) {
                    System.out.println("Skipping corrupted task record.");
                    continue;
                }
                Task task;

                switch (parts[0]) {
                case "T":
                    if (parts.length != 3 || parts[2].isBlank()) {
                        System.out.println("Skipping corrupted task record.");
                        continue;
                    }
                    task = new ToDo(parts[2]);
                    break;
                case "D":
                    if (parts.length != 4 || parts[2].isBlank() || parts[3].isBlank()) {
                        System.out.println("Skipping corrupted task record.");
                        continue;
                    }
                    task = new Deadline(parts[2], parts[3]);
                    break;
                case "E":
                    if (parts.length != 5 || parts[2].isBlank()
                            || parts[3].isBlank() || parts[4].isBlank()) {
                        System.out.println("Skipping corrupted task record.");
                        continue;
                    }
                    task = new Event(parts[2], parts[3], parts[4]);
                    break;
                default:
                    System.out.println("Skipping corrupted task record.");
                    continue;
                }

                if (parts[1].equals("1")) {
                    task.mark();
                }
                list.add(task);
            }
        } catch (IOException e) {
            System.out.println("Unable to load saved tasks.");
        }
    }

    private static void saveTasks(List<Task> list) {
        Path file = Path.of("data", "storage.txt");

        try {
            Files.createDirectories(file.getParent());
            try (var writer = Files.newBufferedWriter(file,
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING)) {
                for (Task task : list) {
                    String status = task.isDone() ? "1" : "0";
                    if (task instanceof Deadline deadline) {
                        writer.write("D|" + status + "|" + task.getDescription()
                                + "|" + deadline.getBy());
                    } else if (task instanceof Event event) {
                        writer.write("E|" + status + "|" + task.getDescription()
                                + "|" + event.getFrom() + "|" + event.getTo());
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

    private static String getDescription(String[] parts) {
        StringBuilder sb = new StringBuilder();
        for(int i = 1; i < parts.length; i++) {
            if(parts[i].equals("/by") || parts[i].equals("/from")) {
                break;
            }
            sb.append(parts[i]).append(" ");
        }
        String value = sb.toString().trim();
        if (value.isEmpty()) {
            throw new InvalidFormatException("Task is formatted incorrectly.");
        }
        return value;
    }

    private static String getByOrFrom(String[] parts, CommandType command) {
        int i = 1;
        while(i < parts.length && !(command == CommandType.DEADLINE && parts[i].equals("/by")) && !(command == CommandType.EVENT && parts[i].equals("/from"))) {
            if(command == CommandType.EVENT && parts[i].equals("/to")) {
                throw new InvalidFormatException("Task is formatted incorrectly.");
            }

            i++;
        }

        if(i == parts.length) {
            throw new InvalidFormatException("Task is formatted incorrectly.");
        }

        StringBuilder sb = new StringBuilder();
        for(int j = i + 1; j < parts.length; j++) {
            if(parts[j].equals("/to")) {
                break;
            }
            sb.append(parts[j]).append(" ");
        }
        String value = sb.toString().trim();
        if (value.isEmpty()) {
            throw new InvalidFormatException("Task is formatted incorrectly.");
        }
        return value;
    }

    private static String getTo(String[] parts) {
        int i = 1;
        while(i < parts.length && !parts[i].equals("/to")) {
            i++;
        }

        if(i == parts.length) {
            throw new InvalidFormatException("Task is formatted incorrectly.");
        }

        StringBuilder sb = new StringBuilder();
        for(int j = i + 1; j < parts.length; j++) {
            sb.append(parts[j]).append(" ");
        }
        String value = sb.toString().trim();
        if (value.isEmpty()) {
            throw new InvalidFormatException("Task is formatted incorrectly.");
        }
        return value;
    }

    private static void printTask(Task task, int size) {
        System.out.printf("Got it. I've added this task: \n %s \n Now you have %d tasks in the list. \n", task, size);
    }
}
