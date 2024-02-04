package jh.github.com.itemservice;

import jh.github.com.inventoryservice.InventoryController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemController controller;

    public List<Item> get() {
        return controller.getAllItems();
    }

}
