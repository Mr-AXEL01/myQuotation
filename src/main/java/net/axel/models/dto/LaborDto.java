package net.axel.models.dto;

import net.axel.models.enums.ComponentType;

import java.util.UUID;

public record LaborDto(
        String laborName,
        Double laborCost,
        Double duration,
        ComponentType laborType,
        Double laborEfficiencyFactor
) {
}
