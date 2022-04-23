package com.fadedbytes.theotherside.listeners;

import com.fadedbytes.theotherside.TheOtherside;
import com.fadedbytes.theotherside.items.StrangerSpyglass.StrangerSpyglassEventListener;
import com.fadedbytes.theotherside.listeners.blocks.PlayerBreakingBlockInOthersideListener;
import com.fadedbytes.theotherside.listeners.blocks.PlayerBreakingBlockInOverworldListener;
import com.fadedbytes.theotherside.listeners.blocks.PlayerPlacingBlockInOthersideListener;
import com.fadedbytes.theotherside.listeners.blocks.PlayerPlacingBlockInOverworldListener;
import com.fadedbytes.theotherside.listeners.gameplay.PlayerDyingInOverworldNearToOthersidePlayer;
import com.fadedbytes.theotherside.listeners.gameplay.PlayerSummoningLightingOnChestListener;
import com.fadedbytes.theotherside.listeners.gameplay.PlayerUsingElytraInOtherside;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

public class ListenerManager {

    public static void registerListeners() {
        PluginManager manager = Bukkit.getPluginManager();

        // World events
        manager.registerEvents(new PlayerBreakingBlockInOverworldListener(), TheOtherside.PLUGIN);
        manager.registerEvents(new PlayerBreakingBlockInOthersideListener(), TheOtherside.PLUGIN);

        manager.registerEvents(new PlayerPlacingBlockInOverworldListener(), TheOtherside.PLUGIN);
        manager.registerEvents(new PlayerPlacingBlockInOthersideListener(), TheOtherside.PLUGIN);

        manager.registerEvents(new PlayerDyingInOverworldNearToOthersidePlayer(), TheOtherside.PLUGIN);
        manager.registerEvents(new PlayerSummoningLightingOnChestListener(), TheOtherside.PLUGIN);
        manager.registerEvents(new PlayerUsingElytraInOtherside(), TheOtherside.PLUGIN);

        // Item events
        manager.registerEvents(new StrangerSpyglassEventListener(), TheOtherside.PLUGIN);

    }
}
