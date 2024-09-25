package net.axel.services.interfaces;

import net.axel.models.dto.QuotationDto;
import net.axel.models.entities.Quotation;

public interface IQuotationService {

    Quotation addQuotation(QuotationDto dto);
}
