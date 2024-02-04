package jh.github.com.inventoryservice;

import jakarta.persistence.EntityManager;
import jh.github.com.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class InventoryDao implements Dao<Inventory> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Inventory save(Inventory inventory) {
        return entityManager.merge(inventory);
    }

    @Override
    public List<Inventory> getAll() {
        return entityManager.createQuery("SELECT i FROM Inventory i", Inventory.class).getResultList();
    }

    @Override
    public Optional<Inventory> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Inventory.class, id));
    }

    @Override
    public void delete(Inventory inventory) {
        entityManager.remove(inventory);
    }
}
