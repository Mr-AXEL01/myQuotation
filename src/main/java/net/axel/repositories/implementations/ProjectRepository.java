package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Project;
import net.axel.repositories.interfaces.IProjectRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProjectRepository implements IProjectRepository {

    private final String tableName = "projects";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public ProjectRepository() throws SQLException {
    }

    @Override
    public Project addProject(Project project) {
        final String query = "INSERT INTO " + tableName + " (id, name, surface, project_status, client_id)" +
                " VALUES(?, ?, ?, ?::ProjectStatus, ?)";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, project.getId());
            stmt.setString(2, project.getName());
            stmt.setDouble(3, project.getSurface());
            stmt.setString(4, project.getProjectStatus().name());
            stmt.setObject(5, project.getClient().getId());

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
