package com.fadedbytes.theotherside.listeners.gameplay;

import com.fadedbytes.theotherside.world.TheOthersideWorld;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.block.data.BlockData;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Trident;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

/**
 * When a lighting bolt is summoned on a chest, the contents will be sent to The Otherside.
 */
public class PlayerSummoningLightingOnChestListener implements Listener {

    @EventHandler
    public static void onTridentHit(ProjectileHitEvent event) {
        // Check if the projectile is a trident
        if (event.getEntity() instanceof Trident trident) {
            // Check if the trident has the channeling enchantment
            if (!trident.getItem().getEnchantments().containsKey(Enchantment.CHANNELING)) return;

            Block hitBlock = event.getHitBlock();
            if (hitBlock == null) return;

            // Check if the block is a chest or a trapped chest
            if (!(hitBlock.getType().equals(Material.CHEST) || hitBlock.getType().equals(Material.TRAPPED_CHEST))) return;

            teleportChest(hitBlock);
        }
    }

    /**
     * Teleports the contents of the chest to The Otherside.
     * @param sourceChest The chest to teleport.
     */
    private static void teleportChest(Block sourceChest) {

        // Summons a lightning bolt at the original chest
        sourceChest.getLocation().getWorld().strikeLightning(sourceChest.getLocation());

        // Calculates the location of the chest on The Otherside
        Location destinationChest = new Location(TheOthersideWorld.getWorld(), sourceChest.getX(), sourceChest.getY(), sourceChest.getZ());

        // Stores the contents of the source block and the destination block. Then, swaps them.
        BlockData sourceChestBlockData = sourceChest.getBlockData();
        BlockData destinationChestBlockData = TheOthersideWorld.getWorld().getBlockData(destinationChest);

        TheOthersideWorld.getWorld().setBlockData(destinationChest, sourceChestBlockData);
        sourceChest.setBlockData(destinationChestBlockData);



        // Summons a lightning bolt at the destination chest
        destinationChest.getWorld().strikeLightning(destinationChest);

        // Plays a sound at both locations
        destinationChest.getWorld().playSound(destinationChest, Sound.ENTITY_VEX_DEATH, SoundCategory.AMBIENT, 4.0f, 0.0f);
        sourceChest.getWorld().playSound(sourceChest.getLocation(), Sound.ENTITY_VEX_DEATH, SoundCategory.AMBIENT, 4.0f, 0.0f);

    }

}
