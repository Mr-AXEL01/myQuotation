package net.axel.presentations;

import net.axel.models.dto.LaborDto;
import net.axel.models.dto.MaterialDto;
import net.axel.models.entities.Labor;
import net.axel.models.entities.Material;
import net.axel.repositories.implementations.ClientRepository;
import net.axel.repositories.implementations.LaborRepository;
import net.axel.repositories.implementations.MaterialRepository;
import net.axel.repositories.implementations.ProjectRepository;
import net.axel.services.implementations.ClientService;
import net.axel.services.implementations.LaborService;
import net.axel.services.implementations.MaterialService;
import net.axel.services.implementations.ProjectService;
import net.axel.services.interfaces.IComponentService;

import java.sql.SQLException;
import java.util.Scanner;

public class Menu {

    private final Scanner scanner;

    public Menu() {
        this.scanner = new Scanner(System.in);
    }

    public void showMainMenu() throws SQLException {
        int choice;
        do {
            System.out.println("\n=== Main Menu ===\n");
            System.out.println("1. Client Management");
            System.out.println("2. Project Management");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    manageClients();
                    break;
                case 2:
                    manageProjects();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

    private void manageClients() throws SQLException {
        ClientService clientService = new ClientService(new ClientRepository());
        ClientUi clientUi = new ClientUi(clientService);
        clientUi.showMenu();
    }

    private void manageProjects() {
        try {
            ProjectService projectService = new ProjectService(new ProjectRepository(), new ClientService(new ClientRepository()));
            IComponentService<Material, MaterialDto> materialService = new MaterialService(new MaterialRepository());
            IComponentService<Labor, LaborDto> laborService = new LaborService(new LaborRepository());
            ProjectUi projectUi = new ProjectUi(projectService, materialService, laborService);
            projectUi.showMenu();
        } catch (SQLException e) {
            System.out.println("Error initializing Project Management: " + e.getMessage());
        }
    }
}
