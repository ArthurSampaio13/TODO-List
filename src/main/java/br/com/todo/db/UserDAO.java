package br.com.todo.db;

import br.com.todo.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    private Connection connection;
    private TaskDAO tasksdao;

    public UserDAO(Connection connection) {
        this.connection = connection;
        this.tasksdao = new TaskDAO(connection);
    }

    public User userLogin(User user) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getSenha());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                long id = rs.getLong("id");
                String nome = rs.getString("name");
                return new User(id, nome, user.getEmail(), user.getSenha());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public User insertUser(User usuario) {
        String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    int generatedId = generatedKeys.getInt(1);
                    usuario.setId(generatedId);
                } else {
                    throw new SQLException("Falha ao inserir o usuário, nenhum ID obtido.");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuario;
    }

    public void updateUser(User usuario) {
        String sql = "UPDATE users SET name = ?, email = ?, password = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setLong(4, usuario.getId());
            stmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteUser(int id) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.execute();
            tasksdao.deleteTasksByUsuarioId(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public User getUsuarioById(int id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                String nome = rs.getString("name");
                String email = rs.getString("email");
                String senha = rs.getString("password");
                return new User(id, nome, email, senha);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public List<User> getAllUsuarios() {
        List<User> usuarios = new ArrayList<>();
        String sql = "SELECT * FROM users";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("name");
                String email = rs.getString("email");
                String senha = rs.getString("password");
                usuarios.add(new User(id, nome, email, senha));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return usuarios;
    }
}
