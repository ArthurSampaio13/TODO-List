package br.com.todo.controller.validation;

import br.com.todo.controller.validation.Enum.ResultValidationEnum;
import br.com.todo.model.User;

import java.util.List;

public interface UserInterfaceValidation {
    ResultValidationEnum validateUserLogin(User userDTO);
    ResultValidationEnum usuarioIsNotNull(User user);
    ResultValidationEnum validateUser(User user);
    ResultValidationEnum emailAlreadyExists(List<User> usuarios, String email);
}
