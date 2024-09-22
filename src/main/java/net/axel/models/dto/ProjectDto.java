package net.axel.models.dto;

import net.axel.models.enums.ProjectStatus;

import java.util.UUID;

public record ProjectDto(
        String name,
        Double surface,
        UUID clientId
) {
}
