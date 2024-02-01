package jh.github.com.invmanager.inventory;

import jh.github.com.invmanager.item.Item;
import jh.github.com.invmanager.item.ItemCondition;
import jh.github.com.invmanager.item.ItemQuality;

public class InventoryElement {

    private Item item;
    private ItemCondition condition;
    private ItemQuality quality;

    public InventoryElement() {}

    public InventoryElement(Item item, ItemCondition condition, ItemQuality quality) {
        this.item = item;
        this.condition = condition;
        this.quality = quality;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public ItemCondition getCondition() {
        return condition;
    }

    public void setCondition(ItemCondition condition) {
        this.condition = condition;
    }

    public ItemQuality getQuality() {
        return quality;
    }

    public void setQuality(ItemQuality quality) {
        this.quality = quality;
    }
}
