package net.axel.models.dto;

import net.axel.models.enums.ComponentType;

import java.util.UUID;

public record MaterialDto(
        String materialName,
        Double materialCost,
        Double quantity,
        ComponentType materialType,
        Double materialEfficiencyFactory,
        UUID clientId,
        Double transportType
) {
}
