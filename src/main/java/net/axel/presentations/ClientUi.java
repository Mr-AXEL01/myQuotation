package net.axel.presentations;

import net.axel.models.dto.ClientDto;
import net.axel.services.implementations.ClientService;
import net.axel.services.interfaces.IClientService;
import net.axel.utils.Validation;

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
                    findClientByName();
                    break;
                case 3:
                    findAllClients();
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

    private void addClient() {
        String name;
        do {
            System.out.print("Enter name: ");
            name = scanner.nextLine();
            if (!Validation.isNotEmpty(name)) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            }
        } while (!Validation.isNotEmpty(name));

        String address;
        do {
            System.out.print("Enter address: ");
            address = scanner.nextLine();
            if (!Validation.isNotEmpty(address)) {
                System.out.println("Address cannot be empty. Please enter a valid address.");
            }
        } while (!Validation.isNotEmpty(address));

        String phone;
        do {
            System.out.print("Enter phone (06********): ");
            phone = scanner.nextLine();
            if (!Validation.isValidPhone(phone)) {
                System.out.println("Invalid phone number. Please enter a 10-digit phone number.");
            }
        } while (!Validation.isValidPhone(phone));

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

        ClientDto clientDto = new ClientDto(
                name,
                address,
                phone,
                isProfessional
        );

        clientService.createClient(clientDto);

        System.out.println("Client added successfully.");
    }

    private void findClientByName() {
        System.out.print("Enter the client's name: ");
        String name = scanner.nextLine();

        Optional<ClientDto> client = clientService.getClientByName(name);
        if (client.isPresent()) {
            System.out.println("Client found: " + client.get());
        } else {
            System.out.println("Client not found.");
        }
    }

    private void findAllClients() {
        List<ClientDto> clients = clientService.getAllClients();
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
        } else {
            clients.forEach(client -> System.out.println(client));
        }
    }

    private void updateClient() {
        System.out.print("Enter the name of the client to update: ");
        String name = scanner.nextLine();

        Optional<ClientDto> clientOptional = clientService.getClientByName(name);
        if (clientOptional.isPresent()) {
            ClientDto existingClient = clientOptional.get();

            System.out.print("Enter new name (or press Enter to keep '" + existingClient.name() + "'): ");
            String newName = scanner.nextLine();
            newName = newName.isEmpty() ? existingClient.name() : newName;

            System.out.print("Enter new address (or press Enter to keep '" + existingClient.address() + "'): ");
            String newAddress = scanner.nextLine();
            newAddress = newAddress.isEmpty() ? existingClient.address() : newAddress;

            System.out.print("Enter new phone (or press Enter to keep '" + existingClient.phone() + "'): ");
            String newPhone = scanner.nextLine();
            newPhone = newPhone.isEmpty() ? existingClient.phone() : newPhone;

            Boolean isProfessional = existingClient.isProfessional();
            String isProfessionalInput;
            do {
                System.out.print("Is the client a professional (yes/no) (or press Enter to keep current): ");
                isProfessionalInput = scanner.nextLine();
                if (!isProfessionalInput.isEmpty()) {
                    if (!Validation.isValidBoolean(isProfessionalInput)) {
                        System.out.println("Please answer with 'yes' or 'no'.");
                    } else {
                        isProfessional = isProfessionalInput.equalsIgnoreCase("yes");
                    }
                }
            } while (!isProfessionalInput.isEmpty() && !Validation.isValidBoolean(isProfessionalInput));

            ClientDto updatedClient = new ClientDto(newName, newAddress, newPhone, isProfessional);
            clientService.updateClient(existingClient.name(), updatedClient);
            System.out.println("Client updated successfully.");
        } else {
            System.out.println("Client not found.");
        }
    }

    private void deleteClient() {
        System.out.print("Enter the name of the client to delete: ");
        String name = scanner.nextLine();

        Optional<ClientDto> client = clientService.getClientByName(name);
        if (client.isPresent()) {
            clientService.deleteClient(name);
            System.out.println("Client deleted successfully.");
        } else {
            System.out.println("Client not found.");
        }
    }
}
