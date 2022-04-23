package com.fadedbytes.theotherside.debug.Link;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.ArrayList;
import java.util.List;

public class MovementSyncronization implements Listener {

    @EventHandler
    public static void onMasterMove(PlayerMoveEvent event) {
        List<PlayerLink> linksToUpdate = new ArrayList<>();
        Player player = event.getPlayer();

        for (PlayerLink link : PlayerLink.LINKED_PLAYERS) {
            if (link.getMaster().equals(player)) {
                linksToUpdate.add(link);
            }
        }

        for (PlayerLink link : linksToUpdate) {
            link.update();
        }
    }

}
