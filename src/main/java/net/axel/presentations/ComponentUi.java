package net.axel.presentations;

import net.axel.models.dto.LaborDto;
import net.axel.models.dto.MaterialDto;
import net.axel.models.entities.Labor;
import net.axel.models.entities.Material;
import net.axel.models.enums.ComponentType;
import net.axel.services.interfaces.IComponentService;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

public class ComponentUi {

    private final IComponentService<Material, MaterialDto> materielService;
    private final IComponentService<Labor, LaborDto> laborService;
    private final List<MaterialDto> materials;
    private final List<LaborDto> labors;
    private final Scanner scanner;

    public ComponentUi(IComponentService<Material, MaterialDto> materielService, IComponentService<Labor, LaborDto> laborService) {
        this.materielService = materielService;
        this.laborService = laborService;
        this.materials = new ArrayList<>();
        this.labors = new ArrayList<>();
        this.scanner = new Scanner(System.in);
    }

    public List<MaterialDto> addMaterials() {
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
        return materials;
    }

    public List<LaborDto> addLabors() {
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
        return labors;
    }

    public double calculateTotalMaterialCost(double vat, List<MaterialDto> materials) {
        System.out.println("\n1- Material Costs");

        materials
                .forEach(material -> {
                    System.out.print("\t\t- " + material.materialName()+ ": ");
                    System.out.print((material.materialCost() * material.quantity() * material.materialEfficiencyFactory()) + material.transportCost());
                    System.out.println(" (quantity: "+ material.quantity()+", unit cost: "+ material.materialCost()+ "$, quality: "+ material.materialEfficiencyFactory()+ ", transport: "+ material.transportCost()+"$).");
                });

        double totalMaterialCost = materielService.calculateTotalCost(materials);

        System.out.println("**Total Material Cost before VAT: " + totalMaterialCost + " $");

        if (vat > 0) {
            totalMaterialCost = materielService.addVat(totalMaterialCost, vat);
            System.out.println("**Total Material Cost with VAT (" + (int) (vat * 100) + "%): " + totalMaterialCost + " $");
        }
        return totalMaterialCost;
    }

    public double calculateTotalLaborCost(double vat, List<LaborDto> labors) {
        System.out.println("\n2- Labor Costs");

        labors
                .forEach(labor -> {
                    System.out.print("\t\t- "+ labor.laborName()+ ": ");
                    System.out.print(labor.laborCost() * labor.duration() * labor.laborEfficiencyFactor());
                    System.out.println(" (hourly rate: "+ labor.laborCost() +"$, hours worked: "+ labor.duration() +"productivity: "+ labor.laborEfficiencyFactor());
                });

        double totalLaborCost = laborService.calculateTotalCost(labors);

        System.out.println("Total Labor Cost before VAT: " + totalLaborCost + " $");

        if (vat > 0) {
            totalLaborCost = laborService.addVat(totalLaborCost, vat);
            System.out.println("Total Labor Cost with VAT (" + (int) (vat * 100) + "%): " + totalLaborCost + " $");
        }
        return totalLaborCost;
    }
}
