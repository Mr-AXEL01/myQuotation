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
}
