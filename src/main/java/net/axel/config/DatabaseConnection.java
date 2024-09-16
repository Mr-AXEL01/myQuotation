package net.axel.config;

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
        final String URL = "jdbc:postgresql://localhost:5432/ecomove";
        final String USER = "axel";
        final String PASSWORD = "6631";
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
