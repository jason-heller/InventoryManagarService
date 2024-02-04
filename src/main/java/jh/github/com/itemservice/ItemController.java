package jh.github.com.itemservice;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import jh.github.com.dao.DaoAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path="/item")
public class ItemController {
	@Autowired
	private ItemDao itemDao;
	private final DaoAssembler assembler;

	public ItemController(DaoAssembler assembler) {
		this.assembler = assembler;
	}

	@PostMapping(consumes="application/json")
	public ResponseEntity<Item> createItem(@RequestBody Item item) {
		Item savedItem = itemDao.save(item);
		return ResponseEntity.created(URI.create("/item/" + savedItem.getId())).body(savedItem);
	}

	@GetMapping
	public List<Item> getAllItems() {
		return itemDao.getAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<Item> getItemById(@PathVariable("id") Long id) {
		Optional<Item> item = itemDao.getById(id);

		return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateItem(@PathVariable("id") Long id, @RequestBody Item newItem) {
		Item updatedItem = itemDao.getById(id)
				.map(item -> {
					item.set(newItem);
					return itemDao.save(item);
				})
				.orElseGet(() -> {
					newItem.setId(id);
					return itemDao.save(newItem);
				});

		return assembler.toResponse(updatedItem);
	}
}
