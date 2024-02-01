package jh.github.com.invmanager.inventory;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jh.github.com.invmanager.exceptions.InventorySizeException;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Inventory {
    private @Id @GeneratedValue Long id;
    private String ownerName;
    private List<InventoryElement> inventoryElements;

    private int maxSize = 100;

    Inventory() {}

    Inventory(String ownerName, int maxSize) {
        this.ownerName = ownerName;
        this.maxSize = maxSize;

        inventoryElements = new ArrayList<>(maxSize);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public int getMaxSize() {
        return maxSize;
    }

    int getNumElements() {
        return this.inventoryElements.size();
    }

    public void setMaxSize(int maxSize) {
        if (getNumElements() < maxSize)
            throw new InventorySizeException("Max size (" + maxSize + ") is less than current number of elements (" + getNumElements() + ")");

        this.maxSize = maxSize;
    }

    public void increaseMaxSize(int increase) {
        this.maxSize += increase;
    }

    public List<InventoryElement> getInventoryElements() {
        return inventoryElements;
    }

    public void setInventoryElements(List<InventoryElement> inventoryElements) {
        this.inventoryElements = inventoryElements;
    }

    public void addInventoryElement(InventoryElement newInventoryElement) {
        if (getNumElements() == maxSize)
            throw new InventorySizeException("Cannot add element, inventory is at capacity");

        inventoryElements.add(newInventoryElement);
    }

    public void setInventoryElement(int index, InventoryElement element) {
        if (getNumElements() >= maxSize)
            throw new InventorySizeException("Cannot add element, index is greater than capacity");

        inventoryElements.add(index, element);
    }

    public InventoryElement removeInventoryElementByIndex(int index) {
        return inventoryElements.remove(index);
    }
}
