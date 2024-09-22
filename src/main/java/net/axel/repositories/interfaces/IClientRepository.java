package net.axel.repositories.interfaces;

import net.axel.models.entities.Client;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IClientRepository {

    Client addClient(Client client);

    Optional<Client> findClientByName(String name);

    Optional<Client> findClientById(UUID id);

    List<Client> findAllClients();

    Client updateClient(String oldName, Client updatedClient);

    void deleteClient(String name);
}
