package net.axel.models.entities;

import net.axel.models.enums.ProjectStatus;

import java.util.UUID;

public class Project {

    private UUID id;
    private String name;
    private Double surface;
    private Double profitMargin;
    private Double totalCost;
    private ProjectStatus projectStatus;
    private Client client;

    
}
