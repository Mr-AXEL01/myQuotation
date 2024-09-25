package net.axel.repositories.implementations;

import net.axel.config.DatabaseConnection;
import net.axel.models.entities.Client;
import net.axel.models.entities.Project;
import net.axel.models.enums.ProjectStatus;
import net.axel.repositories.interfaces.IProjectRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class ProjectRepository implements IProjectRepository {

    private final String tableName = "projects";
    private final Connection connection = DatabaseConnection.getInstance().getConnection();

    public ProjectRepository() throws SQLException {
    }

    @Override
    public Project addProject(Project project) {
        final String query = "INSERT INTO " + tableName + " (id, name, surface, profit_margin, total_cost, project_status, client_id)" +
                " VALUES(?, ?, ?, ?, ?, ?::ProjectStatus, ?)";
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

    @Override
    public Optional<Project> findProjectById(UUID id) {
        final String query = "SELECT p.* , c.id , c.name AS client_name, c.address, c.phone, c.is_professional FROM "+ tableName + " p JOIN " +
                "clients c ON p.client_id = c.id WHERE p.id = ? AND p.deleted_at IS NULL";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setObject(1, id);
            try (ResultSet rst = stmt.executeQuery()) {
                if(rst.next()) {
                    return Optional.of(mapToProject(rst));
                }
            }
        }catch(SQLException e) {
            throw new RuntimeException("Error finding project by id: " + e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Project> findAllProjects() {
        final String query = "SELECT p.* , c.id , c.name AS client_name, c.address, c.phone, c.is_professional FROM "+ tableName + " p JOIN " +
                "clients c ON p.client_id = c.id WHERE p.deleted_at IS NULL";
        List<Project> projects = new ArrayList<>();
        try(Statement stmt = connection.createStatement()) {
            ResultSet rst = stmt.executeQuery(query);
            while(rst.next()){
                projects.add(mapToProject(rst));
            }
        } catch(SQLException e) {
            throw new RuntimeException("Error getting all projects : " + e.getMessage());
        }
        return projects;
    }

    @Override
    public Project updateProject(UUID oldProjectId, Project project) {
        final String query = "UPDATE "+ tableName + " SET (iname = ?, surface = ?, profit_margin = ?, total_cost = ?, project_status = ?," +
                " client_id = ?) WHERE id = oldProjectId";
        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, project.getName());
            stmt.setDouble(2, project.getSurface());
            stmt.setDouble(3, project.getProfitMargin());
            stmt.setDouble(4, project.getTotalCost());
            stmt.setString(5, project.getProjectStatus().name());
            stmt.setObject(6, project.getClient().getId());
            stmt.setObject(7, project.getId());

            stmt.executeUpdate();
        }catch (SQLException e) {
            throw new RuntimeException("Error updating project : " + e.getMessage());
        }
        return  project;
    }

    private Project mapToProject(ResultSet rst) throws SQLException {
        UUID projectId = UUID.fromString(rst.getString("id"));
        String projectName = rst.getString("name");
        Double surface = rst.getDouble("surface");
        Double profitMargin = rst.getDouble("profit_margin");
        Double totalCost = rst.getDouble("total_cost");
        ProjectStatus projectStatus = ProjectStatus.valueOf(rst.getString("project_status"));

        UUID clientId = UUID.fromString(rst.getString("client_id"));
        String clientName = rst.getString("name");
        String address = rst.getString("address");
        String phone = rst.getString("phone");
        Boolean isProfessional = rst.getBoolean("is_professional");

        Client client = new Client(clientId, clientName, address, phone, isProfessional);

        return new Project(projectId, projectName, surface, profitMargin, totalCost, projectStatus, client);
    }
}
