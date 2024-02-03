package jh.github.com.itemservice;
import java.util.Optional;

import jh.github.com.inventoryservice.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(path="/item", produces={"application/json"})
public class ItemController {
	@Autowired
	private final ItemRepository repository;

	public ItemController(ItemRepository repository) {
		this.repository = repository;
	}

	@GetMapping
	public Iterable<Item> getAllItems() {
		return repository.findAll();
	}

	@GetMapping("/{id}")
	public Item getItemById(@PathVariable("id") Long id) {
		Optional<Item> item = repository.findById(id);

		return item.orElse(null);
	}

	@PostMapping(consumes="application/json")
	public Item createItem(@RequestBody Item product) {
		return repository.save(product);
	}
}
