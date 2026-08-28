package heisenberg;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TaskListTest {
    @Test
    public void deleteTask_validTaskNumber_removesAndReturnsTask() {
        TaskList taskList = new TaskList();
        Task firstTask = new ToDo("read book");
        Task secondTask = new ToDo("return book");
        taskList.addTask(firstTask);
        taskList.addTask(secondTask);

        Task deletedTask = taskList.deleteTask(1);

        assertSame(firstTask, deletedTask);
        assertEquals(1, taskList.size());
        assertSame(secondTask, taskList.getTask(1));
    }

    @Test
    public void deleteTask_invalidTaskNumber_throwsInvalidTaskNumberException() {
        TaskList taskList = new TaskList();
        taskList.addTask(new ToDo("read book"));

        assertThrows(InvalidTaskNumberException.class, () -> taskList.deleteTask(0));
        assertThrows(InvalidTaskNumberException.class, () -> taskList.deleteTask(2));
    }
}
