package jh.github.com.invmanager.item;

import jakarta.persistence.Entity;

import java.util.Objects;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Item {
    private @Id @GeneratedValue Long id;
    private String name;
    private String category;
    private ItemRarity rarity;

    Item() {}

    Item(String name, String category) {
        this(name, category, ItemRarity.COMMON);
    }

    Item(String name, String category, ItemRarity rarity) {

        this.name = name;
        this.category = category;
        this.rarity = rarity;
    }

    public Long getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getCategory() {
        return this.category;
    }

    public ItemRarity getRarity() {
        return this.rarity;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setRarity(ItemRarity rarity) {
        this.rarity = rarity;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o)
            return true;
        if (!(o instanceof Item item))
            return false;

        return Objects.equals(this.id, item.id) && Objects.equals(this.name, item.name) && Objects.equals(this.rarity, item.rarity)
                && Objects.equals(this.category, item.category);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id, this.name, this.category);
    }

    @Override
    public String toString() {
        return "Item{"
                + "id=" + this.id
                + ", name='" + this.name + '\''
                + ", category='" + this.category + '\''
                + ", rarity='" + this.rarity + '\'' + '}';
    }
}
