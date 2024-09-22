package net.axel.models.entities;

import net.axel.models.enums.ComponentType;

import java.util.UUID;

public class Material extends Component {
    private Double transportCost;

    public Material() {
    }

    public Material(UUID id, String componentName, Double unitCost, Double quantityOrDuration, Double vat, ComponentType componentType, Double efficiencyFactor, Project project, Double transportCost) {
        super(id, componentName, unitCost, quantityOrDuration, vat, componentType, efficiencyFactor, project);
        this.transportCost = transportCost;
    }

    public Double getTransportCost() {
        return transportCost;
    }

    public void setTransportCost(Double transportCost) {
        this.transportCost = transportCost;
    }
}
