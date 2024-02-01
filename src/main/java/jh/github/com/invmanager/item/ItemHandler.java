package jh.github.com.invmanager.item;

import jh.github.com.invmanager.exceptions.ItemNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.hateoas.EntityModel;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class ItemHandler {
    private final ItemRepository repository;
    private final ItemModelAssembler assembler;

    ItemHandler(ItemRepository repository, ItemModelAssembler assembler) {
        this.repository = repository;
        this.assembler = assembler;
    }

    @PostMapping("/items")
    public ResponseEntity<?> newItem(@RequestBody Item newItem) {

        EntityModel<Item> entityModel = assembler.toModel(repository.save(newItem));

        return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @GetMapping("/items")
    public  CollectionModel<EntityModel<Item>> all() {

        List<EntityModel<Item>> items = repository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(items, linkTo(methodOn(ItemHandler.class).all()).withSelfRel());
    }

    @GetMapping("/items/{id}")
    public EntityModel<Item> one(@PathVariable Long id) {

        Item item = repository.findById(id).orElseThrow(() -> new ItemNotFoundException(id));

        return assembler.toModel(item);
    }

    @PutMapping("/items/{id}")
    public ResponseEntity<?> replaceItem(@RequestBody Item newItem, @PathVariable Long id) {

        Item updatedItem = repository.findById(id)
                .map(item -> {
                    item.setName(newItem.getName());
                    item.setCategory(newItem.getCategory());
                    return repository.save(item);
                })
                .orElseGet(() -> {
                    newItem.setId(id);
                    return repository.save(newItem);
                });

        EntityModel<Item> entityModel = assembler.toModel(updatedItem);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
                .body(entityModel);
    }

    @DeleteMapping("/items/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) {

        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
