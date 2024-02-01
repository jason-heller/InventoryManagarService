package jh.github.com.invmanager.exceptions;

public class InventoryElementNotFoundException extends RuntimeException {

    public InventoryElementNotFoundException(int index) {
        super("Could not find inventory element at index " + index);
    }
}