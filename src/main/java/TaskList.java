import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/** Stores tasks and provides operations that use the displayed one-based task numbers. */
public class TaskList implements Iterable<Task> {
    private final List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTask(int taskNumber) {
        return tasks.get(toIndex(taskNumber));
    }

    public Task deleteTask(int taskNumber) {
        return tasks.remove(toIndex(taskNumber));
    }

    public Task editTask(int taskNumber, Task replacement) {
        return tasks.set(toIndex(taskNumber), replacement);
    }

    public Task markTask(int taskNumber) {
        Task task = getTask(taskNumber);
        task.mark();
        return task;
    }

    public int size() {
        return tasks.size();
    }

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    @Override
    public Iterator<Task> iterator() {
        return Collections.unmodifiableList(tasks).iterator();
    }

    private int toIndex(int taskNumber) {
        if (taskNumber < 1 || taskNumber > tasks.size()) {
            throw new InvalidTaskNumberException("This task does not exist!");
        }
        return taskNumber - 1;
    }
}
