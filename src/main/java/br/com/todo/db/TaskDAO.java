package br.com.todo.db;

import br.com.todo.model.Task;
import br.com.todo.model.Enum.PriorityEnum;
import br.com.todo.model.Enum.StatusEnum;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskDAO {
    private Connection connection;

    public TaskDAO(Connection connection) {
        this.connection = connection;
    }

    public void deleteTasksByUsuarioId(int usuarioId) {
        String sql = "DELETE FROM tasks WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, usuarioId);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteTaskById(int id) {
        String sql = "DELETE FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void updateTask(Task task) {
        String sql = "UPDATE tasks SET nome = ?, description = ?, status = ?, priority = ?, data_termino = ?, category = ? WHERE id = ? AND user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, task.getNome());
            stmt.setString(2, task.getDescricao());
            stmt.setString(3, task.getStatus().getDescription());
            stmt.setString(4, task.getPriority().getDescription());
            stmt.setTimestamp(5, Timestamp.valueOf(task.getDataTermino().atStartOfDay()));
            stmt.setString(6, task.getCategoria());
            stmt.setLong(7, task.getId());
            stmt.setLong(8, task.getIdUsuario());

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tarefa atualizada com sucesso! Task ID: " + task.getId());
                System.out.println("Novos dados da task: " + task);
            } else {
                System.out.println("Nenhuma tarefa foi atualizada. Verifique o ID da tarefa.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar a tarefa", e);
        }
    }

    public Integer insertTask(Task task) {
        String sql = "INSERT INTO tasks (nome, description, status, priority, data_termino, user_id, category) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, task.getNome());
            stmt.setString(2, task.getDescricao());
            stmt.setString(3, task.getStatus().getDescription());
            stmt.setString(4, task.getPriority().getDescription());
            stmt.setTimestamp(5, Timestamp.valueOf(task.getDataTermino().atStartOfDay()));
            stmt.setLong(6, task.getIdUsuario());
            stmt.setString(7, task.getCategoria());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to insert task, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    return generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Failed to insert task, no ID obtained.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public List<Task> getTasksByUserId(long usuarioId) {
        List<Task> tasks = new ArrayList<>();
        String sql = "SELECT * FROM tasks WHERE user_id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, usuarioId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                String descricao = rs.getString("description");
                String status = rs.getString("status");
                String priority = rs.getString("priority");
                LocalDate dataTermino = rs.getTimestamp("data_termino").toLocalDateTime().toLocalDate();
                String categoria = rs.getString("category");

                tasks.add(new Task(id, nome, descricao, dataTermino, StatusEnum.getByDescription(status), PriorityEnum.getPriorityEnum(priority), categoria, usuarioId));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tasks;
    }

    public Task getTaskById(long id) {
        String sql = "SELECT * FROM tasks WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("nome");
                String descricao = rs.getString("description");
                String status = rs.getString("status");
                String priority = rs.getString("priority");
                LocalDate dataTermino = rs.getTimestamp("data_termino").toLocalDateTime().toLocalDate();
                String categoria = rs.getString("category");
                int idUsuario = rs.getInt("user_id");
                return new Task(id, nome, descricao, dataTermino, StatusEnum.getByDescription(status), PriorityEnum.getPriorityEnum(priority), categoria, idUsuario);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
