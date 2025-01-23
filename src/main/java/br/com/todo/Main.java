package br.com.todo;

import br.com.todo.db.SQLiteConnection;
import br.com.todo.db.TaskDAO;
import br.com.todo.db.UserDAO;
import br.com.todo.view.UserView;

import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        SQLiteConnection.createTables();

        Connection conn = SQLiteConnection.connect();
        UserDAO usuarioProxy = new UserDAO(conn);
        TaskDAO taskProxy = new TaskDAO(conn);

        UserView usuarioView = new UserView(conn);
        usuarioView.menu();

        SQLiteConnection.close();
    }
}