package jh.github.com.itemservice;

import jakarta.persistence.EntityManager;
import jh.github.com.dao.Dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class ItemDao implements Dao<Item> {

    @Autowired
    private EntityManager entityManager;

    @Override
    public Item save(Item item) {
        return entityManager.merge(item);
    }

    @Override
    public List<Item> getAll() {
        return entityManager.createQuery("SELECT i FROM Item i", Item.class).getResultList();
    }

    @Override
    public Optional<Item> getById(Long id) {
        return Optional.ofNullable(entityManager.find(Item.class, id));
    }

    @Override
    public void delete(Item item) {
        entityManager.remove(item);
    }
}
