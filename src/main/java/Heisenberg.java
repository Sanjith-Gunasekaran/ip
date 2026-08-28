import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;


public class Heisenberg {
    public static void main(String[] args) {
        UI ui = new UI();
        ui.showWelcome();
        List<Task> list = new ArrayList<>();
        Storage storage = new Storage();
        try {
            storage.loadTasks(list);
        } catch (StorageException e) {
            ui.showError(e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        String input;
        boolean isRunning = true;
        while(isRunning) {
            input = scanner.nextLine();
            try {
                Parser parser = new Parser(input);

                switch (parser.getCommand()) {
                    case MARK: {
                        int index = parser.getTaskNumber();
                        if (index > list.size() || index < 1) {
                            throw new InvalidTaskNumberException("This task does not exist!");
                        }
                        list.get(index - 1).mark();
                        storage.saveTasks(list);
                        ui.showTaskMarked(list.get(index - 1));
                        break;
                    }

                    case LIST: {
                        parser.requireNoArguments();
                        ui.showTaskList(list);
                        break;
                    }

                    case BYE: {
                        parser.requireNoArguments();
                        ui.showGoodbye();
                        isRunning = false;
                        break;
                    }

                    case DEADLINE: {
                        String description = parser.getDescription();
                        LocalDateTime by = parser.getDeadlineDateTime();
                        Deadline curr = new Deadline(description, by);
                        list.add(curr);
                        storage.saveTasks(list);
                        ui.showTaskAdded(curr, list);
                        break;
                    }

                    case TODO: {
                        String description = parser.getDescription();
                        ToDo curr = new ToDo(description);
                        list.add(curr);
                        storage.saveTasks(list);
                        ui.showTaskAdded(curr, list);
                        break;
                    }

                    case EVENT: {
                        String description = parser.getDescription();
                        LocalDateTime from = parser.getEventFromDateTime();
                        LocalDateTime to = parser.getEventToDateTime();
                        if (!from.isBefore(to)) {
                            throw new InvalidFormatException("Event must start before it ends.");
                        }
                        Event curr = new Event(description, from, to);
                        list.add(curr);
                        storage.saveTasks(list);
                        ui.showTaskAdded(curr, list);
                        break;
                    }
                    case DELETE: {
                        int index = parser.getTaskNumber();
                        if (index > list.size() || index < 1) {
                            throw new InvalidTaskNumberException("This task does not exist!");
                        }
                        Task toRemove = list.get(index - 1);
                        list.remove(index - 1);
                        storage.saveTasks(list);
                        ui.showTaskDeleted(toRemove, list);
                        break;
                    }
                }
            } catch(InvalidCommandException
                    | InvalidFormatException
                    | InvalidTaskNumberException
                    | StorageException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
