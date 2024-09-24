package net.axel.services.implementations;

import net.axel.models.dto.LaborDto;
import net.axel.models.entities.Labor;
import net.axel.models.entities.Material;
import net.axel.models.entities.Project;
import net.axel.repositories.interfaces.IComponentRepository;
import net.axel.services.interfaces.IComponentService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class LaborService implements IComponentService<Labor, LaborDto> {
    private final IComponentRepository<Labor> laborRepository;

    public LaborService(IComponentRepository<Labor> laborRepository) {
        this.laborRepository = laborRepository;
    }


    @Override
    public List<Labor> save(List<LaborDto> labors, Double vat, Project project) {
        return labors.stream()
                .map(laborDto -> {
                    Labor labor = new Labor(
                            UUID.randomUUID(),
                            laborDto.laborName(),
                            laborDto.laborCost(),
                            laborDto.duration(),
                            vat,
                            laborDto.laborType(),
                            laborDto.laborEfficiencyFactor(),
                            project
                    );
                    return laborRepository.save(labor);
                })
                .collect(Collectors.toList());
    }
}
