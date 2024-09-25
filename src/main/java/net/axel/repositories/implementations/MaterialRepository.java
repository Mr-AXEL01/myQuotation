package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Material;
import net.axel.repositories.interfaces.IComponentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MaterialRepository implements IComponentRepository<Material> {

    final String tableName = "materials";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public MaterialRepository() throws SQLException {
    }

    @Override
    public Material save(Material material) {
        final String query = "INSERT INTO "+ tableName + " (id, name, unit_cost, quantity_or_duration, vat, component_type, efficiency_factor, project_id, transport_cost)" +
                " VALUES(?, ?, ?, ?, ?, ?::ComponentType, ?, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, material.getId());
            stmt.setString(2, material.getComponentName());
            stmt.setDouble(3, material.getUnitCost());
            stmt.setDouble(4, material.getQuantityOrDuration());
            stmt.setDouble(5, material.getVat());
            stmt.setString(6, material.getComponentType().name());
            stmt.setDouble(7, material.getEfficiencyFactor());
            stmt.setObject(8, material.getProject().getId());
            stmt.setDouble(9, material.getTransportCost());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to add material, no rows affected.");
            }
        } catch(SQLException e) {
            throw new RuntimeException("Error adding new material : " + e.getMessage());
        }
        return material;
    }

}
