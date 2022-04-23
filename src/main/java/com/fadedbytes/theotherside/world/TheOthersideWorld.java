package com.fadedbytes.theotherside.world;

import com.fadedbytes.theotherside.TheOtherside;
import org.bukkit.Bukkit;
import org.bukkit.GameRule;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.block.Biome;
import org.bukkit.generator.*;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.fadedbytes.theotherside.TheOtherside.OVERWORLD_NAME;

public class TheOthersideWorld {
    private static final String WORLD_NAME = "DEV_the_otherside_011";


    private static World THE_OTHERSIDE;

    public static void generateWorld() {
        World overworld = Bukkit.getWorld(OVERWORLD_NAME);
        WorldCreator worldCreator = new WorldCreator(WORLD_NAME);

        if (overworld == null) {
            TheOtherside.PLUGIN.getLogger().severe("Could not find the overworld! The plugin will now be disabled.");
            Bukkit.getPluginManager().disablePlugin(TheOtherside.PLUGIN);
            return;
        }
        worldCreator.copy(overworld);

        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.generateStructures(false);

        worldCreator.generator(new TheOthersideChunkGenerator());
        worldCreator.biomeProvider(new BiomeProvider() {

            static final List<Biome> biomes = new ArrayList<>();

            static {
                biomes.add(Biome.THE_END);
            }

            @NotNull
            @Override
            public Biome getBiome(@NotNull WorldInfo worldInfo, int x, int y, int z) {
                return biomes.get(0);
            }

            @NotNull
            @Override
            public List<Biome> getBiomes(@NotNull WorldInfo worldInfo) {
                return biomes;
            }
        });

        worldCreator.createWorld();
        THE_OTHERSIDE = Bukkit.getWorld(WORLD_NAME);

        // Set gamerules
        THE_OTHERSIDE.setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        THE_OTHERSIDE.setGameRule(GameRule.DISABLE_RAIDS, true);
        THE_OTHERSIDE.setGameRule(GameRule.DO_PATROL_SPAWNING, false);
        THE_OTHERSIDE.setGameRule(GameRule.DO_INSOMNIA, false);
        THE_OTHERSIDE.setGameRule(GameRule.DO_TRADER_SPAWNING, false);
        THE_OTHERSIDE.setGameRule(GameRule.NATURAL_REGENERATION, false);
        THE_OTHERSIDE.setGameRule(GameRule.SPECTATORS_GENERATE_CHUNKS, false);
        THE_OTHERSIDE.setGameRule(GameRule.DO_WEATHER_CYCLE, false);

        // Set time
        THE_OTHERSIDE.setTime(0);

        THE_OTHERSIDE.getPopulators().add(new BlockPopulator() {
            @Override
            public void populate(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull LimitedRegion limitedRegion) {

            }
        });

    }

    public static World getWorld() {
        return THE_OTHERSIDE;
    }
}
