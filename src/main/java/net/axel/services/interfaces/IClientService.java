package net.axel.services.interfaces;

import net.axel.models.dto.ClientDto;
import net.axel.models.entities.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClientService {

    Client addClient(ClientDto clientDto);

    Optional<Client> findClientByName(String name);

    Optional<Client> findClientById(UUID id);

    List<Client> findAllClients();

    Client updateClient(String oldName, ClientDto updatedClient);

    void deleteClient(String name);
}
