package com.fadedbytes.theotherside.listeners.gameplay;

import com.fadedbytes.theotherside.world.TheOthersideWorld;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityToggleGlideEvent;

/**
 * Prevents the usage of elytras in The Otherside.
 */
public class PlayerUsingElytraInOtherside implements Listener {

    @EventHandler
    public void onPlayerUsingElytra(EntityToggleGlideEvent event) {
        if (event.getEntity().getWorld().equals(TheOthersideWorld.getWorld())) {
            event.setCancelled(true);
            event.getEntity().getWorld().playSound(event.getEntity(), Sound.BLOCK_FIRE_EXTINGUISH, SoundCategory.AMBIENT, 1.0f, 0.8f);
        }
    }

}
