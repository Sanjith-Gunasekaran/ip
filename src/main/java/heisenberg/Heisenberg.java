package heisenberg;

import java.util.Scanner;
import java.time.LocalDateTime;


public class Heisenberg {
    public static void main(String[] args) {
        UI ui = new UI();
        ui.showWelcome();
        TaskList taskList = new TaskList();
        Storage storage = new Storage();
        try {
            storage.loadTasks(taskList);
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
                        Task task = taskList.markTask(index);
                        storage.saveTasks(taskList);
                        ui.showTaskMarked(task);
                        break;
                    }

                    case LIST: {
                        parser.requireNoArguments();
                        ui.showTaskList(taskList);
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
                        taskList.addTask(curr);
                        storage.saveTasks(taskList);
                        ui.showTaskAdded(curr, taskList);
                        break;
                    }

                    case TODO: {
                        String description = parser.getDescription();
                        ToDo curr = new ToDo(description);
                        taskList.addTask(curr);
                        storage.saveTasks(taskList);
                        ui.showTaskAdded(curr, taskList);
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
                        taskList.addTask(curr);
                        storage.saveTasks(taskList);
                        ui.showTaskAdded(curr, taskList);
                        break;
                    }
                    case DELETE: {
                        int index = parser.getTaskNumber();
                        Task toRemove = taskList.deleteTask(index);
                        storage.saveTasks(taskList);
                        ui.showTaskDeleted(toRemove, taskList);
                        break;
                    }

                    case FIND: {
                        String keyword = parser.getKeyword();
                        TaskList matches = taskList.findTasks(keyword);
                        ui.showMatchingTasks(matches);
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
