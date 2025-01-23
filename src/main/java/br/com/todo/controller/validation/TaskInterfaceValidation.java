package br.com.todo.controller.validation;

import br.com.todo.controller.validation.Enum.ResultValidationEnum;
import br.com.todo.model.Task;

public interface TaskInterfaceValidation {
    ResultValidationEnum validateTask(Task task);
}
