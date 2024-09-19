package net.axel.helpers;

import net.axel.models.entities.Client;
import net.axel.utils.Validation;

import java.util.Scanner;

public class ClientHelper {

    private final Scanner scanner;

    public ClientHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String promptForName() {
        String name;
        do {
            System.out.print("Enter client name: ");
            name = scanner.nextLine();
            if (!Validation.isNotEmpty(name)) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            }
        } while (!Validation.isNotEmpty(name));
        return name;
    }

    public String promptForAddress() {
        String address;
        do {
            System.out.print("Enter address: ");
            address = scanner.nextLine();
            if (!Validation.isNotEmpty(address)) {
                System.out.println("Address cannot be empty. Please enter a valid address.");
            }
        } while (!Validation.isNotEmpty(address));
        return address;
    }

    public String promptForPhone() {
        String phone;
        do {
            System.out.print("Enter phone (06********): ");
            phone = scanner.nextLine();
            if (!Validation.isValidPhone(phone)) {
                System.out.println("Invalid phone number. Please enter a 10-digit phone number.");
            }
        } while (!Validation.isValidPhone(phone));
        return phone;
    }

    public Boolean promptForProfessionalStatus() {
        String isProfessionalInput;
        Boolean isProfessional = null;
        do {
            System.out.print("Is the client a professional (yes/no): ");
            isProfessionalInput = scanner.nextLine();
            if (!Validation.isValidBoolean(isProfessionalInput)) {
                System.out.println("Please answer with 'yes' or 'no'.");
            } else {
                isProfessional = isProfessionalInput.equalsIgnoreCase("yes");
            }
        } while (!Validation.isValidBoolean(isProfessionalInput));
        return isProfessional;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public void tableHeader() {
        System.out.println("╔════════════════════════════════════════════════════════════════════════════════════════════════════════════════╗");
        System.out.printf("║%-38s ║ %-6s ║ %-30s ║ %-10s ║ %-16s║%n",
                "ID", "Name", "Address", "Phone", "Professional");
        System.out.println("╠════════════════════════════════════════════════════════════════════════════════════════════════════════════════╣");
    }

    public void tableBody(Client client) {
        String professionalStatus = (client.getIsProfessional() != null)
                ? (client.getIsProfessional() ? "Professional" : "Normal client")
                : "Unknown";

        System.out.printf("║ %-38s║ %-6s ║ %-30s ║ %-10s ║ %-16s║%n",
                client.getId(),
                client.getName(),
                client.getAddress(),
                client.getPhone(),
                professionalStatus);
        System.out.println("╚════════════════════════════════════════════════════════════════════════════════════════════════════════════════╝");
    }

}
