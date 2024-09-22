package net.axel;

import net.axel.config.DatabaseConnection;
import net.axel.presentations.ClientUi;
import net.axel.presentations.ProjectUi;
import net.axel.repositories.implementations.ClientRepository;
import net.axel.repositories.implementations.ProjectRepository;
import net.axel.services.implementations.ClientService;
import net.axel.services.implementations.ProjectService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
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

         // Adjust if necessary
//        ClientUi clientUi = new ClientUi(new ClientService(new ClientRepository()));
//        clientUi.showMenu();

        ProjectUi projectUi = new ProjectUi(new ProjectService(new ProjectRepository(), new ClientService(new ClientRepository())));
        projectUi.showMenu();
    }

}