package net.axel.services.implementations;

import net.axel.models.dto.MaterialDto;
import net.axel.models.entities.Material;
import net.axel.models.entities.Project;
import net.axel.repositories.interfaces.IComponentRepository;
import net.axel.services.interfaces.IComponentService;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MaterialService implements IComponentService<Material, MaterialDto> {

    private final IComponentRepository<Material> materialRepository;

    public MaterialService(IComponentRepository<Material> materialRepository) {
        this.materialRepository = materialRepository;
    }

    @Override
    public List<Material> save(List<MaterialDto> materials, Double vat, Project project) {
        return materials.stream()
                .map(materialDto -> {
                    Material material  = new Material(
                            UUID.randomUUID(),
                            materialDto.materialName(),
                            materialDto.materialCost(),
                            materialDto.quantity(),
                            vat,
                            materialDto.materialType(),
                            materialDto.materialEfficiencyFactory(),
                            project,
                            materialDto.transportCost()
                  );
                    return materialRepository.save(material);
                })
                .collect(Collectors.toList());
    }

    @Override
    public Double calculateTotalCost(List<MaterialDto> dto) {
        return dto.stream()
                .mapToDouble(material -> (material.materialCost() * material.quantity() * material.materialEfficiencyFactory()) + material.transportCost())
                .sum();
    }

    @Override
    public Double addVat(Double materialCost, Double vat) {
        return materialCost + (materialCost + vat);
    }
}
