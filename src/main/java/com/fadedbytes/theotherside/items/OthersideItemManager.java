package com.fadedbytes.theotherside.items;

import com.fadedbytes.theotherside.TheOtherside;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Set;

public class OthersideItemManager {

    private static final HashMap<String, OthersideItem> ITEMS = new HashMap<>();
    public static final NamespacedKey ITEM_NAME = new NamespacedKey(TheOtherside.PLUGIN, "name");

    /**
     * Register an item. Also sets the item's name into the item's persistent data container.
     * @param item The item to register.
     */
    public static void registerItem(OthersideItem item) {
        ItemMeta meta = item.getItem().getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();
        container.set(ITEM_NAME, PersistentDataType.STRING, item.getName());
        item.getItem().setItemMeta(meta);

        TheOtherside.PLUGIN.getLogger().info("Registered item: " + item.getName());

        ITEMS.put(item.getName(), item);
    }

    /**
     * Returns whether the item passed mathes the name passed.
     * @param item The item to check. Must not be null.
     * @param name The name to check. Must not be null.
     * @return Whether the item passed matches the name passed.
     */
    public static boolean isItem(@NotNull ItemStack item, @NotNull String name) {
        OthersideItem customItem = ITEMS.get(name);
        if (customItem == null) return false;

        String value1 = item.getItemMeta().getPersistentDataContainer().get(ITEM_NAME, PersistentDataType.STRING);
        String value2 = customItem.getItem().getItemMeta().getPersistentDataContainer().get(ITEM_NAME, PersistentDataType.STRING);

        if (value1 == null || value2 == null) return false;

        return value1.equals(value2);
    }

    /**
     * Returns a set of all registered item names.
     * @return A set of all registered item names.
     */
    public static Set<String> getItemKeys() {
        return ITEMS.keySet();
    }

    /**
     * Returns the item with the name passed.
     * @param name The name of the item to return.
     * @return The item with the name passed. Returns null if no item with the name passed exists.
     */
    public static ItemStack getItem(String name) {
        OthersideItem customItem = ITEMS.get(name);
        if (customItem == null) return null;
        return ITEMS.get(name).getItem();
    }

}
