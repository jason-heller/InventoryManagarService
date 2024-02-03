package jh.github.com.inventoryservice;

import jh.github.com.itemservice.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/inv", produces={"application/json"})
public class InventoryController {

    @Autowired
    private final InventoryRepository repository;

    public InventoryController(InventoryRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/inv")
    public Inventory createInventory(Inventory inventory) {
        return repository.save(inventory);
    }

    @GetMapping("/inv/{id}")
    public Inventory getInventory(@PathVariable Long id) {
        Optional<Inventory> inventory = repository.findById(id);

        return inventory.orElse(null);
    }

    @GetMapping("/inv")
    public Iterable<Inventory> getAllInventories() {
        return repository.findAll();
    }

    @PutMapping("/inv/{id}")
    public Inventory addToInventory(@PathVariable Long id, Item item) {
        Optional<Inventory> optInventory = repository.findById(id);

        if (optInventory.isEmpty())
            return null;

        Inventory inventory = optInventory.get();
        List<Item> items = inventory.getItems();

        items.add(item);
        return repository.save(inventory);
    }

    @PutMapping("/inv/{id}")
    public Inventory removeFromInventory(@PathVariable Long id, Item item) {
        Optional<Inventory> optInventory = repository.findById(id);

        if (optInventory.isEmpty())
            return null;

        Inventory inventory = optInventory.get();
        List<Item> items = inventory.getItems();

        if (!items.remove(item))
            return null;

        return repository.save(inventory);
    }


    @DeleteMapping("/inv/{id}")
    public void removeInventory(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        }
    }
}
