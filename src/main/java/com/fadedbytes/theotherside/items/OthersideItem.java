package com.fadedbytes.theotherside.items;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

/**
 * Preset class for the Otherside items.
 */
public abstract class OthersideItem {

    private final String name;
    private final ItemStack item;

    protected OthersideItem(@NotNull String name) {
        this.name = name;
        this.item = initItem();
        OthersideItemManager.registerItem(this);
    }

    /**
     * Gets the name of the item.
     * @return the name of the item
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the item.
     * @return the item
     */
    public ItemStack getItem() {
        return this.item;
    }

    protected abstract ItemStack initItem();

}
