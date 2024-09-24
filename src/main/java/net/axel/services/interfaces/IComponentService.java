package net.axel.services.interfaces;

import net.axel.models.entities.Project;

import java.util.List;

public interface IComponentService<Entity, Dto> {
    List<Entity> save(List<Dto> components, Double vat, Project project);
    Double calculateTotalCost(List<Dto> dto);
    Double addVat(Double totalCost, Double vat);
}
