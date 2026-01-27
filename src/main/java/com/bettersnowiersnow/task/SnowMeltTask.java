package com.bettersnowiersnow.task;

import com.bettersnowiersnow.utils.Utilities;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Snow;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Pose Snow using the new Smooth Strategy
 *
 * @author JimiIT92
 */
public class SnowMeltTask implements Runnable {

    /**
     * Run the Task
     */
    @Override
    public void run() {
        Utilities.getLoadedChunks(false).forEach(chunk -> {
            if(Utilities.shouldMeltSnow()) {
                Set<Block> blocks = getRandomBlocksAtMinLevel(chunk);
                if(blocks != null && !blocks.isEmpty()) {
                    blocks.stream().filter(Objects::nonNull).forEach(block -> {
                        if (Utilities.shouldNotMeltSnowLayer(block)) {
                            return;
                        }
                        try {
                            Snow snow = Utilities.cast(block);
                            Utilities.decreaseSnowLayersFromMelting(block, snow, 1);
                        } catch (Exception ignored) { }
                    });
                }
            }
        });
    }

    /**
     * Get a random Snow Layer inside a chunk
     *
     * @param chunk Chunk
     * @return Chunk Minimum Snow Level
     */
    private Set<Block> getRandomBlocksAtMinLevel(Chunk chunk) {
        Set<Block> chunkBlocks = getValidChunkBlocks(chunk.getWorld(), chunk.getX(), chunk.getZ());
        return chunkBlocks.stream()
                .filter(block -> Utilities.canMoreLayersBeMelted(Utilities.cast(block)))
                .limit(1)
                .collect(Collectors.toSet());
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
                Block startingBlock = world.getHighestBlockAt(i + (x * 16), j + (z * 16));
                if(Utilities.isSnowLayer(startingBlock)) {
                    chunkBlocks.add(startingBlock);
                }
                Block highestBlock = Utilities.getRelativeBlock(startingBlock, BlockFace.UP);
                if(Utilities.isSnowLayer(highestBlock)) {
                    chunkBlocks.add(highestBlock);
                }

            }
        }
        return new HashSet<>(chunkBlocks);
    }
}
