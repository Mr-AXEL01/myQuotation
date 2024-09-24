package net.axel.presentations;

import net.axel.models.dto.LaborDto;
import net.axel.models.dto.MaterialDto;
import net.axel.models.entities.Labor;
import net.axel.models.entities.Material;
import net.axel.models.enums.ComponentType;
import net.axel.services.interfaces.IComponentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComponentUi {

    private final IComponentService<Material, MaterialDto> materielService;
    private final IComponentService<Labor, LaborDto> laborService;
    private final List<MaterialDto> materials;
    private final List<LaborDto> labors ;
    private final Scanner scanner;

    public ComponentUi(IComponentService<Material, MaterialDto> materielService, IComponentService<Labor, LaborDto> laborService) {
        this.materielService = materielService;
        this.laborService = laborService;
        this.materials = new ArrayList<>();
        this.labors = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public void addMaterials() {
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

    public void addLabors() {
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

    public double calculateTotalMaterialCost(double vat) {
        System.out.println("\n=== Material Costs ===");

        double totalMaterialCost = materielService.calculateTotalCost(materials);

        System.out.println("**Total Material Cost before VAT: " + totalMaterialCost + " $");

        if (vat > 0) {
            totalMaterialCost = materielService.addVat(totalMaterialCost, vat);
            System.out.println("**Total Material Cost with VAT (" + (int)(vat * 100) + "%): " + totalMaterialCost + " $");
        }
        return totalMaterialCost;
    }

    public double calculateTotalLaborCost(double vat) {
        System.out.println("\n=== Labor Costs ===");

        double totalLaborCost = laborService.calculateTotalCost(labors);

        System.out.println("Total Labor Cost before VAT: " + totalLaborCost + " $");

        if (vat > 0) {
            totalLaborCost = laborService.addVat(totalLaborCost ,vat);
            System.out.println("Total Labor Cost with VAT (" + (int)(vat * 100) + "%): " + totalLaborCost + " $");
        }
        return totalLaborCost;
    }
}
