package com.bettersnowiersnow.task;

import com.bettersnowiersnow.utils.Utilities;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Snow;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Pose Snow using the new Smooth Strategy
 *
 * @author JimiIT92
 */
public class SnowPoseTask implements Runnable {

    /**
     * Run the Task
     */
    @Override
    public void run() {
        Set<Chunk> loadedChunks = Utilities.getLoadedChunks();
        loadedChunks.forEach(chunk -> {
            if(Utilities.shouldPoseSnow()) {
                Block block = getRandomBlockAtMinLevel(chunk);
                if(block != null) {
                    Snow snow;
                    if (!Utilities.isSnowLayer(block)) {
                        block = Utilities.getRelativeBlock(block, BlockFace.UP);
                        block.setType(Material.SNOW);
                    }
                    snow = Utilities.cast(block);
                    Utilities.increaseSnowLayersFromPosing(block, snow);
                }
            }
        });
    }

    /**
     * Get the Minimum Snow Level inside a Chunk
     *
     * @param chunk Chunk
     * @return Chunk Minimum Snow Level
     */
    private Block getRandomBlockAtMinLevel(Chunk chunk) {
        Set<Block> chunkBlocks = getValidChunkBlocks(chunk.getWorld(), chunk.getX(), chunk.getZ());
        int minLevel = chunkBlocks.stream().mapToInt(block -> {
            Block blockAbove = Utilities.getRelativeBlock(block, BlockFace.UP);
            if(Utilities.isSnowLayer(blockAbove)) {
                Snow snow = Utilities.cast(blockAbove);
                return snow.getLayers();
            }
            return 0;
        }).min().orElse(0);
        List<Block> blockPool;
        if(minLevel == 0) {
            blockPool = chunkBlocks.stream().filter(block -> !Utilities.isSnowLayer(block)).collect(Collectors.toList());
        } else {
            blockPool = chunkBlocks.stream().filter(Utilities::isSnowLayer).filter(block -> {
                Snow snow = Utilities.cast(block);
                return snow.getLayers() == minLevel - 1 && Utilities.canMoreLayersBePlaced(snow);
            }).collect(Collectors.toList());
        }
        if(blockPool.size() == 0) {
            return null;
        }
        Collections.shuffle(blockPool);
        return blockPool.stream().findFirst().orElse(null);
    }

    /**
     * Get all the valid blocks for snow posing inside a chunk
     *
     * @param world World
     * @param x Chunk X Coordinate
     * @param z Chunk Z Coordinate
     * @return Valid Blocks for snow posing
     */
    private Set<Block> getValidChunkBlocks(World world, int x, int z) {
        Set<Block> chunkBlocks = new HashSet<>();
        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 16; j++) {
                chunkBlocks.add(world.getHighestBlockAt(i + (x * 16), j + (z * 16)));
            }
        }
        return chunkBlocks.stream().filter(Utilities::isValidMaterial).collect(Collectors.toSet());
    }
}
