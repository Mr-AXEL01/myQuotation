package net.axel.repositories.interfaces;

import net.axel.models.entities.Project;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IProjectRepository {

    Project addProject(Project project);

    Optional<Project> findProjectById(UUID id);

    List<Project> findAllProjects();

    Project updateProject(UUID oldProjectId, Project project);
}
