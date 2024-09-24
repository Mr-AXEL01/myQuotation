package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Labor;
import net.axel.repositories.interfaces.IComponentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LaborRepository implements IComponentRepository<Labor> {

    final String tableName = "labors";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public LaborRepository() throws SQLException {
    }

    @Override
    public Labor save(Labor labor) {
        final String query = "INSERT INTO "+ tableName + " (id, name, unit_cost, quantity_or_duration, vat, component_type, efficiency_factor, project_id)" +
                " VALUES(?, ?, ?, ?, ?, ?::ComponentType, ?, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, labor.getId());
            stmt.setString(2, labor.getComponentName());
            stmt.setDouble(3, labor.getUnitCost());
            stmt.setDouble(4, labor.getQuantityOrDuration());
            stmt.setDouble(5, labor.getVat());
            stmt.setString(6, labor.getComponentType().name());
            stmt.setDouble(7, labor.getEfficiencyFactor());
            stmt.setObject(8, labor.getProject().getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to add labor, no rows affected.");
            }
        } catch(SQLException e) {
            throw new RuntimeException("Error adding new labor : " + e.getMessage());
        }
        return labor;
    }
}
