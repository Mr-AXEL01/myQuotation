package net.axel;

import net.axel.config.DatabaseConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

//        try {
//            Connection connection = DatabaseConnection.getInstance().getConnection();
//            if (connection != null && !connection.isClosed()) {
//                System.out.println("Database connection successful!");
//            } else {
//                System.out.println("Failed to connect to the database.");
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

}