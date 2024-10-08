package net.axel.presentations;

import net.axel.models.dto.LaborDto;
import net.axel.models.dto.MaterialDto;
import net.axel.models.dto.QuotationDto;
import net.axel.models.entities.Client;
import net.axel.models.entities.Project;
import net.axel.models.entities.Quotation;
import net.axel.repositories.implementations.LaborRepository;
import net.axel.repositories.implementations.MaterialRepository;
import net.axel.services.implementations.LaborService;
import net.axel.services.implementations.MaterialService;
import net.axel.services.implementations.QuotationService;
import net.axel.services.interfaces.IQuotationService;
import net.axel.utils.Validation;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class QuotationUI {
    private final ComponentUi componentUi;
    private final IQuotationService quotationService;
    private final Scanner scanner;

    public QuotationUI(QuotationService quotationService) throws SQLException {
        this.componentUi = new ComponentUi(new MaterialService(new MaterialRepository()), new LaborService(new LaborRepository()));
        this.quotationService = quotationService;
        this.scanner = new Scanner(System.in);
    }

    public Double displayProjectCostSummary(Client selectedClient, String projectName, double surface, double vat, double profitMargin, List<MaterialDto> materialDtos, List<LaborDto> laborDtos) {
        System.out.println("\n=== Project Cost Summary ===\n");
        System.out.println(" Project Name    : " + projectName);
        System.out.println(" Client Name     : " + selectedClient.getName());
        System.out.println(" Client Address  : " + selectedClient.getAddress());
        System.out.println(" Surface Area    : " + surface + " m²");

        System.out.println("\n=== Cost Details ===");

        double totalMaterialCost = componentUi.calculateTotalMaterialCost(vat, materialDtos);
        double totalLaborCost = componentUi.calculateTotalLaborCost(vat, laborDtos);

        double totalCost = totalMaterialCost + totalLaborCost;
        System.out.println("3- Total cost before margin: " + totalCost + "$");
        double finalTotalCost = calculateTotalCostWithMargin(totalCost, profitMargin);

        Boolean isProfessional= selectedClient.getIsProfessional();

        if(isProfessional) {
            System.out.println("\n === special discount === ");
            System.out.println("please enter discounts percentage (in decimal form, e.g., 0.2 for 20%) :");
            double discount = Double.parseDouble(scanner.nextLine());
            finalTotalCost -= (finalTotalCost * discount);
        }

        System.out.println("\n** Final Total Cost of the Project: " + finalTotalCost + "$ **");

        return finalTotalCost;
    }

    private double calculateTotalCostWithMargin(double totalCost, double profitMargin) {
        if (profitMargin > 0) {
            double profitCost = totalCost * profitMargin;
            System.out.println("4- Profit Margin (" + (int) (profitMargin * 100) + "%): " + profitCost + "$");
            totalCost += profitCost;
        }
        return totalCost;
    }

    public void saveQuotation(Project project) {
        System.out.println("\n=== Registration of the Quote ===");
        Quotation quotation;
        do {
            LocalDate issuedDate;
            do {
                System.out.println("Enter the date of issue of the quote (format: yyyy-mm-dd) : ");
                issuedDate = LocalDate.parse(scanner.nextLine());
                if (!Validation.isNotEmpty(String.valueOf(issuedDate)) || Validation.isValidDate(String.valueOf(issuedDate))) {
                    System.out.println("please enter a valid date");
                }
            } while (!Validation.isValidDate(String.valueOf(issuedDate)));

            LocalDate validityDate;
            do {
                System.out.println("Enter the validity date of the quote (format: yyyy-mm-dd) : ");
                validityDate = LocalDate.parse(scanner.nextLine());
                if (!Validation.isNotEmpty(String.valueOf(validityDate)) || Validation.isValidDate(String.valueOf(validityDate)));
            } while (!Validation.isValidDate(String.valueOf(validityDate)));

            Boolean accepted = confirmQuotationStatus();

            QuotationDto dto = new QuotationDto(
                    project.getTotalCost(),
                    issuedDate,
                    validityDate,
                    accepted,
                    project.getId()
            );
            quotation = quotationService.addQuotation(dto);
            if (quotation == null) {
                System.out.println("validity date must be after issuedDate.\n");
            }
        } while (quotation == null);

        System.out.println("\n \t\t\t\t** End of project **\n");
    }

    public Boolean confirmQuotationStatus() {
        System.out.println("Would you like to save the quote? (y/n) :");
        String confirm = scanner.nextLine();

        return confirm.equalsIgnoreCase("y");
    }


}
