package net.axel.services.interfaces;

import net.axel.models.dto.ClientDto;

import java.util.List;
import java.util.Optional;

public interface IClientService {

    void addClient(ClientDto clientDto);

    Optional<ClientDto> findClientByName(String name);

    List<ClientDto> findAllClients();

    void updateClient(String oldName, ClientDto updatedClient);

    void deleteClient(String name);
}

}
