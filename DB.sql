-- creating enum types
CREATE TYPE ProjectStatus AS ENUM ('IN_PROGRESS', 'COMPLETED', 'CANCELLED');
CREATE TYPE ComponentType AS ENUM ('MATERIAL', 'LABOR');

-- creating tables

CREATE TABLE IF NOT EXISTS clients (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    address VARCHAR(300) NOT NULL,
    phone VARCHAR(255) NOT NULL,
    is_professional BOOLEAN NOT NULL,
    deleted_at TIMESTAMP,

    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS projects (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    surface DOUBLE PRECISION NOT NULL,
    profit_margin DOUBLE PRECISION ,
    total_cost DOUBLE PRECISION ,
    project_status ProjectStatus NOT NULL,
    client_id UUID NOT NULL,
    deleted_at TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (client_id) REFERENCES clients(id)
);

CREATE TABLE IF NOT EXISTS quotations (
    id UUID NOT NULL,
    estimated_amount DOUBLE PRECISION NOT NULL,
    issued_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    validity_date TIMESTAMP NOT NULL,
    accepted BOOLEAN NOT NULL,
    project_id UUID NOT NULL,
    deleted_at TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES projects(id)
);

CREATE TABLE IF NOT EXISTS components (
    id UUID NOT NULL,
    name VARCHAR(255) NOT NULL,
    unit_cost DOUBLE PRECISION NOT NULL,
    quantity_or_duration DOUBLE PRECISION NOT NULL,
    vat DOUBLE PRECISION NOT NULL,
    component_type ComponentType NOT NULL,
    efficiency_factor DOUBLE PRECISION NOT NULL,
    project_id UUID NOT NULL,
    deleted_at TIMESTAMP,

    PRIMARY KEY (id),
    FOREIGN KEY (project_id) REFERENCES projects(id)
);

-- creating child tables to inherit from component table.

CREATE TABLE IF NOT EXISTS labors (
) INHERITS (components);

CREATE TABLE IF NOT EXISTS materials (
    transport_cost DOUBLE PRECISION NOT NULL
) INHERITS (components);
