package br.com.todo.view;

import br.com.todo.controller.TaskController;
import br.com.todo.controller.UserController;
import br.com.todo.controller.validation.Enum.ResultValidationEnum;
import br.com.todo.db.TaskDAO;
import br.com.todo.model.User;

import java.sql.Connection;
import java.util.Scanner;

public class UserView {
    private final UserController usuarioController;
    private final Scanner scanner;
    private final TaskDAO taskProxyDAO;

    public UserView(Connection connection) {
        this.usuarioController = new UserController(connection);
        this.scanner = new Scanner(System.in);
        this.taskProxyDAO = new TaskDAO(connection);
    }

    public void menu() {
        int opcao = 0;
        do {
            System.out.println("---- Menu de Usuário ----");
            System.out.println("1. Login");
            System.out.println("2. Cadastrar novo usuário");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.\n");
                continue;
            }

            switch (opcao) {
                case 1:
                    login();
                    break;
                case 2:
                    cadastrar();
                    break;
                case 3:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (opcao != 3);
    }

    private void login() {
        System.out.println("---- Login de Usuário ----");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        User userDTO = new User(email, senha);

        User usuario = usuarioController.login(userDTO);

        if (usuario != null) {
            System.out.println("Login realizado com sucesso! Bem-vindo, " + usuario.getNome() + "!");
            TaskController taskController = new TaskController(taskProxyDAO);
            TaskView taskView = new TaskView(taskController, usuario.getId(), usuarioController);
            taskView.menu();
        } else {
            System.out.println("Email ou senha inválidos.");
        }
    }

    private void cadastrar() {
        System.out.println("---- Cadastro de Novo Usuário ----");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        User userDTO = new User(nome, email, senha);

        ResultValidationEnum resultValidation = usuarioController.register(userDTO);

        if (resultValidation == ResultValidationEnum.APPROVED) {
            System.out.println("Usuário cadastrado com sucesso!");
            return;
        }
        System.out.println("Erro ao cadastrar usuário.");
    }
}
