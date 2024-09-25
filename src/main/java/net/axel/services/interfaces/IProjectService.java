package net.axel.services.interfaces;

import net.axel.models.dto.ProjectDto;
import net.axel.models.entities.Project;

import java.util.List;
import java.util.UUID;

public interface IProjectService {

    Project addProject(ProjectDto dto);

    Project findProjectById(UUID id);

    List<Project> findAllProjects();
}
