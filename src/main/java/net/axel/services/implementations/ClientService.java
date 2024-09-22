package net.axel.services.implementations;

import net.axel.models.dto.ClientDto;
import net.axel.models.entities.Client;
import net.axel.repositories.implementations.ClientRepository;
import net.axel.repositories.interfaces.IClientRepository;
import net.axel.services.interfaces.IClientService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientService implements IClientService {
    private final IClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public Client addClient(ClientDto clientDto) {
        if (clientExists(clientDto.name())) {
            throw new IllegalArgumentException("Client with name " + clientDto.name() + " already exists.");
        }
        Client client = new Client(
                UUID.randomUUID(),
                clientDto.name(),
                clientDto.address(),
                clientDto.phone(),
                clientDto.isProfessional()
        );
        return clientRepository.addClient(client);
    }

    @Override
    public Optional<Client> findClientByName(String name) {
        return clientRepository.findClientByName(name);
    }

    @Override
    public Client findClientById(UUID id) {
        return clientRepository.findClientById(id)
                .orElseThrow(() -> new RuntimeException("Error find client By ID : " + id));
    }

    @Override
    public List<Client> findAllClients() {
        return clientRepository.findAllClients();
    }

    @Override
    public Client updateClient(String oldName, ClientDto updatedClientDto) {
        Optional<Client> existingClientOptional = clientRepository.findClientByName(oldName);

        if (existingClientOptional.isEmpty()) {
            throw new IllegalArgumentException("Client with name " + oldName + " does not exist.");
        }

        Client existingClient = existingClientOptional.get();

        Client updatedClient = new Client(
                existingClient.getId(),
                updatedClientDto.name().isEmpty() ? existingClient.getName() : updatedClientDto.name(),
                updatedClientDto.address().isEmpty() ? existingClient.getAddress() : updatedClientDto.address(),
                updatedClientDto.phone().isEmpty() ? existingClient.getPhone() : updatedClientDto.phone(),
                updatedClientDto.isProfessional() != null ? updatedClientDto.isProfessional() : existingClient.getIsProfessional()
        );

        return clientRepository.updateClient(oldName, updatedClient);
    }

    @Override
    public void deleteClient(String name) {
        if (!clientExists(name)) {
            throw new IllegalArgumentException("Client with name " + name + " does not exist.");
        }
        clientRepository.deleteClient(name);
    }

    private boolean clientExists(String name) {
        return clientRepository.findClientByName(name).isPresent();
    }
}
