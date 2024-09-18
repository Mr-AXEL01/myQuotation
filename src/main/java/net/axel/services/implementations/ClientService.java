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
    public void addClient(ClientDto clientDto) {
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
        clientRepository.addClient(client);
    }

    private boolean clientExists(String name) {
        return clientRepository.findByName(name).isPresent();
    }
}
