package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Client;
import net.axel.repositories.interfaces.IClientRepository;

import java.sql.*;
import java.util.ArrayList;
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
                " VALUES(?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, client.getId());
            stmt.setString(2, client.getName());
            stmt.setString(3, client.getAddress());
            stmt.setString(4, client.getPhone());
            stmt.setBoolean(5, client.getIsProfessional());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to add client, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while adding client: " + e.getMessage());
        }
        return client;
    }

    @Override
    public Optional<Client> findClientByName(String name) {
        final String query = "SELECT * FROM " + tableName + " WHERE name = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            try (ResultSet rst = stmt.executeQuery()) {
                if (rst.next()) {
                    return Optional.of(mapToClient(rst));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error finding client by name: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Client> findAllClients() {
        final String query = "SELECT * FROM " + tableName + " WHERE deleted_at IS NULL";
        List<Client> clients = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); ResultSet rst = stmt.executeQuery(query)) {
            while (rst.next()) {
                clients.add(mapToClient(rst));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving all clients: " + e.getMessage());
        }
        return clients;
    }

    @Override
    public Client updateClient(String oldName, Client updatedClient) {
        final String query = "UPDATE " + tableName + " SET name = ?, address = ?, phone = ?, is_professional = ? WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, updatedClient.getName());
            stmt.setString(2, updatedClient.getAddress());
            stmt.setString(3, updatedClient.getPhone());
            stmt.setBoolean(4, updatedClient.getIsProfessional());
            stmt.setString(5, oldName);

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to update client, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating client: " + e.getMessage());
        }
        return updatedClient;
    }

    @Override
    public void deleteClient(String name) {
        final String query = "UPDATE " + tableName + " SET deleted_at = CURRENT_TIMESTAMP WHERE name = ? AND deleted_at IS NULL";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to delete client, no rows affected.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting client: " + e.getMessage());
        }
    }

    private Client mapToClient(ResultSet rst) throws SQLException {
        UUID clientId = UUID.fromString(rst.getString("id"));
        String clientName = rst.getString("name");
        String address = rst.getString("address");
        String phone = rst.getString("phone");
        Boolean isProfessional = rst.getBoolean("is_professional");

        return new Client(clientId, clientName, address, phone, isProfessional);
    }
}
