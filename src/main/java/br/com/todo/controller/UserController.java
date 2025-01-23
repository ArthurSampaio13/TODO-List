package br.com.todo.controller;

import br.com.todo.controller.validation.Enum.ResultValidationEnum;
import br.com.todo.controller.validation.UserInterfaceValidation;
import br.com.todo.controller.validation.impl.UserValidation;
import br.com.todo.db.TaskDAO;
import br.com.todo.db.UserDAO;
import br.com.todo.model.User;

import java.sql.Connection;
import java.util.List;

public class UserController {
    private final UserInterfaceValidation userInterfaceValidation;
    private final TaskDAO taskProxyDAO;
    private final UserDAO usuarioProxy;

    public UserController(Connection connection) {
        this.usuarioProxy = new UserDAO(connection);
        this.taskProxyDAO = new TaskDAO(connection);
        this.userInterfaceValidation = new UserValidation();
    }

    public User login(User userDTO) {
        ResultValidationEnum resultValidation = userInterfaceValidation.validateUserLogin(userDTO);
        if (resultValidation == ResultValidationEnum.REJECTED) {
            return null;
        }

        User usuario = usuarioProxy.userLogin(userDTO);

        resultValidation = userInterfaceValidation.usuarioIsNotNull(usuario);
        if (resultValidation == ResultValidationEnum.REJECTED) {

            return null;
        }
        return usuario;
    }

    public ResultValidationEnum register(User userDTO) {
        ResultValidationEnum resultValidation = emailAlreadyExists(userDTO.getEmail());
        if (resultValidation == ResultValidationEnum.REJECTED) {
            return resultValidation;
        }

        User novoUsuario = new User(userDTO.getNome(), userDTO.getEmail(), userDTO.getSenha());
        resultValidation = userInterfaceValidation.validateUser(novoUsuario);
        if (resultValidation == ResultValidationEnum.REJECTED) {
            return resultValidation;
        }

        usuarioProxy.insertUser(novoUsuario);
        return ResultValidationEnum.APPROVED;
    }

    private ResultValidationEnum emailAlreadyExists(String email) {
        List<User> usuarios = usuarioProxy.getAllUsuarios();
        return userInterfaceValidation.emailAlreadyExists(usuarios, email);
    }
}
