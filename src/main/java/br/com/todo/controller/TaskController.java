package br.com.todo.controller;

import br.com.todo.controller.strategy.SortByPriority;
import br.com.todo.controller.strategy.TaskSortingStrategy;
import br.com.todo.controller.validation.Enum.ResultValidationEnum;
import br.com.todo.controller.validation.TaskInterfaceValidation;
import br.com.todo.controller.validation.impl.TaskValidation;
import br.com.todo.db.TaskDAO;
import br.com.todo.model.Task;

import java.util.List;

public class TaskController {
    private final TaskInterfaceValidation taskValidation;
    private final TaskDAO database;
    private TaskSortingStrategy taskSortingStrategy;

    public TaskController(TaskDAO databaseTask) {
        this.database = databaseTask;
        this.taskValidation = new TaskValidation();
        this.taskSortingStrategy = new SortByPriority();
    }

    public void setSortingStrategy(TaskSortingStrategy taskSortingStrategy){
        this.taskSortingStrategy = taskSortingStrategy;
    }

    public ResultValidationEnum addTask(Task task) {
        ResultValidationEnum result = taskValidation.validateTask(task);
        if (result == ResultValidationEnum.REJECTED) {
            return result;
        }
        database.insertTask(task);
        return result;
    }

    public ResultValidationEnum updateTask(Task task) {
        ResultValidationEnum result = taskValidation.validateTask(task);
        if (result == ResultValidationEnum.REJECTED) {
            return result;
        }
        database.updateTask(task);
        return result;
    }

    public Task getTaskById(int taskId) {
        Task task = database.getTaskById(taskId);

        if (task == null) {
            return null;
        }
        ResultValidationEnum result = taskValidation.validateTask(task);
        if (result == ResultValidationEnum.REJECTED) {
            return null;
        }
        return task;
    }

    public ResultValidationEnum deleteTask(int taskId) {
        Task task = getTaskById(taskId);
        if (task != null) {
            database.deleteTaskById(taskId);
            return ResultValidationEnum.APPROVED;
        }
        return ResultValidationEnum.REJECTED;
    }

    public List<Task> getAllTasks(long userId) {
        List<Task> tasks = database.getTasksByUserId(userId);
        return taskSortingStrategy.sort(tasks);
    }

}
