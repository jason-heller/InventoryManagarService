package jh.github.com.inventoryservice;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Table(name="inventories")
public class Inventory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String ownerName;
    private Date lastInteraction;
    private BigDecimal totalWorth = new BigDecimal(0);

    @JsonIgnore
    //@ElementCollection
    @OneToMany
    private List<Item> items = new ArrayList<>();

    public Inventory() {}

    public Inventory(String ownerName) {
        this.ownerName = ownerName;

        this.lastInteraction = new Date();
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
            this.totalWorth = this.totalWorth.add(item.getWorth());
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
