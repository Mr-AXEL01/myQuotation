package net.axel.models.entities;

import java.time.LocalDate;
import java.util.UUID;

public class Quotation {
    private UUID QuotationId;
    private Double estimatedAmount;
    private LocalDate issuedDate;
    private LocalDate validityDate;
    private Boolean accepted;
    private Project project;

    public Quotation(UUID quotationId, Double estimatedAmount, LocalDate issuedDate, LocalDate validityDate, Boolean accepted, Project project) {
        QuotationId = quotationId;
        this.estimatedAmount = estimatedAmount;
        this.issuedDate = issuedDate;
        this.validityDate = validityDate;
        this.accepted = accepted;
        this.project = project;
    }

    public UUID getQuotationId() {
        return QuotationId;
    }

    public void setQuotationId(UUID quotationId) {
        QuotationId = quotationId;
    }

    public Double getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(Double estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public LocalDate getIssuedDate() {
        return issuedDate;
    }

    public void setIssuedDate(LocalDate issuedDate) {
        this.issuedDate = issuedDate;
    }

    public LocalDate getValidityDate() {
        return validityDate;
    }

    public void setValidityDate(LocalDate validityDate) {
        this.validityDate = validityDate;
    }

    public Boolean getAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
