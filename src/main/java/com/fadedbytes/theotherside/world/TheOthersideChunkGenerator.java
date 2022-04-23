package com.fadedbytes.theotherside.world;

import org.bukkit.Material;
import org.bukkit.block.data.BlockData;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class TheOthersideChunkGenerator extends ChunkGenerator {
    @Override
    public boolean shouldGenerateNoise() {
        return true;
    }

    @Override
    public boolean shouldGenerateSurface() {
        return false;
    }

    @Override
    public boolean shouldGenerateBedrock() {
        return true;
    }

    @Override
    public boolean shouldGenerateCaves() {
        return false;
    }

    @Override
    public boolean shouldGenerateDecorations() {
        return false;
    }

    @Override
    public boolean shouldGenerateMobs() {
        return false;
    }

    @Override
    public boolean shouldGenerateStructures() {
        return false;
    }

    @Override
    public void generateSurface(@NotNull WorldInfo worldInfo, @NotNull Random random, int x, int z, @NotNull ChunkGenerator.ChunkData chunkData) {
        for (int bx = 0; bx < 16; bx++) {
            for (int bz = 0; bz < 16; bz++) {

                int higherBlock = chunkData.getMaxHeight();
                Material currentBlock = chunkData.getBlockData(bx, higherBlock, bz).getMaterial();
                while (currentBlock == Material.AIR || currentBlock == Material.WATER || currentBlock == Material.LAVA) {
                    higherBlock--;
                    currentBlock = chunkData.getBlockData(bx, higherBlock, bz).getMaterial();
                }

                for (int by = chunkData.getMinHeight(); by <= higherBlock; by++) {
                    if (chunkData.getBlockData(bx, by, bz).getMaterial() != Material.AIR) {
                        if (by <= 50) {
                            chunkData.setBlock(bx, by, bz, Material.BLACKSTONE);
                        } else {
                            chunkData.setBlock(bx, by, bz, Material.DEEPSLATE);
                        }
                    }
                }

            }
        }

    }
}
