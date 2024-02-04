package jh.github.com.itemservice;

import jakarta.persistence.*;
import jh.github.com.IdentifiableEntity;
import jh.github.com.inventoryservice.Inventory;

import java.math.BigDecimal;

@Entity
@Table(name="items")
public class Item implements IdentifiableEntity {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal worth;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Inventory inventory;

    public Item() {}

    public Item(Inventory inventory, String name, String description, String imageUrl, BigDecimal worth) {
        this.inventory = inventory;
        this.name = name;
        this.description = description;
        this.imageUrl = imageUrl;
        this.worth = worth;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public BigDecimal getWorth() {
        return worth;
    }

    public void setWorth(BigDecimal worth) {
        this.worth = worth;
    }

    @Override
    public String getRel() {
        return "items";
    }

    public void set(Item newItem) {
        this.name = newItem.getName();
        this.description = newItem.getDescription();
        this.imageUrl = newItem.getImageUrl();
        this.worth = newItem.getWorth();
    }
}
