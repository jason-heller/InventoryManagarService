package jh.github.com.inventoryservice;

import jh.github.com.dao.Dao;
import jh.github.com.itemservice.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/inv")
public class InventoryController {

    @Autowired
    private Dao<Inventory> inventoryDao;

    @PostMapping()
    public ResponseEntity<Inventory> createInventory(Inventory inventory) {
        Inventory savedInventory = inventoryDao.save(inventory);
        return ResponseEntity.created(URI.create("/inv/" + savedInventory.getId())).body(savedInventory);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Inventory> getInventoryById(@PathVariable Long id) {
        Optional<Inventory> inventory = inventoryDao.getById(id);

        return inventory.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping()
    public List<Inventory> getAllInventories() {
        return inventoryDao.getAll();
    }

    @PutMapping("/add/{id}")
    public ResponseEntity<Inventory> addToInventory(@PathVariable Long id, Item item) {
        Optional<Inventory> optInventory = inventoryDao.getById(id);

        if (optInventory.isEmpty())
            return ResponseEntity.notFound().build();

        Inventory inventory = optInventory.get();
        List<Item> items = inventory.getItems();

        items.add(item);
        inventoryDao.save(inventory);
        return ResponseEntity.ok(inventory);
    }

    @PutMapping("/remove/{id}")
    public ResponseEntity<Inventory> removeFromInventory(@PathVariable Long id, Item item) {
        Optional<Inventory> optInventory = inventoryDao.getById(id);

        if (optInventory.isEmpty())
            return ResponseEntity.notFound().eTag("inventory").build();

        Inventory inventory = optInventory.get();
        List<Item> items = inventory.getItems();

        if (!items.remove(item))
            return ResponseEntity.notFound().eTag("item").build();

        inventoryDao.save(inventory);
        return ResponseEntity.ok(inventory);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Inventory> removeInventory(@PathVariable Long id) {
        Optional<Inventory> inventory = inventoryDao.getById(id);

        if (inventory.isPresent()) {
            inventoryDao.delete(inventory.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
