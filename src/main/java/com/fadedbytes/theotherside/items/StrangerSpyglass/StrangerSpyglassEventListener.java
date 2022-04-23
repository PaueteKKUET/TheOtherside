package com.fadedbytes.theotherside.items.StrangerSpyglass;

import com.fadedbytes.theotherside.items.OthersideItemManager;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class StrangerSpyglassEventListener implements Listener {
    static HashMap<Player, StrangerSpyglassTeleport> teleportQueue = new HashMap<>();

    @EventHandler
    public static void rightClickUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getGameMode().equals(GameMode.CREATIVE)) {
            player.setCooldown(Material.SPYGLASS, 0);
        }

        if (player.hasCooldown(Material.SPYGLASS)) return;

        ItemStack item = event.getItem();
        if (item == null) return;
        if (!isStrangerSpyglass(item)) return;


        if (event.getAction().equals(Action.RIGHT_CLICK_AIR) || event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (teleportQueue.containsKey(player)) {
                teleportQueue.get(player).right = true;
            } else {
                StrangerSpyglassTeleport teleport = new StrangerSpyglassTeleport(player);
                teleport.right = true;
                teleportQueue.put(player, teleport);
            }
        } else if(event.getAction().equals(Action.LEFT_CLICK_AIR) || event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (teleportQueue.containsKey(player)) {
                teleportQueue.get(player).left = true;
            } else {
                StrangerSpyglassTeleport teleport = new StrangerSpyglassTeleport(player);
                teleport.left = true;
                teleportQueue.put(player, teleport);
            }
        }
    }

    static boolean isStrangerSpyglass(ItemStack item) {
        return OthersideItemManager.isItem(item, StrangerSpyglass.ID);
    }
}
