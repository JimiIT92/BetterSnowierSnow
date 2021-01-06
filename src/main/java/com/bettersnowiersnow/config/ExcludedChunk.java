package com.bettersnowiersnow.config;

import org.bukkit.Chunk;

/**
 * Chunk region where Snow won't be posed
 *
 * @author JimiIT92
 */
public class ExcludedChunk {

    /**
     * First Chunk X coordinate
     */
    public final int fromX;
    /**
     * First Chunk Z coordinate
     */
    public final int fromZ;
    /**
     * Second Chunk X coordinate
     */
    public final int toX;
    /**
     * Second Chunk Z coordinate
     */
    public final int toZ;
    /**
     * If vanilla snow pose will be cancelled too
     */
    public final boolean preventVanilla;

    /**
     * CConstructor. Set the Chunks values
     *
     * @param fromX First Chunk X coordinate
     * @param fromZ First Chunk Z coordinate
     * @param toX Second Chunk X coordinate
     * @param toZ Second Chunk Z coordinate
     * @param preventVanilla If vanilla snow pose will be cancelled too
     */
    public ExcludedChunk(int fromX, int fromZ, int toX, int toZ, boolean preventVanilla) {
        this.fromX = fromX;
        this.fromZ = fromZ;
        this.toX = toX;
        this.toZ = toZ;
        this.preventVanilla = preventVanilla;
    }

    /**
     * Check if a Chunk is
     *
     * @param chunk Chunk to check
     * @return True if the Chunk is in the excluded Chunks, False otherwise
     */
    public boolean isInExcludedChunk(Chunk chunk) {
        int x = chunk.getX();
        int z = chunk.getZ();
        return x >= Math.min(this.fromX, this.toX)
        && x <= Math.max(this.fromX, this.toX)
        && z >= Math.min(this.fromZ, this.toZ)
        && z <= Math.max(this.fromZ, this.toZ);
    }
}
