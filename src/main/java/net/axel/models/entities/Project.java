package net.axel.models.entities;

import net.axel.models.enums.ProjectStatus;

import java.util.List;
import java.util.UUID;

public class Project {

    private UUID id;
    private String name;
    private Double surface;
    private Double profitMargin;
    private Double totalCost;
    private ProjectStatus projectStatus;
    private Client client;
//    private List<Quote> quotes;
    private List<Component> components;

    public Project() {
    }

    public Project(UUID id, String name, Double surface, Double profitMargin, Double totalCost, ProjectStatus projectStatus, Client client) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.projectStatus = projectStatus;
        this.client = client;
    }

    public Project(UUID id, String name, Double surface, Double profitMargin, Double totalCost, ProjectStatus projectStatus, Client client, List<Component> components) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.profitMargin = profitMargin;
        this.totalCost = totalCost;
        this.projectStatus = projectStatus;
        this.client = client;
        this.components = components;
    }

    public Project(UUID id, String name, Double surface, ProjectStatus projectStatus, Client clientId) {
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.projectStatus = projectStatus;
        this.client = clientId;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSurface() {
        return surface;
    }

    public void setSurface(Double surface) {
        this.surface = surface;
    }

    public Double getProfitMargin() {
        return profitMargin;
    }

    public void setProfitMargin(Double profitMargin) {
        this.profitMargin = profitMargin;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public ProjectStatus getProjectStatus() {
        return projectStatus;
    }

    public void setProjectStatus(ProjectStatus projectStatus) {
        this.projectStatus = projectStatus;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
}
