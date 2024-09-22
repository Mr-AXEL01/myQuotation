package net.axel.presentations;

import net.axel.helpers.ClientHelper;
import net.axel.models.dto.ClientDto;
import net.axel.models.entities.Client;
import net.axel.services.implementations.ClientService;
import net.axel.services.interfaces.IClientService;
import net.axel.utils.Validation;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ClientUi {
    private final IClientService clientService;
    private final ClientHelper clientHelper;

    public ClientUi(ClientService clientService) {
        this.clientService = clientService;
        this.clientHelper = new ClientHelper(new Scanner(System.in));
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
            choice = Integer.parseInt(clientHelper.getScanner().nextLine());

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

    public Client addClient() {
        String name = clientHelper.promptForName();
        String address = clientHelper.promptForAddress();
        String phone = clientHelper.promptForPhone();
        Boolean isProfessional = clientHelper.promptForProfessionalStatus();

        ClientDto clientDto = new ClientDto(name, address, phone, isProfessional);
        Client addedClient = clientService.addClient(clientDto);

        System.out.println("\nClient added successfully , Here are the details:");
        clientHelper.tableHeader();
        clientHelper.tableBody(addedClient);

        return addedClient;
    }

    public Client findClientByName() {
        String name = clientHelper.promptForName();

        Optional<Client> client = clientService.findClientByName(name);
        if (client.isPresent()) {
            System.out.println("\nHere are the details:");
            clientHelper.tableHeader();
            clientHelper.tableBody(client.get());
        } else {
            System.out.println("Client not found.");
        }
        return client.get();
    }

    private void findAllClients() {
        List<Client> clients = clientService.findAllClients();
        if (clients.isEmpty()) {
            System.out.println("No clients found.");
        } else {
            clientHelper.tableHeader();
            for (Client client : clients) {
                clientHelper.tableBody(client);
            }
        }
    }

    private void updateClient() {
        String name = clientHelper.promptForName();

        Optional<Client> clientOptional = clientService.findClientByName(name);
        if (clientOptional.isPresent()) {
            Client existingClient = clientOptional.get();

            System.out.println("\nHere are the details:");
            clientHelper.tableHeader();
            clientHelper.tableBody(existingClient);

            System.out.print("Enter new name (or press Enter to keep '" + existingClient.getName() + "'): ");
            String newName = clientHelper.getScanner().nextLine();
            newName = newName.isEmpty() ? existingClient.getName() : newName;

            System.out.print("Enter new address (or press Enter to keep '" + existingClient.getAddress() + "'): ");
            String newAddress = clientHelper.getScanner().nextLine();
            newAddress = newAddress.isEmpty() ? existingClient.getAddress() : newAddress;

            System.out.print("Enter new phone (or press Enter to keep '" + existingClient.getPhone() + "'): ");
            String newPhone = clientHelper.getScanner().nextLine();
            newPhone = newPhone.isEmpty() ? existingClient.getPhone() : newPhone;

            Boolean isProfessional = existingClient.getIsProfessional();
            String isProfessionalInput;
            do {
                System.out.print("Is the client a professional (yes/no) (or press Enter to keep current): ");
                isProfessionalInput = clientHelper.getScanner().nextLine();
                if (!isProfessionalInput.isEmpty()) {
                    if (!Validation.isValidBoolean(isProfessionalInput)) {
                        System.out.println("Please answer with 'yes' or 'no'.");
                    } else {
                        isProfessional = isProfessionalInput.equalsIgnoreCase("yes");
                    }
                }
            } while (!isProfessionalInput.isEmpty() && !Validation.isValidBoolean(isProfessionalInput));

            ClientDto updatedClient = new ClientDto(newName, newAddress, newPhone, isProfessional);
            Client newClient = clientService.updateClient(existingClient.getName(), updatedClient);
            System.out.println("\nClient updated successfully, here are the details:");
            clientHelper.tableHeader();
            clientHelper.tableBody(newClient);
        } else {
            System.out.println("Client not found.");
        }
    }

    private void deleteClient() {
        String name = clientHelper.promptForName();

        try {
            clientService.deleteClient(name);
            System.out.println("Client deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

}
