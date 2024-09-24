package net.axel.repositories.interfaces;

public interface IComponentRepository<Entity> {

    Entity save(Entity component);
}
