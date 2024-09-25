package net.axel.services.implementations;

import net.axel.models.dto.QuotationDto;
import net.axel.models.entities.Project;
import net.axel.models.entities.Quotation;
import net.axel.repositories.interfaces.IQuotationRepository;
import net.axel.services.interfaces.IProjectService;
import net.axel.services.interfaces.IQuotationService;

import java.util.UUID;

public class QuotationService implements IQuotationService {
    private final IQuotationRepository quotationRepository;
    private final IProjectService projectService;

    public QuotationService(IQuotationRepository quotationRepository, ProjectService projectService) {
        this.quotationRepository = quotationRepository;
        this.projectService = projectService;
    }


    public Quotation addQuotation(QuotationDto dto){
        Project project = projectService.findProjectById(dto.projectId());
        Quotation quotation = new Quotation(
                UUID.randomUUID(),
                dto.estimatedAmount(),
                dto.issuedDate(),
                dto.validityDate(),
                dto.accepted(),
                project
        );
        return quotationRepository.addQuotation(quotation);
    }
}
