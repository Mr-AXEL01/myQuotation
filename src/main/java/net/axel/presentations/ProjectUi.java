package net.axel.presentations;


import net.axel.models.dto.LaborDto;
import net.axel.models.dto.MaterialDto;
import net.axel.models.dto.ProjectDto;
import net.axel.models.entities.Client;
import net.axel.models.entities.Labor;
import net.axel.models.entities.Material;
import net.axel.models.entities.Project;
import net.axel.models.enums.ComponentType;

import net.axel.models.enums.ProjectStatus;
import net.axel.repositories.implementations.ClientRepository;
import net.axel.services.implementations.ClientService;
import net.axel.services.implementations.ProjectService;
import net.axel.services.interfaces.IComponentService;
import net.axel.services.interfaces.IProjectService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ProjectUi {
    private final List<MaterialDto> materials ;
    private final List<LaborDto> labors ;
    private final IProjectService projectService;
    private final IComponentService<Material, MaterialDto> materielService;
    private final IComponentService<Labor, LaborDto> laborService;
    private final ClientUi clientUi;
    private final Scanner scanner;
    private Client selectedClient;

    public ProjectUi(ProjectService projectService, IComponentService<Material, MaterialDto> materielService, IComponentService<Labor, LaborDto> laborService) throws SQLException {
        this.projectService = projectService;
        this.materielService = materielService;
        this.laborService = laborService;
        this.clientUi = new ClientUi(new ClientService(new ClientRepository()));
        this.materials = new ArrayList<>();
        this.labors = new ArrayList<>();
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
            System.out.print("Enter your choice: ");

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
                confirmAndProceedWithClient();
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

    private void confirmAndProceedWithClient() {
        System.out.print("Would you like to continue with this client? (y/n): ");
        String confirm = scanner.nextLine();

        if (confirm.equalsIgnoreCase("y")) {
            initializeProjectDetails(selectedClient);
        } else {
            System.out.println("Returning to menu.");
        }
    }

    private void initializeProjectDetails(Client selectedClient) {
        System.out.println("\n=== Creation of a New Project ===");
        System.out.print("Enter the name of the project: ");
        String projectName = scanner.nextLine();

        System.out.print("Enter the area of the kitchen (in m2): ");
        double surface = Double.parseDouble(scanner.nextLine());

        addMaterials();
        addLabors();

        double vat = requestVat();
        double profitMargin = requestProfitMargin();

        double totalMaterialCost = calculateTotalMaterialCost(vat);
        double totalLaborCost = calculateTotalLaborCost(vat);
        double totalCost = totalMaterialCost + totalLaborCost;

        displayProjectCostSummary(selectedClient, projectName, surface, vat, profitMargin);

        ProjectDto dto = new ProjectDto(
                projectName,
                surface,
                profitMargin,
                totalCost,
                ProjectStatus.IN_PROGRESS,
                selectedClient.getId()
        );

        saveAll(vat, dto);
    }

    private void saveAll(Double vat, ProjectDto dto) {
        Project project = projectService.addProject(dto);

        materielService.save(materials, vat, project);
        laborService.save(labors, vat, project);
    }

    private double requestVat() {
        System.out.println("Would you like to apply a VAT to the project? (y/n)");
        String vatConfirmation = scanner.nextLine();

        if (vatConfirmation.equalsIgnoreCase("y")) {
            System.out.println("Enter the VAT percentage (in decimal form, e.g., 0.2 for 20%):");
            return Double.parseDouble(scanner.nextLine());
        }
        return 0.0;
    }

    private double requestProfitMargin() {
        System.out.println("Would you like to apply a profit margin to the project? (y/n)");
        String marginConfirm = scanner.nextLine();

        if (marginConfirm.equalsIgnoreCase("y")) {
            System.out.println("Enter the profit margin percentage (in decimal form, e.g., 0.15 for 15%):");
            return Double.parseDouble(scanner.nextLine());
        }
        return 0.0;
    }

    private void displayProjectCostSummary(Client selectedClient, String projectName, double surface, double vat, double profitMargin) {
        System.out.println("\n=== Project Cost Summary ===\n");
        System.out.println("Project Name    : " + projectName);
        System.out.println("Client Name     : " + selectedClient.getName());
        System.out.println("Client Address  : " + selectedClient.getAddress());
        System.out.println("Surface Area    : " + surface + " m²");

        double totalMaterialCost = calculateTotalMaterialCost(vat);
        double totalLaborCost = calculateTotalLaborCost(vat);

        double totalCost = totalMaterialCost + totalLaborCost;
        double finalTotalCost = calculateFinalCostWithMargin(totalCost, profitMargin);

        System.out.println("\n** Final Total Cost of the Project: " + finalTotalCost + " $ **");
    }

    private double calculateTotalMaterialCost(double vat) {
        System.out.println("\n=== Material Costs ===");

        double totalMaterialCost = materials.stream()
                .mapToDouble(material -> (material.materialCost() * material.quantity() * material.materialEfficiencyFactory()) + material.transportCost())
                .sum();

        System.out.println("**Total Material Cost before VAT: " + totalMaterialCost + " $");

        if (vat > 0) {
            double totalMaterialWithVat = totalMaterialCost + (totalMaterialCost * vat);
            System.out.println("**Total Material Cost with VAT (" + (int)(vat * 100) + "%): " + totalMaterialWithVat + " $");
            return totalMaterialWithVat;
        }
        return totalMaterialCost;
    }

    private double calculateTotalLaborCost(double vat) {
        System.out.println("\n=== Labor Costs ===");

        double totalLaborCost = labors.stream()
                .mapToDouble(labor -> labor.laborCost() * labor.duration() * labor.laborEfficiencyFactor())
                .sum();

        System.out.println("Total Labor Cost before VAT: " + totalLaborCost + " $");

        if (vat > 0) {
            double totalLaborWithVat = totalLaborCost + (totalLaborCost * vat);
            System.out.println("Total Labor Cost with VAT (" + (vat * 100) + "%): " + totalLaborWithVat + " $");
            return totalLaborWithVat;
        }
        return totalLaborCost;
    }

    private double calculateFinalCostWithMargin(double totalCost, double profitMargin) {
        if (profitMargin > 0) {
            double profitCost = totalCost * profitMargin;
            System.out.println("Profit Margin (" + (profitMargin * 100) + "%): " + profitCost + " $");
            return totalCost + profitCost;
        }
        return totalCost;
    }

    private void addMaterials() {
        System.out.println("\n=== Add Material ===");

        System.out.print("Enter the name of the material: ");
        String materialName = scanner.nextLine();

        System.out.print("Enter the unit cost: ");
        Double materialCost = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter the quantity: ");
        Double materialQuantity = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter the transport cost: ");
        Double transportCost = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter the efficiency factor (1.0 = standard, < 1.0 for high quality): ");
        Double materialEfficiencyFactor = Double.parseDouble(scanner.nextLine());

        MaterialDto dto = new MaterialDto(
                materialName,
                materialCost,
                materialQuantity,
                ComponentType.MATERIAL,
                materialEfficiencyFactor,
                transportCost
        );

        materials.add(dto);
        System.out.println("Material added successfully!");

        System.out.print("Would you like to add another material? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            addMaterials();
        }
    }

    private void addLabors() {
        System.out.println("\n=== Add Labor ===");

        System.out.print("Enter the name of the labor: ");
        String laborName = scanner.nextLine();

        System.out.print("Enter the hourly rate: ");
        Double laborCost = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter the number of hours: ");
        Double laborDuration = Double.parseDouble(scanner.nextLine());

        System.out.print("Enter the efficiency factor (1.0 = standard, < 1.0 for highly skilled): ");
        Double laborEfficiencyFactor = Double.parseDouble(scanner.nextLine());

        LaborDto dto = new LaborDto(
                laborName,
                laborCost,
                laborDuration,
                ComponentType.LABOR,
                laborEfficiencyFactor
        );

        labors.add(dto);
        System.out.println("Labor added successfully!");

        System.out.print("Would you like to add another labor? (y/n): ");
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            addLabors();
        }
    }

    private void displayExistingProjects() {
        System.out.println("Displaying existing projects...");
        // To be implemented
    }

    private void calculateProjectCost() {
        System.out.println("Calculating project cost...");
        // To be implemented
    }


}
