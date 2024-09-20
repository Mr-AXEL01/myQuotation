package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Project;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectRepository {

    private final String tableName = "projects";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public ProjectRepository() throws SQLException {
    }

    public Project addProject(Project project) {
        final String query = "INSERT INTO " + tableName + " (id, name, surface, profit_margin, total_cost, project_status, client_id)" +
                " VALUES(?, ?, ?, ?, ?, ?:ProjectStatus, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, project.getId());
            stmt.setString(2, project.getName());
            stmt.setDouble(3, project.getSurface());
            stmt.setDouble(4, project.getProfitMargin());
            stmt.setDouble(5, project.getTotalCost());
            stmt.setString(6, project.getProjectStatus().name());
            stmt.setObject(7, project.getClient().getId());

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected == 0) {
                throw new SQLException("Failed to add project, no rows affected.");
            }
        } catch(SQLException e) {
            throw new RuntimeException("Error while adding new project," + e.getMessage());
        }
        return project;
    }
    


}
