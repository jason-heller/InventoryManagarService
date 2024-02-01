package jh.github.com.invmanager.inventory;

import jh.github.com.invmanager.item.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {}