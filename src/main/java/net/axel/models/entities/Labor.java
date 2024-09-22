package net.axel.models.entities;

import net.axel.models.enums.ComponentType;

import java.util.UUID;

public class Labor extends Component{

    public Labor(UUID id, String componentName, Double unitCost, Double quantityOrDuration, Double vat, ComponentType componentType, Double efficiencyFactor, Project project) {
        super(id, componentName, unitCost, quantityOrDuration, vat, componentType, efficiencyFactor, project);
    }
}
