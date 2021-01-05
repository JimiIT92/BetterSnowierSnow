package com.bettersnowiersnow.utils;

import com.bettersnowiersnow.BetterSnowierSnow;
import com.bettersnowiersnow.config.Settings;
import org.bukkit.*;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Slab;
import org.bukkit.block.data.type.Snow;
import org.bukkit.block.data.type.Stairs;
import org.bukkit.block.data.type.TrapDoor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Plugin Utility functions
 *
 * @author JimiIT92
 */
public class Utilities {

    /**
     * Random Instance
     */
    public static final Random RANDOM = new Random(System.currentTimeMillis());
    /**
     * Plugin Instance
     */
    private static final BetterSnowierSnow PLUGIN = BetterSnowierSnow.getInstance();
    /**
     * Scheduler Instance
     */
    private static final BukkitScheduler SCHEDULER = Bukkit.getScheduler();
    /**
     * Logger Instance
     */
    private static final Logger LOGGER = PLUGIN.getLogger();
    /**
     * Cold Biomes
     */
    private static final List<Biome> coldBiomes = Arrays.asList(
            Biome.COLD_OCEAN
            , Biome.DEEP_FROZEN_OCEAN
            , Biome.DEEP_COLD_OCEAN
            , Biome.FROZEN_OCEAN
            , Biome.FROZEN_RIVER
            , Biome.ICE_SPIKES
            , Biome.SNOWY_BEACH
            , Biome.SNOWY_MOUNTAINS
            , Biome.MOUNTAINS
            , Biome.MOUNTAIN_EDGE
            , Biome.SNOWY_TAIGA
            , Biome.SNOWY_TAIGA_HILLS
            , Biome.SNOWY_TAIGA_MOUNTAINS
            , Biome.SNOWY_TUNDRA
    );

    /**
     * Log a message with Warning level
     *
     * @param message Message
     */
    public static void log(String message) {
        log(Level.WARNING, message);
    }

    /**
     * Log a message
     *
     * @param level Log Level
     * @param message Message
     */
    public static void log(Level level, String message) {
        LOGGER.log(level, message);
    }

    /**
     * Check if a Block is of a given Material
     *
     * @param block Block to check
     * @param material Material to check against
     * @return True if the block is of the given Material, False otherwise
     */
    public static boolean is(Block block, Material material) {
        return block.getType() == material;
    }

    /**
     * Check if a Block is Air
     *
     * @param block Block to check
     * @return True if the Block is Air, False otherwise
     */
    public static boolean isAir(Block block) {
        return block.getType().isAir();
    }

    /**
     * Check if a Block is a Snow Layer
     *
     * @param block Block to check
     * @return True if is a Snow Layer, False otherwise
     */
    public static boolean isSnowLayer(Block block) {
        return is(block, Material.SNOW);
    }

    /**
     * Check if a Block is a Snow Block
     *
     * @param block Block to check
     * @return True if is a Snow Block, False otherwise
     */
    public static boolean isSnowBlock(Block block) {
        return is(block, Material.SNOW_BLOCK);
    }

    /**
     * Check if a Block is either a Snow Layer or a Snow Block
     *
     * @param block Block to check
     * @return True if is a Snow Layer or a Snow Block, False otherwise
     */
    public static boolean isSnowBlockOrLayer(Block block) {
        return isSnowBlock(block) || isSnowLayer(block);
    }

    /**
     * Check if a Material is either a Snow Layer or a Snow Block
     *
     * @param material Material to check
     * @return True if is a Snow Layer or a Snow Block, False otherwise
     */
    public static boolean isSnowBlockOrLayer(Material material) {
        return material == Material.SNOW_BLOCK || material == Material.SNOW;
    }

    /**
     * Get the relative Block in the specified direction
     *
     * @param block Block
     * @param face Direction
     * @return Relative Block
     */
    public static Block getRelativeBlock(Block block, BlockFace face) {
        return block.getRelative(face);
    }

    /**
     * Get the relative Block type in the specified direction
     *
     * @param block Block
     * @param face Direction
     * @return Relative Block Type
     */
    public static Material getRelativeBlockType(Block block, BlockFace face) {
        return getRelativeBlock(block, face).getType();
    }

    /**
     * Check if the Block below is valid for a snow layer to pose
     *
     * @param block Block to check
     * @return True if the Block below is valid, False otherwise
     */
    public static boolean isValidBlockBelow(Block block) {
        return isValidMaterial(getRelativeBlock(block, BlockFace.DOWN));
    }

    /**
     * Check if the Block below is valid for a snow layer to fall
     *
     * @param block Block to check
     * @return True if the Block below is valid, False otherwise
     */
    public static boolean isValidBlockBelowForFalling(Block block) {
        return isValidMaterialForFalling(getRelativeBlock(block, BlockFace.DOWN));
    }

    /**
     * Check if the Block is valid for a Snow Layer to fall
     *
     * @param block Block to check
     * @return True if is valid, False otherwise
     */
    public static boolean isValidMaterialForFalling(Block block) {
        return !block.getType().isAir() && !block.isLiquid() && !Tag.ICE.isTagged(block.getType()) && !is(block, Material.CAULDRON);
    }

    /**
     * Check if a Block is a solid full Block
     *
     * @param block Block to check
     * @return True if is a solid full Block, False otherwise
     */
    public static boolean isSolidFullBlock(Block block) {
        Material material = block.getType();
        return material.isOccluding() || is(block, Material.SCAFFOLDING) ||
                (material.isSolid() && (
                    isTopOrDoubleSlab(block)
                    || isTopStair(block)
                    || isTopTrapdoor(block)
                    || (block.getBoundingBox().getVolume() == 1.0D && !isSlab(block)
                            && !is(block, Material.HOPPER)
                            && !isStair(block) && !isTrapdoor(block)
                            && (material.isBlock() && !block.isLiquid() && !block.isPassable())
                    )
                ));
    }

    /**
     * Check if the Block is a Top or Double Slab
     *
     * @param block Block to check
     * @return True if is a Top or Double Slab, False otherwise
     */
    private static boolean isTopOrDoubleSlab(Block block) {
        if(isSlab(block)) {
            Slab slab = cast(block);
            return slab.getType() == Slab.Type.TOP || slab.getType() == Slab.Type.DOUBLE;
        }
        return false;
    }

    /**
     * Check if a Block is a Top Stair
     *
     * @param block Block to check
     * @return True if is a Top Stair, False otherwise
     */
    private static boolean isTopStair(Block block) {
        if(isStair(block)) {
            Stairs stairs = cast(block);
            return stairs.getHalf() ==Bisected.Half.TOP;
        }
        return false;
    }

    /**
     * Check if a Block is a Top Trapdoor
     *
     * @param block Block to check
     * @return True if is a Top Trapdoor, False otherwise
     */
    private static boolean isTopTrapdoor(Block block) {
        if(isTrapdoor(block)) {
            TrapDoor trapDoor = cast(block);
            return trapDoor.getHalf() == Bisected.Half.TOP;
        }
        return false;
    }

    /**
     * Check if a Block is a Slab
     *
     * @param block Block to check
     * @return True if the Block is a Slab, False otherwise
     */
    private static boolean isSlab(Block block) {
        return Tag.SLABS.isTagged(block.getType()) || Tag.WOODEN_SLABS.isTagged(block.getType());
    }

    /**
     * Check if a Block is a Stair
     *
     * @param block Block to check
     * @return True if the Block is a Stair, False otherwise
     */
    private static boolean isStair(Block block) {
        return Tag.STAIRS.isTagged(block.getType()) || Tag.WOODEN_STAIRS.isTagged(block.getType());
    }

    /**
     * Check if a Block is a Trapdoor
     *
     * @param block Block to check
     * @return True if the Block is a Trapdoor, False otherwise
     */
    private static boolean isTrapdoor(Block block) {
        return Tag.TRAPDOORS.isTagged(block.getType()) || Tag.WOODEN_TRAPDOORS.isTagged(block.getType());
    }

    /**
     * Check if the Block is valid for a snow layer to pose
     *
     * @param block Block to check
     * @return True if is valid, False otherwise
     */
    public static boolean isValidMaterial(Block block) {
        return isSnowBlockOrLayer(block) || (isValidMaterialForFalling(block) && isSolidFullBlock(block));
    }

    /**
     * Spawn a Falling Block
     *
     * @param block Block
     */
    public static void spawnFallingSnow(Block block) {
        BlockData blockData = block.getBlockData().clone();
        SCHEDULER.runTaskLater(
                PLUGIN, () -> {
                    block.setType(Material.AIR);
                    createFallingBlock(block, blockData);
                }, 2L
        );
    }

    /**
     * Create a Falling Block
     *
     * @param block Block
     * @param blockData Block Data
     * @return Falling Block
     */
    public static FallingBlock createFallingBlock(Block block, BlockData blockData) {
        FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation().add(0.5D, 0.0D, 0.5D), blockData);
        fallingBlock.setDropItem(true);
        return fallingBlock;
    }

    /**
     * Drop an ItemStack at the Entity location
     *
     * @param entity Entity
     * @param item Item
     * @param quantity Quantity
     */
    public static void dropItemAtEntity(Entity entity, Material item, int quantity) {
        dropItemAtEntity(entity, new ItemStack(item, quantity));
    }

    /**
     * Drop an ItemStack at the Entity location
     *
     * @param entity Entity
     * @param itemStack ItemStack to drop
     */
    public static void dropItemAtEntity(Entity entity, ItemStack itemStack) {
        entity.getWorld().dropItem(entity.getLocation(), itemStack);
    }

    /**
     * Check if a Block is in a Cold Biome
     *
     * @param block Block
     * @return True if the Block is in a Cold Biome, False otherwise
     */
    public static boolean isInColdBiome(Block block) {
        return block.getTemperature() <= 0.15D || coldBiomes.contains(block.getBiome());
    }

    /**
     * Check if the Block Light Level is below the
     * minimum Light Level set for snow to melt
     *
     * @param block Block to check
     * @return True if the Light Level is below the minimum Light Level set, False otherwise
     */
    public static boolean isBelowMinimumLightLevel(Block block) {
        return block.getLightFromBlocks() <= Settings.meltAboveLightLevel;
    }

    /**
     * Cast a Block into a specific BlockData type
     *
     * @param block Block
     * @param <T> BlockData Type
     * @return Casted BlockData
     */
    @SuppressWarnings("unchecked")
    public static <T> T cast(Block block) {
        return (T) block.getBlockData();
    }

    /**
     * Increase the snow layers by the specified amount
     * at the given location
     *
     * @param location Location
     * @param amount Amount
     */
    public static void increaseSnowLayersAt(Location location, int amount) {
        Block block = location.getBlock();
        if(isSnowLayer(block)) {
            Snow snow = cast(block);
            int layers = snow.getLayers() + amount;
            snow.setLayers(Math.min(snow.getMaximumLayers(), layers));
            block.setBlockData(snow);
            if(snow.getLayers() >= snow.getMaximumLayers()) {
                block.setType(Material.SNOW_BLOCK);
                int leftoverLayers = layers - snow.getMaximumLayers();
                Block upperBlock = getRelativeBlock(block, BlockFace.UP);
                if(leftoverLayers > 0) {
                    upperBlock.setType(Material.SNOW);
                    Snow leftoverSnow = cast(upperBlock);
                    leftoverSnow.setLayers(Math.min(snow.getMaximumLayers(), leftoverLayers));
                    upperBlock.setBlockData(leftoverSnow);
                }
            }
        }
    }

    /**
     * Increase the Snow Layers by posing another one
     *
     * @param block Block
     * @param snow Snow
     */
    public static void increaseSnowLayersFromPosing(Block block, Snow snow) {
        snow.setLayers(snow.getLayers() + 1);
        block.setBlockData(snow);
        Bukkit.getPluginManager().callEvent(new BlockFormEvent(block, block.getState()));
    }

    /**
     * Get the Block the Player is standing on
     *
     * @param player Player
     * @return Player ground Block
     */
    public static Block getPlayerGroundBlock(Player player) {
        return getRelativeBlock(player.getLocation().getBlock(), BlockFace.DOWN);
    }

    /**
     * Get the Loaded Chunks for snow pose
     *
     * @return Loaded Chunks
     */
    public static Set<Chunk> getLoadedChunks() {
        final int viewDistance = Math.min(Bukkit.getViewDistance(), 7);
        Set<Chunk> chunkSet = new HashSet<>();
        Bukkit.getOnlinePlayers().stream()
                .filter(player -> isValidWorld(player.getWorld()))
                .map(player -> player.getLocation().getChunk())
                .forEach(chunk -> {
                    int chunkX = chunk.getX();
                    int chunkZ = chunk.getZ();
                    for (int x = chunkX - viewDistance; x <= chunkX + viewDistance; x++) {
                        for (int z = chunkZ - viewDistance; z <= chunkZ + viewDistance; z++) {
                            chunkSet.add(chunk.getWorld().getChunkAt(x, z));
                        }
                    }
                });
        return chunkSet.stream().filter(Chunk::isLoaded).collect(Collectors.toSet());
    }

    /**
     * Check if the World is valid for snow pose strategies
     *
     * @param world World
     * @return True if the World is valid for snow pose strategies, False otherwise
     */
    private static boolean isValidWorld(World world) {
        return Settings.snowPoseWorlds.contains(world.getName())
                && world.getEnvironment() == World.Environment.NORMAL
                && world.hasStorm();
    }

    /**
     * Check if Snow could be posed
     *
     * @return True if snow could be pose, False otherwise
     */
    public static boolean shouldPoseSnow() {
        return RANDOM.nextFloat() <= Settings.snowChancePercentage;
    }

    /**
     * Check if a Block is a valid for Snow Layer being posed
     *
     * @param block Block to check
     * @return True if is a valid for Snow Layer being posed, False otherwise
     */
    public static boolean isValidBlockForPose(Block block) {
        boolean isSnowLayer = isInColdBiome(block) && isBelowMinimumLightLevel(block) && is(block, Material.SNOW);
        if(isSnowLayer) {
            Snow snow = cast(block);
            return canMoreLayersBePlaced(snow);
        }
        return isAir(block);
    }

    /**
     * Check if more layers can be naturally placed
     *
     * @param snow Snow
     * @return True if more layers can be placed, False otherwise
     */
    public static boolean canMoreLayersBePlaced(Snow snow) {
        return snow.getLayers() < Settings.snowPoseMaxLayers;
    }

    /**
     * Get a Random Block in a Chunk
     *
     * @param chunk Chunk
     * @return Random Block
     */
    public static Block getRandomBlockInChunk(Chunk chunk) {
        int chunkX = chunk.getX();
        int chunkZ = chunk.getZ();
        int blockX = randomRange(chunkX * 16, chunkX * 16 + 16);
        int blockZ = randomRange(chunkZ * 16, chunkZ * 16 + 16);
        Block highestBlock = chunk.getWorld().getHighestBlockAt(blockX, blockZ);
        return Utilities.getRelativeBlock(highestBlock, BlockFace.UP);
    }

    /**
     * Get a random number from a range
     *
     * @param start Range start
     * @param end Range end
     * @return Random number
     */
    private static int randomRange(int start, int end) {
        return start + (int) Math.floor((RANDOM.nextFloat() * (end - start)));
    }
}
