import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

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

        Scanner scanner = new Scanner(System.in);
        String input;
        boolean running = true;
        while(running) {
            input = scanner.nextLine();
            String[] parts = input.trim().split("\\s+");
            CommandType command = CommandType.valueOf(parts[0].toUpperCase());

            switch(command) {
                case MARK: {
                    if (checkMark(parts)) {
                        int index = Integer.parseInt(parts[1]);
                        list.get(index - 1).mark();
                        System.out.println("Nice! I've marked this task as done: \n" + list.get(index - 1));
                    }
                    break;
                }

                case LIST: {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < list.size(); i++) {
                        System.out.printf("%d. %s%n", i + 1, list.get(i));
                    }
                    break;
                }

                case BYE: {
                    System.out.print("Goodbye!");
                    running = false;
                    break;
                }

                case DEADLINE: {
                    String description = getDescription(parts);
                    String by = getByOrFrom(parts);
                    Deadline curr = new Deadline(description, by);
                    list.add(curr);
                    printTask(curr, list.size());
                    break;
                }

                case TODO: {
                    String description = getDescription(parts);
                    ToDo curr = new ToDo(description);
                    list.add(curr);
                    printTask(curr, list.size());
                    break;
                }

                case EVENT: {
                    String description = getDescription(parts);
                    String from = getByOrFrom(parts);
                    String to = getTo(parts);
                    Event curr = new Event(description, from, to);
                    list.add(curr);
                    printTask(curr, list.size());
                    break;
                }
            }
        }
    }
    private static boolean checkMark(String[] parts) {
        try {
            Integer.parseInt(parts[1]);
            return parts.length == 2;
        } catch (NumberFormatException e) {
            return false;
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
        return sb.toString().trim();
    }

    private static String getByOrFrom(String[] parts) {
        int i = 1;
        while(!parts[i].equals("/by") && !parts[i].equals("/from")) {
            i++;
        }

        StringBuilder sb = new StringBuilder();
        for(int j = i + 1; j < parts.length; j++) {
            if(parts[j].equals("/to")) {
                break;
            }
            sb.append(parts[j]).append(" ");
        }
        return sb.toString().trim();
    }

    private static String getTo(String[] parts) {
        int i = 1;
        while(!parts[i].equals("/to")) {
            i++;
        }
        StringBuilder sb = new StringBuilder();
        for(int j = i + 1; j < parts.length; j++) {
            sb.append(parts[j]).append(" ");
        }
        return sb.toString().trim();
    }

    private static void printTask(Task task, int size) {
        System.out.printf("Got it. I've added this task: \n %s \n Now you have %d tasks in the list. \n", task, size);
    }
}
