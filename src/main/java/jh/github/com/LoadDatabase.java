package jh.github.com;

import jh.github.com.inventoryservice.Inventory;
import jh.github.com.inventoryservice.InventoryRepository;
import jh.github.com.itemservice.Item;
import jh.github.com.itemservice.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    Inventory i0 = new Inventory("inv 0");
    Inventory i1 = new Inventory("inv 1");

    @Bean
    CommandLineRunner initInventoryDatabase(InventoryRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(i0));
            log.info("Preloading " + repository.save(i1));
        };
    }

    @Bean
    CommandLineRunner initItemDatabase(ItemRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Item(i0, "Bilbo Baggins", "burglar", "none", new BigDecimal(3))));
            log.info("Preloading " + repository.save(new Item(i1, "Frodo Baggins", "thief", "none", new BigDecimal(6))));
        };
    }
}