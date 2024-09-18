package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Client;
import net.axel.repositories.interfaces.IClientRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ClientRepository implements IClientRepository {

    private final String tableName = "clients";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public ClientRepository() throws SQLException {
    }

    @Override
    public Client addClient(Client client) {
        final String query = "INSERT INTO " + tableName + " (id, name, address, phone, is_professional)" +
                " VALUES(?, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, client.getId());
            stmt.setString(2, client.getName());
            stmt.setString(3, client.getAddress());
            stmt.setString(4, client.getPhone());
            stmt.setBoolean(5, client.getIsProfessional());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to add client, no rows affected.");
            }
        } catch(SQLException e){
            throw new RuntimeException("Error while adding client to database");
        }
        return client;
    }

    @Override
    public Optional<Client> findClientByName(String name) {
        final String query = "SELECT * FROM " + tableName + " WHERE name = ?";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try(ResultSet rst = stmt.executeQuery()) {
                if(rst.next()) {
                    UUID clientId          = UUID.fromString(rst.getString("id"));
                    String clientName      = rst.getString("name");
                    String address         = rst.getString("address");
                    String phone           = rst.getString("phone");
                    Boolean isProfessional = rst.getBoolean("is_professional");
                    Client client = new Client(clientId, clientName, address, phone, isProfessional);
                    return Optional.of(client);
                }
            }
        } catch(SQLException e) {
            throw new RuntimeException("Error finding client by name in the database.", e);
        }
        return Optional.empty();
    }

}
