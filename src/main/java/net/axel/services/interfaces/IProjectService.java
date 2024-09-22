package net.axel.services.interfaces;

import net.axel.models.dto.ProjectDto;
import net.axel.models.entities.Project;

public interface IProjectService {
    Project addProject(ProjectDto dto);
}
