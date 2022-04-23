package com.fadedbytes.theotherside.items.StrangerSpyglass;

import com.fadedbytes.theotherside.TheOtherside;
import com.fadedbytes.theotherside.world.TheOthersideWorld;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.util.RayTraceResult;

public class StrangerSpyglassTeleport {
    boolean left;
    boolean right;
    final Player player;

    public StrangerSpyglassTeleport(Player player) {
        this.player = player;

        Bukkit.getScheduler().runTaskLater(TheOtherside.PLUGIN, this::teleport, 1L);
    }

    public void teleport() {
        if (left && right) {
            Location initialLocation = player.getEyeLocation();
            RayTraceResult result = initialLocation.getWorld().rayTraceBlocks(initialLocation, initialLocation.getDirection(), 160.0D, FluidCollisionMode.NEVER, true);

            if (result != null) {

                Location finalLocation = result.getHitPosition().toLocation(initialLocation.getWorld(), initialLocation.getYaw(), initialLocation.getPitch());
                finalLocation.setY(finalLocation.getY() - 1.62D);

                if (!finalLocation.clone().add(0, 1, 0).getBlock().isPassable()) {
                    finalLocation.setY(finalLocation.getBlockY() + 2);
                } else if (!finalLocation.getBlock().isPassable()) {
                    finalLocation.setY(finalLocation.getBlockY() + 1);
                }

                player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 20, 255, false, false, false));
                player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 30, 0, false, false, false));

                BukkitScheduler scheduler = Bukkit.getScheduler();
                for (int i = 0; i < 20; i+=2) {
                    float pitch = i * 0.05f;
                    scheduler.runTaskLater(TheOtherside.PLUGIN, () -> finalLocation.getWorld().playSound(initialLocation, Sound.BLOCK_SCULK_SENSOR_BREAK, SoundCategory.AMBIENT, 1.0f, pitch), i);
                }
                scheduler.runTaskLater(TheOtherside.PLUGIN, () -> {
                    for (int i = 0; i < 5; i+=2) {
                        initialLocation.getWorld().playSound(initialLocation, Sound.BLOCK_SCULK_SENSOR_CLICKING, SoundCategory.AMBIENT, 1.0f, 1.3f);
                    }

                    player.teleport(finalLocation);
                    player.setSwimming(true);

                    for (int i = 0; i < 5; i+=2) {
                        finalLocation.getWorld().playSound(finalLocation, Sound.BLOCK_SCULK_SENSOR_CLICKING, SoundCategory.AMBIENT, 1.0f, 0.5f);
                    }

                    boolean isFriendly = false;

                    for (Material type : StrangerSpyglass.friendlyMaterials) {
                        if (type.equals(result.getHitBlock().getType())) {
                            isFriendly = true;
                        }
                    }

                    int cooldown;
                    boolean isCreative = player.getGameMode().equals(GameMode.CREATIVE);
                    if (!isFriendly && !isCreative) {
                        cooldown = 12000; // 10 minutes
                        for (int i = 0; i < 5; i++) {
                            float pitch = i * 0.1f;
                            scheduler.runTaskLater(TheOtherside.PLUGIN, () -> finalLocation.getWorld().playSound(finalLocation, Sound.ENTITY_FOX_SCREECH, 1.0f, pitch), i);
                        }
                    } else {
                        cooldown = isCreative ? 20 : 200; // 10 seconds
                    }

                    player.setCooldown(Material.SPYGLASS, cooldown);

                },20L);
            }
        }
        StrangerSpyglassEventListener.teleportQueue.remove(player);
    }
}
