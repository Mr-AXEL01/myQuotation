package net.axel.presentations;


import net.axel.models.dto.LaborDto;
import net.axel.models.dto.MaterialDto;
import net.axel.models.dto.ProjectDto;
import net.axel.models.entities.Client;
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
    private List<Component> labors ;
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

        Double vat;
        Double profitMargin;
        ProjectStatus projectStatus = ProjectStatus.IN_PROGRESS;


        addMaterials(clientId);

        addLabors(clientId);

        System.out.println("\n=== Calcul du coût total ===\n");

        System.out.println("Would you like to apply a VAT to the project? (y/n)");
        String vatConfirmation = scanner.nextLine();

        if(vatConfirmation.equalsIgnoreCase("y")){
            System.out.println("Enter the VAT percentage (in decimal form, e.g., 0.2 for 20%) :");
            vat = Double.valueOf(scanner.nextLine());
        } else {
            vat = 0.0;
        }

        System.out.println("Would you like to apply a profit margin to the project? (y/n)");
        String marginConfirm = scanner.nextLine();

        if(marginConfirm.equalsIgnoreCase("y")) {
            System.out.println("Would you like to apply a profit margin to the project? (y/n)");
            profitMargin = Double.valueOf(scanner.nextLine());
        } else {
            profitMargin = 0.0;
        }

        addMaterials(clientId);


    }

    private void addMaterials(UUID clientId) {

        System.out.println("\n=== Add Material ===");

        System.out.print("Enter the name of the material: ");
        String materialName = scanner.nextLine();

        System.out.print("Enter the unit cost: ");
        Double materialCost = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter the quantity: ");
        Double materialQuantity = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter the transport cost: ");
        Double transportCost = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter the efficiency factor: (1.0 = standard < high quality)");
        Double materialEfficiencyFactor = Double.parseDouble(scanner.nextLine());

        ComponentType componentType = ComponentType.MATERIAL;

        MaterialDto dto = new MaterialDto(
                materialName,
                materialCost,
                materialQuantity,
                componentType,
                materialEfficiencyFactor,
                clientId,
                transportCost
        );

        materials.add(dto);

        System.out.println("material added successfully!");

        System.out.print("Would you like to add new material? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            addMaterials(clientId);
        }
    }

    private void addLabors() {
        
    }

    

    private void displayExistingProjects() {
//         to do
    }

    private void calculateProjectCost() {
//         to do
    }


}
