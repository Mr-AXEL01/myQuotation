package net.axel.models.dto;

import java.time.LocalDate;
import java.util.UUID;

public record QuotationDto(
        Double estimatedAmount,
        LocalDate issuedDate,
        LocalDate validityDate,
        Boolean accepted,
        UUID projectId
) {
}
