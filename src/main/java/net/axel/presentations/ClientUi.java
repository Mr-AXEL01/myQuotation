package net.axel.presentations;

import net.axel.services.implementations.ClientService;
import net.axel.services.interfaces.IClientService;

import java.util.Scanner;

public class ClientUi {
    private final IClientService clientService;
    private final Scanner scanner;

    public ClientUi(ClientService clientService) {
        this.clientService = clientService;
        this.scanner = new Scanner(System.in);
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println("\nClient Management Menu:\n");
            System.out.println("1. Add Client");
            System.out.println("2. Get Client by Name");
            System.out.println("3. Get All Clients");
            System.out.println("4. Update Client");
            System.out.println("5. Delete Client");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addClient();
                    break;
                case 2:
                    getClientByName();
                    break;
                case 3:
                    getAllClients();
                    break;
                case 4:
                    updateClient();
                    break;
                case 5:
                    deleteClient();
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }

   
}
