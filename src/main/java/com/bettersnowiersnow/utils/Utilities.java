package com.bettersnowiersnow.utils;

import com.bettersnowiersnow.BetterSnowierSnow;
import com.bettersnowiersnow.config.Settings;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Plugin Utility functions
 *
 * @author JimiIT92
 */
public class Utilities {

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
     * Check if a Block is a Snow Layer
     *
     * @param block Block to check
     * @return True if is a Snow Layer, False otherwise
     */
    public static boolean isSnowLayer(Block block) {
        return isSnowLayer(block.getType());
    }

    /**
     * Check if a Material is a Snow Layer
     *
     * @param material Material to check
     * @return True if is a Snow Layer, False otherwise
     */
    public static boolean isSnowLayer(Material material) {
        return material == Material.SNOW;
    }

    /**
     * Check if a Block is a Snow Block
     *
     * @param block Block to check
     * @return True if is a Snow Block, False otherwise
     */
    public static boolean isSnowBlock(Block block) {
        return isSnowBlock(block.getType());
    }

    /**
     * Check if a Material is a Snow Block
     *
     * @param material Material to check
     * @return True if is a Snow Block, False otherwise
     */
    public static boolean isSnowBlock(Material material) {
        return material == Material.SNOW_BLOCK;
    }

    /**
     * Check if a Block is either a Snow Layer or a Snow Block
     *
     * @param block Block to check
     * @return True if is a Snow Layer or a Snow Block, False otherwise
     */
    public static boolean isSnowBlockOrLayer(Block block) {
        return isSnowBlockOrLayer(block.getType());
    }

    /**
     * Check if a Material is either a Snow Layer or a Snow Block
     *
     * @param material Material to check
     * @return True if is a Snow Layer or a Snow Block, False otherwise
     */
    public static boolean isSnowBlockOrLayer(Material material) {
        return isSnowBlock(material) || isSnowLayer(material);
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
        return isValidMaterialForFalling(block.getType());
    }

    /**
     * Check if the Material is valid for a Snow Layer to fall
     *
     * @param material Material to check
     * @return True if is valid, False otherwise
     */
    public static boolean isValidMaterialForFalling(Material material) {
        return !material.isAir() && material != Material.WATER && material != Material.LAVA;
    }

    /**
     * Check if the Block is valid for a snow layer to pose
     *
     * @param block Block to check
     * @return True if is valid, False otherwise
     */
    public static boolean isValidMaterial(Block block) {
        return isValidMaterial(block.getType());
    }

    /**
     * Check if the Material is valid for a snow layer to pose
     *
     * @param material Material to check
     * @return True if is valid, False otherwise
     */
    public static boolean isValidMaterial(Material material) {
        return isSnowBlockOrLayer(material) ||
                (isValidMaterialForFalling(material)
                && material.isBlock()
                && material.isSolid());
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
}
