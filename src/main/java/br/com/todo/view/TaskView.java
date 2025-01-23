package br.com.todo.view;

import br.com.todo.controller.TaskController;
import br.com.todo.controller.UserController;
import br.com.todo.controller.strategy.*;
import br.com.todo.controller.validation.Enum.ResultValidationEnum;
import br.com.todo.model.Task;
import br.com.todo.model.Enum.PriorityEnum;
import br.com.todo.model.Enum.StatusEnum;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class TaskView {
    private final Scanner scanner = new Scanner(System.in);
    private final TaskController taskController;
    private final UserController userController;
    private final long userId;
    private TaskSortingStrategy currentSortingStrategy;

    public TaskView(TaskController taskController, long userId, UserController userController) {
        this.taskController = taskController;
        this.userController = userController;
        this.userId = userId;
        this.currentSortingStrategy = new SortByPriority();
    }

    public void menu() {
        int option;
        do {
            showMenu();
            option = getOptionFromUser();

            switch (option) {
                case 1 -> displayTasks();
                case 2 -> addNewTask();
                case 3 -> deleteTask();
                case 4 -> updateTask();
                case 5 -> changeSortingStrategy();
                case 6 -> System.out.println("Saindo...");
                default -> System.out.println("Opção inválida.");
            }
        } while (option != 6);
    }

    private void showMenu() {
        System.out.println("\n---- Menu de Task ----");
        System.out.println("1. Mostrar as tasks");
        System.out.println("2. Criar uma task");
        System.out.println("3. Deletar uma task");
        System.out.println("4. Atualizar uma task");
        System.out.println("5. Alterar a forma de ordenação");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private int getOptionFromUser() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Opção inválida.");
            return -1;
        }
    }

    private void displayTasks() {
        taskController.setSortingStrategy(currentSortingStrategy);
        List<Task> tasks = taskController.getAllTasks(userId);

        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa encontrada.");
        } else {
            tasks.forEach(this::showTask);
        }
    }

    private void changeSortingStrategy() {
        System.out.println("\n---- Alterar forma de ordenação ----");
        System.out.println("1. Ordenar por prioridade");
        System.out.println("2. Ordenar por status de conclusão");
        System.out.println("3. Ordenar por categoria");
        System.out.print("Escolha uma opção: ");

        int sortOption = getOptionFromUser();
        currentSortingStrategy = switch (sortOption) {
            case 1 -> new SortByPriority();
            case 2 -> new SortByCompletionStatus();
            case 3 -> new SortByCategory();
            default -> {
                System.out.println("Opção inválida. Mantendo a ordenação atual.");
                yield currentSortingStrategy;
            }
        };
        System.out.println("Forma de ordenação alterada com sucesso.");
    }

    private void addNewTask() {
        Task newTask = getNewTaskDetails();
        if (newTask != null) {
            taskController.addTask(newTask);
            System.out.println("Tarefa adicionada com sucesso.");
        }
    }

    private void deleteTask() {
        int taskId = getTaskIdFromUser();
        taskController.deleteTask(taskId);
        System.out.println("Tarefa deletada com sucesso.");

    }

    private void updateTask() {
        int taskId = getTaskIdFromUser();
        Task task = taskController.getTaskById(taskId);

        if (task == null) {
            System.out.println("Tarefa não encontrada.");
            return;
        }

        showTask(task);
        Task updatedTask = setNewDataToTask(task);

        if (updatedTask != null && taskController.updateTask(updatedTask) == ResultValidationEnum.APPROVED) {
            System.out.println("Tarefa atualizada com sucesso.");
        } else {
            System.out.println("Erro ao atualizar a tarefa.");
        }
    }

    private Task getNewTaskDetails() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Descrição: ");
        String description = scanner.nextLine();
        System.out.print("Prioridade (1, 2, 3, 4, 5): ");
        String priority = scanner.nextLine();
        System.out.print("Categoria: ");
        String category = scanner.nextLine();
        System.out.print("Status (todo, doing e done): ");
        String status = scanner.nextLine();
        System.out.print("Data de término (yyyy-MM-dd): ");
        try {
            LocalDate dateTime = LocalDate.parse(scanner.nextLine(), formatter);
            return new Task(title, description, userId, dateTime, StatusEnum.getByDescription(status), PriorityEnum.getPriorityEnum(priority), category);
        } catch (Exception e) {
            System.out.println("Data inválida. Certifique-se de usar o formato correto.");
            return null;
        }
    }

    private int getTaskIdFromUser() {
        System.out.print("ID da tarefa: ");
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID inválido.");
            return -1;
        }
    }

    private Task setNewDataToTask(Task task) {
        System.out.println("\n---- Atualização da tarefa ----");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Descrição: ");
        String description = scanner.nextLine();
        System.out.print("Prioridade (1, 2, 3, 4, 5): ");
        String priority = scanner.nextLine();
        System.out.print("Categoria: ");
        String category = scanner.nextLine();
        System.out.print("Status (todo, doing e done): ");
        String status = scanner.nextLine();
        System.out.print("Data de término (yyyy-MM-dd): ");

        task.setNome(title);
        task.setDescricao(description);
        task.setPriority(PriorityEnum.getPriorityEnum(priority));
        task.setCategoria(category);
        task.setStatus(StatusEnum.getByDescription(status));

        try {
            LocalDate dateTime = LocalDate.parse(scanner.nextLine(), formatter);
            task.setDataTermino(dateTime);
            return task;
        } catch (Exception e) {
            System.out.println("Data inválida. Certifique-se de usar o formato correto.");
            return null;
        }
    }

    private void showTask(Task task) {
        System.out.println("\n---- Detalhes da Tarefa ----");
        System.out.println("ID: " + task.getId());
        System.out.println("Título: " + task.getNome());
        System.out.println("Descrição: " + task.getDescricao());
        System.out.println("Prioridade: " + task.getPriority());
        System.out.println("Categoria: " + task.getCategoria());
        System.out.println("Status: " + task.getStatus());
        System.out.println("Data de término: " + task.getDataTermino());
    }
}
