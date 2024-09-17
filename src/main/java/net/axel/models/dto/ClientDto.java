package net.axel.models.dto;

public record ClientDto(
        String name,
        String address,
        String phone,
        Boolean isProfessional
) {
}
