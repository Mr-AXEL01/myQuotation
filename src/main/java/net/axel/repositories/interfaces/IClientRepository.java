package net.axel.repositories.interfaces;

import net.axel.models.entities.Client;

import java.util.List;
import java.util.Optional;

public interface IClientRepository {

    Client addClient(Client client);

    Optional<Client> findClientByName(String name);

    List<Client> findAllClients();

    Client updateClient(String oldName, Client updatedClient);

    void deleteClient(String name);
}
