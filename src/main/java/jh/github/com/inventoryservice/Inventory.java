package jh.github.com.inventoryservice;

import jakarta.persistence.*;
import jh.github.com.itemservice.Item;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="INVENTORY")
public class Inventory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "inventory_id")
    private Long id;

    @Column(nullable = false, name = "owner_name")
    private String ownerName;

    @Column(name = "last_interaction")
    private Date lastInteraction;

    @Column(name = "total_worth")
    private BigDecimal totalWorth;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "inventory", cascade = CascadeType.ALL)
    private List<Item> items;

    public Inventory() {
    }

    public Inventory(String ownerName) {
        this.ownerName = ownerName;

        this.lastInteraction = new Date();
        this.totalWorth = new BigDecimal(0);
        this.items = new ArrayList<>();
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

    public Date getLastInteraction() {
        return lastInteraction;
    }

    public void setLastInteraction(Date lastInteraction) {
        this.lastInteraction = lastInteraction;
    }

    @PrePersist
    public void updateLastInteraction() {
        this.lastInteraction = new Date();
    }

    public BigDecimal getTotalWorth() {
        return totalWorth;
    }

    public void setTotalWorth(BigDecimal totalWorth) {
        this.totalWorth = totalWorth;
    }

    public void calculateTotalWorth() {
        this.totalWorth = new BigDecimal(0);

        for(Item item : items)
            this.totalWorth = this.totalWorth.add(item.getValue());
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
