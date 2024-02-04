package jh.github.com.dao;

import jh.github.com.inventoryservice.Inventory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    T save(T t);

    List<T> getAll();

    Optional<T> getById(Long id);

    void delete(T t);
}
