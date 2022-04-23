package com.fadedbytes.theotherside;

import com.fadedbytes.theotherside.commands.GetOthersideCommand;
import com.fadedbytes.theotherside.debug.Link.LinkCommand;
import com.fadedbytes.theotherside.debug.Link.MovementSyncronization;
import com.fadedbytes.theotherside.items.StrangerSpyglass.StrangerSpyglass;
import com.fadedbytes.theotherside.listeners.ListenerManager;
import com.fadedbytes.theotherside.world.TheOthersideWorld;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;

public final class TheOtherside extends JavaPlugin {

    public static TheOtherside PLUGIN;
    public static String OVERWORLD_NAME = "world";

    @Override
    public void onEnable() {
        PLUGIN = this;

        setupModules();
        initItems();

        ListenerManager.registerListeners();
    }

    @Override
    public void onDisable() {

    }

    private void setupModules() {

        // PlayerLink
        LinkCommand linkCommand = new LinkCommand();
        getCommand("link").setExecutor(linkCommand);
        getCommand("link").setTabCompleter(linkCommand);

        Bukkit.getPluginManager().registerEvents(new MovementSyncronization(), this);

        // GetOtherside
        GetOthersideCommand getOthersideCommand = new GetOthersideCommand();
        getCommand("getotherside").setExecutor(getOthersideCommand);
        getCommand("getotherside").setTabCompleter(getOthersideCommand);

        // World
        TheOthersideWorld.generateWorld();
    }

    private static void initItems() {
        new StrangerSpyglass();
    }
}
