package jh.github.com.invmanager.inventory;

import jh.github.com.invmanager.exceptions.InventoryElementNotFoundException;
import jh.github.com.invmanager.exceptions.InventoryNotFoundException;
import jh.github.com.invmanager.exceptions.InventorySizeException;
import jh.github.com.invmanager.exceptions.ItemNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mediatype.problem.Problem;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class InventoryHandler {
    private final InventoryRepository repository;
    private final InventoryModelAssembler inventoryAssembler;
    private final InventoryElementModelAssembler elementAssembler;

    InventoryHandler(InventoryRepository repository, InventoryModelAssembler inventoryAssembler, InventoryElementModelAssembler elementAssembler) {
        this.repository = repository;
        this.inventoryAssembler = inventoryAssembler;
        this.elementAssembler = elementAssembler;
    }

    @PostMapping("/inventories")
    public ResponseEntity<?> newInventory(@RequestBody Inventory newInventory) {

        EntityModel<Inventory> entityModel = inventoryAssembler.toModel(repository.save(newInventory));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/inventories")
    public CollectionModel<EntityModel<Inventory>> all() {

        List<EntityModel<Inventory>> inventories = repository.findAll().stream()
                .map(inventoryAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(inventories, linkTo(methodOn(InventoryHandler.class).all()).withSelfRel());
    }

    @GetMapping("/inventories/{id}")
    public EntityModel<Inventory> one(@PathVariable Long id) {

        Inventory inventory = repository.findById(id).orElseThrow(() -> new InventoryNotFoundException(id));

        return inventoryAssembler.toModel(inventory);
    }

    @PutMapping("/inventories/{id}")
    public ResponseEntity<?> updateInventory(@RequestBody Inventory newInventory, @PathVariable Long id) {

        Inventory updatedInventory = repository.findById(id) //
                .map(inventory -> {
                    inventory.setOwnerName(newInventory.getOwnerName());
                    return repository.save(inventory);
                })
                .orElseGet(() -> {
                    newInventory.setId(id);
                    return repository.save(newInventory);
                });

        EntityModel<Inventory> entityModel = inventoryAssembler.toModel(updatedInventory);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/inventories/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id) {

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/inventories/{id}")
    public ResponseEntity<?> addContents(@RequestBody InventoryElement newInventoryElement, @PathVariable Long id) {

        Inventory inventory = repository.findById(id).orElseThrow(() -> new InventoryNotFoundException(id));

        if (inventory.getMaxSize() < inventory.getNumElements()) {
            inventory.addInventoryElement(newInventoryElement);

            Inventory updatedInventory = repository.save(inventory);
            EntityModel<Inventory> entityModel = inventoryAssembler.toModel(updatedInventory);

            return ResponseEntity
                    .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                    .body(entityModel);
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                .body(Problem.create() //
                        .withTitle("Method not allowed")
                        .withDetail("You cannot cancel add an item to an already full inventory"));
    }

    @GetMapping("/inventories/{invIndex}/{itemIndex}")
    public EntityModel<InventoryElement> getContentsByIndex(@PathVariable("invIndex") Long id, @PathVariable("itemIndex") int index) {

        Inventory inventory = repository.findById(id).orElseThrow(() -> new InventoryNotFoundException(id));
        final int numElements = inventory.getNumElements();

        if (index >= numElements)
            throw new InventorySizeException("Index " + index + " is greater than the number of elements");

        InventoryElement element = inventory.getInventoryElements().get(index);

        if (element == null)
            throw new InventoryElementNotFoundException(index);

        return elementAssembler.toModel(element);
    }

    @PutMapping("/inventories/{invIndex}/{itemIndex}")
    public ResponseEntity<?> updateContentsByIndex(@PathVariable("invIndex") Long id, @PathVariable("itemIndex") int index) {

        Inventory updatedInventory = repository.findById(id) //
                .map(inventory -> {
                    InventoryElement element = inventory.getInventoryElements().get(index);

                    if (element == null)
                        throw new InventoryElementNotFoundException(index);

                    inventory.setInventoryElement(index, element);
                    return repository.save(inventory);
                })
                .orElseThrow(() -> new InventoryNotFoundException(id));

        EntityModel<Inventory> entityModel = inventoryAssembler.toModel(updatedInventory);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/inventories/{invIndex}/{itemIndex}")
    public ResponseEntity<?> deleteContentsByIndex(@PathVariable("invIndex") Long id, @PathVariable("itemIndex") int index) {

        Inventory inventory = repository.findById(id).orElseThrow(() -> new InventoryNotFoundException(id));
        final int numElements = inventory.getNumElements();

        if (index >= numElements)
            return ResponseEntity
                    .status(HttpStatus.METHOD_NOT_ALLOWED)
                    .header(HttpHeaders.CONTENT_TYPE, MediaTypes.HTTP_PROBLEM_DETAILS_JSON_VALUE) //
                    .body(Problem.create() //
                            .withTitle("Method not allowed")
                            .withDetail("You cannot delete an entity at index " + index + " as there are only " + numElements + " elements"));

        InventoryElement element = inventory.removeInventoryElementByIndex(index);

        return (element == null) ? ResponseEntity.notFound().build()
                : ResponseEntity.ok(inventoryAssembler.toModel(repository.save(inventory)));
    }
}

