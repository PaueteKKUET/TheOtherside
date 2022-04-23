package com.fadedbytes.theotherside.items.StrangerSpyglass;

import com.fadedbytes.theotherside.items.OthersideItem;
import com.fadedbytes.theotherside.utils.CustomColors;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class StrangerSpyglass extends OthersideItem {

    public static String ID = "stranger_spyglass";

    static final Material[] friendlyMaterials = {
            Material.CHISELED_DEEPSLATE,
            Material.SCULK_SENSOR
    };

    public StrangerSpyglass() {
        super(ID);
    }

    @Override
    protected ItemStack initItem() {
        ItemStack item = new ItemStack(Material.SPYGLASS, 1);

        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(CustomColors.EPIC + "Visión del forastero");
        meta.setCustomModelData(10);
        item.setItemMeta(meta);

        return item;
    }
}
