package heisenberg;

import java.time.LocalDateTime;
import java.util.Scanner;

public class Heisenberg {
    public static void main(String[] args) {
        Ui ui = new Ui();
        ui.showWelcome();
        TaskList taskList = new TaskList();
        Storage storage = new Storage();
        try {
            storage.loadTasks(taskList);
        } catch (StorageException e) {
            ui.showError(e.getMessage());
        }

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        while (isRunning) {
            String input = scanner.nextLine();
            try {
                Parser parser = new Parser(input);

                switch (parser.getCommand()) {
                    case MARK: {
                        int taskNumber = parser.getTaskNumber();
                        Task task = taskList.markTask(taskNumber);
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
                        LocalDateTime deadlineDateTime = parser.getDeadlineDateTime();
                        Deadline deadline = new Deadline(description, deadlineDateTime);
                        taskList.addTask(deadline);
                        storage.saveTasks(taskList);
                        ui.showTaskAdded(deadline, taskList);
                        break;
                    }

                    case TODO: {
                        String description = parser.getDescription();
                        ToDo todo = new ToDo(description);
                        taskList.addTask(todo);
                        storage.saveTasks(taskList);
                        ui.showTaskAdded(todo, taskList);
                        break;
                    }

                    case EVENT: {
                        String description = parser.getDescription();
                        LocalDateTime startDateTime = parser.getEventFromDateTime();
                        LocalDateTime endDateTime = parser.getEventToDateTime();
                        if (!startDateTime.isBefore(endDateTime)) {
                            throw new InvalidFormatException("Event must start before it ends.");
                        }
                        Event event = new Event(description, startDateTime, endDateTime);
                        taskList.addTask(event);
                        storage.saveTasks(taskList);
                        ui.showTaskAdded(event, taskList);
                        break;
                    }

                    case DELETE: {
                        int taskNumber = parser.getTaskNumber();
                        Task removedTask = taskList.deleteTask(taskNumber);
                        storage.saveTasks(taskList);
                        ui.showTaskDeleted(removedTask, taskList);
                        break;
                    }
                }
            } catch (InvalidCommandException
                    | InvalidFormatException
                    | InvalidTaskNumberException
                    | StorageException e) {
                ui.showError(e.getMessage());
            }
        }
    }
}
