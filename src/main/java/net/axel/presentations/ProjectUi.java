package net.axel.presentations;


import net.axel.helpers.ProjectHelper;
import net.axel.models.dto.LaborDto;
import net.axel.models.dto.MaterialDto;
import net.axel.models.dto.ProjectDto;
import net.axel.models.entities.Client;
import net.axel.models.entities.Labor;
import net.axel.models.entities.Material;
import net.axel.models.entities.Project;
import net.axel.models.enums.ComponentType;

import net.axel.models.enums.ProjectStatus;
import net.axel.repositories.implementations.*;
import net.axel.services.implementations.*;
import net.axel.services.interfaces.IComponentService;
import net.axel.services.interfaces.IProjectService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class ProjectUi {
    private  List<MaterialDto> materials ;
    private  List<LaborDto> labors ;
    private final IProjectService projectService;
    private final IComponentService<Material, MaterialDto> materielService;
    private final IComponentService<Labor, LaborDto> laborService;
    private final ProjectHelper projectHelper;
    private final QuotationUI quotationUi;
    private final ComponentUi componentUi;
    private final Scanner scanner;

    public ProjectUi(ProjectService projectService, IComponentService<Material, MaterialDto> materielService, IComponentService<Labor, LaborDto> laborService) throws SQLException {
        this.projectService = projectService;
        this.projectHelper = new ProjectHelper(new Scanner(System.in));
        this.materielService = materielService;
        this.laborService = laborService;
        this.quotationUi = new QuotationUI(new QuotationService(new QuotationRepository(),new ProjectService(new ProjectRepository(), new ClientService(new ClientRepository()))));
        this.componentUi = new ComponentUi(new MaterialService(new MaterialRepository()), new LaborService(new LaborRepository()));
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
        Client selectedClient = projectHelper.associateProjectWithClient();
        if (selectedClient != null) {
            System.out.println("\n=== Creation of a New Project ===");
            String projectName = projectHelper.promptForName();

            System.out.print("Enter the area of the kitchen (in m2): ");
            double surface = Double.parseDouble(scanner.nextLine());

            materials = componentUi.addMaterials();
            labors = componentUi.addLabors();

            System.out.println("\n \t\t\t--- Calculation of the total cost ---\n");

            double vat = projectHelper.requestVat();
            double profitMargin = projectHelper.requestProfitMargin();

            System.out.println("\nCalculation of the current cost...\n");

            double finalTotalCost = quotationUi.displayProjectCostSummary(selectedClient, projectName, surface, vat, profitMargin, materials, labors);

            ProjectDto dto = new ProjectDto(
                    projectName,
                    surface,
                    profitMargin,
                    finalTotalCost,
                    ProjectStatus.IN_PROGRESS,
                    selectedClient.getId()
            );

            saveAll(vat, dto);
        }
    }

    private void saveAll(Double vat, ProjectDto dto) {
        Project project = projectService.addProject(dto);

        materielService.save(materials, vat, project);
        laborService.save(labors, vat, project);

        quotationUi.saveQuotation(project);
    }

    private void displayExistingProjects() {
        System.out.println("\n=== Displaying existing projects===\n");

        List<Project> projects = projectService.findAllProjects();
        if(projects.isEmpty()){
            System.out.println("No project found for the moments.");
            return;
        }
        projectHelper.tableHeader();

        projects.forEach(projectHelper::tableBody);
    }

    private void calculateProjectCost() {
        System.out.println("\n=== Calculating cost of an existing Project ===");

        UUID projectId = projectHelper.requestProjectId();

        Project existedProject = projectService.findProjectById(projectId);
        if(existedProject != null) {
            projectHelper.tableHeader();
            projectHelper.tableBody(existedProject);
            if(projectHelper.confirmAndProceedWithProject()) {

                Client selectedClient = existedProject.getClient();
                String projectName = existedProject.getName();
                double surface = existedProject.getSurface();
                double profitMargin = existedProject.getProfitMargin();
                ProjectStatus projectStatus = existedProject.getProjectStatus();

                if(!projectService.checkProjectStatus(projectStatus)) {
                    System.out.println("Project already completed.");
                    return;
                }

                materials = componentUi.addMaterials();
                labors = componentUi.addLabors();

                System.out.println("\n \t\t\t--- Calculation of the total cost ---\n");

                double vat = projectHelper.requestVat();

                System.out.println("\nCalculation of the current cost...\n");

                double finalTotalCost = quotationUi.displayProjectCostSummary(selectedClient, projectName, surface, vat, profitMargin, materials, labors);

                ProjectDto dto = new ProjectDto(
                        projectName,
                        surface,
                        profitMargin,
                        finalTotalCost,
                        ProjectStatus.IN_PROGRESS,
                        selectedClient.getId()
                );

                Project project = projectService.updateProject(existedProject, dto);

                materielService.save(materials, vat, project);
                laborService.save(labors, vat, project);

                quotationUi.saveQuotation(project);

                projectHelper.tableHeader();
                projectHelper.tableBody(project);
            }
        } else {
            System.out.println("Error getting project with ID : "+ projectId);
        }

    }


}
