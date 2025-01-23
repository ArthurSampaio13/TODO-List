package br.com.todo.controller.strategy;

import br.com.todo.model.Task;

import java.util.Comparator;
import java.util.List;

public class SortByPriority implements TaskSortingStrategy {
    @Override
    public List<Task> sort(List<Task> tasks) {
        return tasks.stream()
                .sorted(Comparator.comparing(Task::getPriority))
                .toList();
    }
}
