package jh.github.com.invmanager.exceptions;

public class InventoryNotFoundException extends RuntimeException {

    public InventoryNotFoundException(Long id) {
        super("Could not find inventory " + id);
    }
}
