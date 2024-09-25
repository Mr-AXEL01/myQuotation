package net.axel.services.implementations;

import net.axel.models.dto.ProjectDto;
import net.axel.models.entities.Client;
import net.axel.models.entities.Project;
import net.axel.repositories.implementations.ProjectRepository;
import net.axel.repositories.interfaces.IProjectRepository;
import net.axel.services.interfaces.IClientService;
import net.axel.services.interfaces.IProjectService;

import java.util.List;
import java.util.UUID;

public class ProjectService implements IProjectService {
    private final IProjectRepository projectRepository;
    private final IClientService clientService;

    public ProjectService(ProjectRepository projectRepository, ClientService clientService) {
        this.projectRepository = projectRepository;
        this.clientService = clientService;
    }

    @Override
    public Project addProject(ProjectDto dto) {
        Client client = clientService.findClientById(dto.clientId());

        Project project = new Project(
                UUID.randomUUID(),
                dto.name(),
                dto.surface(),
                dto.profitMargin(),
                dto.finalTotalCost(),
                dto.projectStatus(),
                client
        );

        return projectRepository.addProject(project);
    }

    @Override
    public Project findProjectById(UUID id) {
        return projectRepository.findProjectById(id)
                .orElseThrow(() -> new RuntimeException("Error find project By ID : " + id));
    }

    @Override
    public List<Project> findAllProjects() {
        return projectRepository.findAllProjects();
    }

}
