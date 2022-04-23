package com.fadedbytes.theotherside.listeners.blocks;

import com.fadedbytes.theotherside.TheOtherside;
import com.fadedbytes.theotherside.world.TheOthersideWorld;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import static com.fadedbytes.theotherside.TheOtherside.OVERWORLD_NAME;

/**
 * Detects when a player breaks a block in the overworld in order to modify the Otherside
 */
public class PlayerBreakingBlockInOverworldListener implements Listener {

    @EventHandler
    public static void onPlayerBreakingBlockInOverworld(BlockBreakEvent event) {
        // Return if the event is not in the overworld
        Block overworldBlock = event.getBlock();
        if (!event.getBlock().getWorld().equals(Bukkit.getWorld(OVERWORLD_NAME))) return;

        Block othersideBlock = new Location(TheOthersideWorld.getWorld(), overworldBlock.getX(), overworldBlock.getY(), overworldBlock.getZ()).getBlock();

        if (canBreakOtherside(othersideBlock) && canBeBroken(overworldBlock)) {
            if (othersideBlock.getType() != Material.AIR) {
                othersideBlock.breakNaturally();
                othersideBlock.getLocation().getWorld().playSound(othersideBlock.getLocation(), Sound.BLOCK_SCULK_SENSOR_HIT, SoundCategory.BLOCKS, 2, 1);
                othersideBlock.getLocation().getWorld().playSound(othersideBlock.getLocation(), Sound.BLOCK_DEEPSLATE_STEP, SoundCategory.BLOCKS, 2, 0);
            }
        }

    }

    /**
     * Checks if the block at the Overworld can break the block at the Otherside
     * @param block The block to check
     * @return True if the block can be broken, false otherwise
     */
    private static boolean canBreakOtherside(Block block) {
        return true;
    }

    /**
     * Checks if the block at the Otherside is a valid block to break
     * @param block The block to check
     * @return True if the block is valid, false otherwise
     */
    private static boolean canBeBroken(Block block) {
        return true;
    }

}
