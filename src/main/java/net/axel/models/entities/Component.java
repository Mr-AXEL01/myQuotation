package net.axel.models.entities;

import net.axel.models.enums.ComponentType;

import java.util.UUID;

public abstract class Component {

    private UUID id;
    private String componentName;
    private Double unitCost;
    private Double quantityOrDuration;
    private Double vat;
    private ComponentType componentType;
    private Double efficiencyFactor;
    private Project project;

    public Component () {

    }

    public Component(UUID id, String componentName, Double unitCost, Double quantityOrDuration, Double vat, ComponentType componentType, Double efficiencyFactor, Project project) {
        this.id = id;
        this.componentName = componentName;
        this.unitCost = unitCost;
        this.quantityOrDuration = quantityOrDuration;
        this.vat = vat;
        this.componentType = componentType;
        this.efficiencyFactor = efficiencyFactor;
        this.project = project;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getComponentName() {
        return componentName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public Double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(Double unitCost) {
        this.unitCost = unitCost;
    }

    public Double getQuantityOrDuration() {
        return quantityOrDuration;
    }

    public void setQuantityOrDuration(Double quantityOrDuration) {
        this.quantityOrDuration = quantityOrDuration;
    }

    public Double getVat() {
        return vat;
    }

    public void setVat(Double vat) {
        this.vat = vat;
    }

    public ComponentType getComponentType() {
        return componentType;
    }

    public void setComponentType(ComponentType componentType) {
        this.componentType = componentType;
    }

    public Double getEfficiencyFactor() {
        return efficiencyFactor;
    }

    public void setEfficiencyFactor(Double efficiencyFactor) {
        this.efficiencyFactor = efficiencyFactor;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
