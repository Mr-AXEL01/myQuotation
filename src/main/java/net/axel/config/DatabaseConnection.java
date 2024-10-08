package net.axel.config;

import net.axel.utils.Env;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static DatabaseConnection instance = null;
    private static Connection connection = null;

    private DatabaseConnection() throws SQLException {
        init();
    }

    public static DatabaseConnection getInstance() throws SQLException {
        if (instance == null || !instance.connection.isClosed()) {
            instance = new DatabaseConnection();
        }
        return instance;
    }

    public static void closeConnection() {
        if (instance != null) {
            try {
                instance.getConnection().close();
                instance = null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void init() throws SQLException {
        final String URL = Env.get("URL");
        final String USER = Env.get("USER");
        final String PASSWORD = Env.get("PASSWORD");
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new SQLException(e);
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
