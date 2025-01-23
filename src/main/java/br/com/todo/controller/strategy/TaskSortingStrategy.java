package br.com.todo.controller.strategy;

import br.com.todo.model.Task;

import java.util.List;

public interface TaskSortingStrategy {
    List<Task> sort(List<Task> tasks);
}
