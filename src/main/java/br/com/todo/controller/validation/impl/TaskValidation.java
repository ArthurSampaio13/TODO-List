package br.com.todo.controller.validation.impl;

import br.com.todo.controller.validation.Enum.ResultValidationEnum;
import br.com.todo.controller.validation.TaskInterfaceValidation;
import br.com.todo.model.Task;

public class TaskValidation implements TaskInterfaceValidation {
    @Override
    public ResultValidationEnum validateTask(Task task) {
        if (task.getNome() == null || task.getNome().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (task.getDescricao() == null || task.getDescricao().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (task.getStatus() == null) {
            return ResultValidationEnum.REJECTED;
        }
        if (task.getIdUsuario() < 0) {
            return ResultValidationEnum.REJECTED;
        }
        if (task.getPriority() == null) {
            return ResultValidationEnum.REJECTED;
        }
        return ResultValidationEnum.APPROVED;
    }
}
