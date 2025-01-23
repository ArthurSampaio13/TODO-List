package br.com.todo.controller.validation.impl;

import br.com.todo.controller.validation.Enum.ResultValidationEnum;
import br.com.todo.controller.validation.UserInterfaceValidation;
import br.com.todo.model.User;

import java.util.List;

public class UserValidation implements UserInterfaceValidation {

    @Override
    public ResultValidationEnum validateUserLogin(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (user.getSenha() == null || user.getSenha().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        return ResultValidationEnum.APPROVED;
    }

    @Override
    public ResultValidationEnum usuarioIsNotNull(User user) {
        if (user == null) {
            return ResultValidationEnum.REJECTED;
        }
        return ResultValidationEnum.APPROVED;
    }

    @Override
    public ResultValidationEnum validateUser(User user) {
        if (usuarioIsNotNull(user) == ResultValidationEnum.REJECTED) {
            return ResultValidationEnum.REJECTED;
        }
        if (user.getNome() == null || user.getNome().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        if (user.getSenha() == null || user.getSenha().isEmpty()) {
            return ResultValidationEnum.REJECTED;
        }
        return ResultValidationEnum.APPROVED;
    }

    @Override
    public ResultValidationEnum emailAlreadyExists(List<User> usuarios, String email) {
        for (User usuario : usuarios) {
            if (usuario.getEmail().equals(email)) {
                return ResultValidationEnum.REJECTED;
            }
        }
        return ResultValidationEnum.APPROVED;
    }
}
