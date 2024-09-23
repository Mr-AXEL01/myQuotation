package net.axel.presentations;


import net.axel.models.dto.LaborDto;
import net.axel.models.dto.MaterialDto;
import net.axel.models.entities.*;
import net.axel.models.enums.ComponentType;
import net.axel.models.enums.ProjectStatus;
import net.axel.repositories.implementations.ClientRepository;
import net.axel.services.implementations.ClientService;
import net.axel.services.implementations.ProjectService;
import net.axel.services.interfaces.IProjectService;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ProjectUi {
    private List<MaterialDto> materials ;
    private List<LaborDto> labors ;
    private final IProjectService projectService;
    private final ClientUi clientUi;
    private final Scanner scanner;
    private Client selectedClient;

    public ProjectUi(ProjectService projectService) throws SQLException {
        this.projectService = projectService;
        this.clientUi = new ClientUi(new ClientService(new ClientRepository()));
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\n=== Project Menu ===\n");
            System.out.println("1. Create a new project");
            System.out.println("2. View existing projects");
            System.out.println("3. Calculate the cost of a project");
            System.out.println("4. Exit");
            System.out.print("Enter your choice : ");

            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> addProject();
                case 2 -> displayExistingProjects();
                case 3 -> calculateProjectCost();
                case 4 -> System.out.println("Exiting...");
                default -> System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 4);
    }

    private void addProject() {
        int customerChoice = -1;
        System.out.println("\n=== Client Search ===");
        System.out.println("Would you like to search for an existing client or add a new one?");
        System.out.println("1. Search for an existing client");
        System.out.println("2. Add a new client");
        System.out.println("3. Cancel");
        System.out.print("Enter your choice: ");

        try {
            customerChoice = Integer.parseInt(scanner.nextLine());

            switch (customerChoice) {
                case 1 -> searchForExistingClient();
                case 2 -> addNewClient();
                case 3 -> System.out.println("Cancelled...");
                default -> System.out.println("Invalid choice. Returning to main menu.");
            }

            if (selectedClient != null) {
                System.out.print("Would you like to continue with this client? (y/n): ");
                String confirm = scanner.nextLine();

                if (confirm.equalsIgnoreCase("y")) {
                    startProjectCreation(selectedClient);
                } else {
                    System.out.println("Returning to menu.");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid number.");
        }
    }

    private void searchForExistingClient() {
        selectedClient = clientUi.findClientByName();
        if (selectedClient == null) {
            System.out.println("Client not found.");
        }
    }

    private void addNewClient() {
        selectedClient = clientUi.addClient();
        if (selectedClient == null) {
            System.out.println("Failed to add client.");
        }
    }

    private void startProjectCreation(Client selectedClient) {
        final UUID clientId = selectedClient.getId();

        System.out.println("\n--- Creation of a New Project ---");
        System.out.print("Enter the name of the project: ");
        String projectName = scanner.nextLine();

        System.out.print("Enter the area of the kitchen (in m2): ");
        Double projectArea = Double.parseDouble(scanner.nextLine());

    }

    private void displayExistingProjects() {
//         to do
    }

    private void calculateProjectCost() {
//         to do
    }


}
