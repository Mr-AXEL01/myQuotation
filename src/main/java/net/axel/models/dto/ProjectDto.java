package net.axel.models.dto;

import net.axel.models.enums.ProjectStatus;

import java.util.UUID;

public record ProjectDto(
        String name,
        Double surface,
        Double profitMargin,
        Double finalTotalCost,
        ProjectStatus projectStatus,
        UUID clientId
) {
}
