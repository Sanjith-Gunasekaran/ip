package heisenberg;

/** Formats the messages shown by the command-line and graphical interfaces. */
public class Ui {
    public void showWelcome() {
        System.out.print("""
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
             """);
    }

    public void showTaskList(TaskList taskList) {
        System.out.println(getTaskListMessage(taskList));
    }

    public void showMatchingTasks(TaskList matches) {
        System.out.println(getMatchingTasksMessage(matches));
    }

    public void showTaskAdded(Task task, TaskList taskList) {
        System.out.println(getTaskAddedMessage(task, taskList));
    }

    public void showTaskMarked(Task task) {
        System.out.println(getTaskMarkedMessage(task));
    }

    public void showTaskDeleted(Task task, TaskList taskList) {
        System.out.println(getTaskDeletedMessage(task, taskList));
    }

    public void showError(String message) {
        System.out.println(message);
    }

    public void showGoodbye() {
        System.out.print(getGoodbyeMessage());
    }

    public String getWelcomeMessage() {
        return """
                My name is Walter Hartwell White.
                I live at 308 Negra Arroyo Lane, Albuquerque, New Mexico, 87104.
                What can I do for you?""";
    }

    public String getTaskListMessage(TaskList taskList) {
        StringBuilder message = new StringBuilder("Here are the tasks in your list:");
        for (int taskNumber = 1; taskNumber <= taskList.size(); taskNumber++) {
            message.append(String.format("%n%d. %s", taskNumber, taskList.getTask(taskNumber)));
        }
        return message.toString();
    }

    public String getMatchingTasksMessage(TaskList matches) {
        StringBuilder message = new StringBuilder("Here are the matching tasks in your list:");
        for (int taskNumber = 1; taskNumber <= matches.size(); taskNumber++) {
            message.append(String.format("%n%d. %s", taskNumber, matches.getTask(taskNumber)));
        }
        return message.toString();
    }

    public String getTaskAddedMessage(Task task, TaskList taskList) {
        return String.format("Got it. I've added this task:%n%s%nNow you have %d tasks in the list.",
                task, taskList.size());
    }

    public String getTaskMarkedMessage(Task task) {
        return "Nice! I've marked this task as done:\n" + task;
    }

    public String getTaskDeletedMessage(Task task, TaskList taskList) {
        return String.format("Noted. I've removed this task:%n%s%nNow you have %d tasks in the list.",
                task, taskList.size());
    }

    public String getGoodbyeMessage() {
        return "Goodbye!";
    }
}
