package net.axel.helpers;

import net.axel.models.entities.Client;
import net.axel.models.entities.Project;
import net.axel.presentations.ClientUi;
import net.axel.repositories.implementations.ClientRepository;
import net.axel.services.implementations.ClientService;
import net.axel.utils.Validation;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.UUID;

public class ProjectHelper {

    private final Scanner scanner;
    private final ClientUi clientUi;
    private Client selectedClient;

    public ProjectHelper(Scanner scanner) throws SQLException {
        this.clientUi = new ClientUi(new ClientService(new ClientRepository()));
        this.scanner = scanner;
    }

    public String promptForName() {
        String name;
        do {
            System.out.print("Enter project name: ");
            name = scanner.nextLine();
            if (!Validation.isNotEmpty(name)) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            }
        } while (!Validation.isNotEmpty(name));
        return name;
    }

    public UUID requestProjectId() {
        System.out.println("Enter the project ID : ");
        return UUID.fromString(scanner.nextLine());
    }

    public Client associateProjectWithClient() {
        int customerChoice = -1;
        do {
            System.out.println("\n=== Client Search ===");
            System.out.println("Would you like to search for an existing client or add a new one?");
            System.out.println("1. Search for an existing client");
            System.out.println("2. Add a new client");
            System.out.println("0. Cancel");
            System.out.print("Enter your choice: ");

            try {
                customerChoice = Integer.parseInt(scanner.nextLine());

                switch (customerChoice) {
                    case 1 -> searchForExistingClient();
                    case 2 -> addNewClient();
                    case 0 -> System.out.println("Cancelled...");
                    default -> System.out.println("Invalid choice. Returning to main menu.");
                }


                if (selectedClient != null) {
                    if (confirmAndProceedWithClient()) {
                        return selectedClient;
                    }
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        } while(customerChoice != 0);
        return null;
    }

    public void addNewClient() {
        selectedClient = clientUi.addClient();
        if (selectedClient == null) {
            System.out.println("Failed to add client.");
        }
    }

    public void searchForExistingClient() {
        selectedClient = clientUi.findClientByName();
    }

    public Boolean confirmAndProceedWithClient() {
        System.out.print("Would you like to continue with this client? (y/n): ");
        String confirm = scanner.nextLine();

        return confirm.equalsIgnoreCase("y");
    }

    public double requestVat() {
        System.out.println("Would you like to apply a VAT to the project? (y/n)");
        String vatConfirmation = scanner.nextLine();

        if (vatConfirmation.equalsIgnoreCase("y")) {
            System.out.println("Enter the VAT percentage (in decimal form, e.g., 0.2 for 20%):");
            return Double.parseDouble(scanner.nextLine());
        }
        return 0.0;
    }

    public double requestProfitMargin() {
        System.out.println("Would you like to apply a profit margin to the project? (y/n)");
        String marginConfirm = scanner.nextLine();

        if (marginConfirm.equalsIgnoreCase("y")) {
            System.out.println("Enter the profit margin percentage (in decimal form, e.g., 0.15 for 15%):");
            return Double.parseDouble(scanner.nextLine());
        }
        return 0.0;
    }

    public void tableHeader() {
        System.out.println("╔═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║ %-36s ║ %-20s ║ %-8s ║ %-10s ║ %-10s ║ %-16s ║ %-12s  ║%n",
                "Project ID", "Project Name", "Surface", "Cost", "Profit (%)", "Client Name", "Professional");
        System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
    }

    public void tableBody(Project project) {
        Client client = project.getClient();
        String professionalStatus = (client.getIsProfessional() != null)
                ? (client.getIsProfessional() ? "Professional " : "Normal client")
                : "Unknown";

        System.out.printf("║ %-36s ║ %-20s ║ %-8.2f ║ %-10.2f ║ %-10d ║ %-16s ║ %-12s ║%n",
                project.getId(),
                project.getName(),
                project.getSurface(),
                project.getTotalCost(),
                (int) (project.getProfitMargin() * 100),
                client.getName(),
                professionalStatus);
        System.out.println("╠═════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
    }

    public Boolean confirmAndProceedWithProject() {
        System.out.print("Would you like to continue with this project? (y/n): ");
        String confirm = scanner.nextLine();

        return confirm.equalsIgnoreCase("y");
    }

}
