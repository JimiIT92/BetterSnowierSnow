package com.bettersnowiersnow.utils;

import com.bettersnowiersnow.BetterSnowierSnow;
import com.bettersnowiersnow.config.ExcludedChunk;
import com.bettersnowiersnow.config.Settings;
import com.bettersnowiersnow.task.SnowPoseTask;
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
import org.bukkit.scheduler.BukkitTask;

import java.util.*;
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
     * Cold Biomes
     */
    private static final List<Biome> coldBiomes = Arrays.asList(
              Biome.DEEP_FROZEN_OCEAN
            , Biome.FROZEN_OCEAN
            , Biome.FROZEN_RIVER
            , Biome.ICE_SPIKES
            , Biome.SNOWY_BEACH
            , Biome.FROZEN_PEAKS
            , Biome.JAGGED_PEAKS
            , Biome.STONY_PEAKS
            , Biome.MEADOW
            , Biome.GROVE
            , Biome.SNOWY_SLOPES
            , Biome.SNOWY_TAIGA
    );

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
        return is(block, Material.SNOW_BLOCK) || is(block, Material.POWDER_SNOW);
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
        return material == Material.SNOW_BLOCK || material == Material.SNOW || material == Material.POWDER_SNOW;
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
     * Check if the Block is valid for a Snow Layer to fall
     *
     * @param block Block to check
     * @return True if is valid, False otherwise
     */
    public static boolean isValidMaterialForFalling(Block block) {
        return !block.getType().isAir() && !block.isLiquid();
    }

    /**
     * Check if a Block is a solid full Block
     *
     * @param block Block to check
     * @return True if is a solid full Block, False otherwise
     */
    public static boolean isSolidFullBlock(Block block) {
        Material material = block.getType();
        return !isInvalid(block) && (
                material.isOccluding() || is(block, Material.SCAFFOLDING) ||
                (material.isSolid() && (
                    isTopOrDoubleSlab(block)
                    || isTopStair(block)
                    || isTopTrapdoor(block)
                    || isFullBlock(block)
                )));
    }

    /**
     * Check if a Block is a Full Block
     *
     * @param block Block to Check
     * @return True if is a Full Block, False otherwise
     */
    private static boolean isFullBlock(Block block) {
        return block.getBoundingBox().getVolume() == 1.0D
                && (block.getType().isBlock() && !block.isLiquid() && !block.isPassable());
    }

    /**
     * Check if a Block is invalid for Snow Pose
     *
     * @param block Block to check
     * @return True if the block is invalid, False otherwise
     */
    private static boolean isInvalid(Block block) {
        return (isSlab(block) && ! isTopOrDoubleSlab(block))
                || is(block, Material.HOPPER)
                || is(block, Material.BARRIER)
                || is(block, Material.STRUCTURE_VOID)
                || isIce(block)
                || is(block, Material.CAULDRON)
                || (isStair(block) && !isTopStair(block))
                || (isTrapdoor(block) && !isTopTrapdoor(block))
                || isFence(block)
                || isGlassPane(block)
                || is(block, Material.IRON_BARS);
    }

    /**
     * Check if a Block is a Glass Pane
     *
     * @param block Block to check
     * @return True if is a Glass Pane, False otherwise
     */
    public static boolean isGlassPane(Block block) {
        return is(block, Material.GLASS_PANE)
                || is(block, Material.BLACK_STAINED_GLASS_PANE)
                || is(block, Material.RED_STAINED_GLASS_PANE)
                || is(block, Material.GREEN_STAINED_GLASS_PANE)
                || is(block, Material.YELLOW_STAINED_GLASS_PANE)
                || is(block, Material.BLUE_STAINED_GLASS_PANE)
                || is(block, Material.WHITE_STAINED_GLASS_PANE)
                || is(block, Material.PINK_STAINED_GLASS_PANE)
                || is(block, Material.ORANGE_STAINED_GLASS_PANE)
                || is(block, Material.GRAY_STAINED_GLASS_PANE)
                || is(block, Material.LIGHT_GRAY_STAINED_GLASS_PANE)
                || is(block, Material.LIGHT_BLUE_STAINED_GLASS_PANE)
                || is(block, Material.LIME_STAINED_GLASS_PANE)
                || is(block, Material.PURPLE_STAINED_GLASS_PANE)
                || is(block, Material.BROWN_STAINED_GLASS_PANE)
                || is(block, Material.MAGENTA_STAINED_GLASS_PANE)
                || is(block, Material.CYAN_STAINED_GLASS_PANE);
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
            return trapDoor.getHalf() == Bisected.Half.TOP && !trapDoor.isOpen();
        }
        return false;
    }

    /**
     * Check if a Block is a Fence
     *
     * @param block Block to check
     * @return True if the Block is a Fence, False otherwise
     */
    private static boolean isFence(Block block) {
        return Tag.FENCES.isTagged(block.getType()) || Tag.WOODEN_FENCES.isTagged(block.getType());
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
     * Check if the Block above the one provided
     * is valid for snow pose
     *
     * @param block Ground block
     * @return True if the block above is valid for snow pose, False otherwise
     */
    public static boolean isValidBlockAboveForPose(Block block) {
        Block blockAbove = getRelativeBlock(block, BlockFace.UP);
        return isValidMaterial(blockAbove) || isAir(blockAbove);
    }

    /**
     * Check if a Block is air
     *
     * @param block Block to check
     * @return True if the Block is air, False otherwise
     */
    public static boolean isAir(Block block) {
        return block.getType().isAir();
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
     */
    public static void createFallingBlock(Block block, BlockData blockData) {
        FallingBlock fallingBlock = block.getWorld().spawnFallingBlock(block.getLocation().add(0.5D, 0.0D, 0.5D), blockData);
        fallingBlock.setDropItem(true);
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
        return block.getTemperature() <= 0.15D || coldBiomes.contains(block.getBiome()) || isInHighMountain(block);
    }

    /**
     * Check if a Block is in High Mountain
     *
     * @param block Block to Check
     * @return True if is in High Mountain, False otherwise
     */
    public static boolean isInHighMountain(Block block) {
        Biome biome = block.getBiome();
        return (biome == Biome.FROZEN_PEAKS || biome == Biome.JAGGED_PEAKS
                || biome == Biome.STONY_PEAKS || biome == Biome.MEADOW
                || biome == Biome.GROVE || biome == Biome.SNOWY_SLOPES) && block.getY() >= 95;
    }

    /**
     * Check if a Block is an Ice Block
     *
     * @param block Block to check
     * @return True if is an Ice Block, False otherwise
     */
    public static boolean isIce(Block block) {
        return Tag.ICE.isTagged(block.getType()) ||
                is(block, Material.ICE) ||
                is(block, Material.PACKED_ICE) || is(block, Material.FROSTED_ICE);
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
     * @return The cast BlockData
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
     * Increase the Snow Layers by posing other ones
     *
     * @param block Block
     * @param snow Snow
     * @param amount How many layers to increase
     */
    public static void increaseSnowLayersFromPosing(Block block, Snow snow, int amount) {
        snow.setLayers(snow.getLayers() + amount);
        block.setBlockData(snow);
        Bukkit.getPluginManager().callEvent(new BlockFormEvent(block, block.getState()));
    }

    /**
     * Decrease the Snow Layers by melting some
     *
     * @param block Block
     * @param snow Snow
     * @param amount How many layers to decrease
     */
    public static void decreaseSnowLayersFromMelting(Block block, Snow snow, int amount) {
        if(canMoreLayersBeMelted(snow)) {
            int layers = snow.getLayers() - amount;
            if(layers == 0) {
                Logger.getAnonymousLogger().info("PLACING AIR");
                block.getWorld().setBlockData(block.getLocation(), Material.AIR.createBlockData());
            } else {
                snow.setLayers(layers);
                block.setBlockData(snow);
                Bukkit.getPluginManager().callEvent(new BlockFormEvent(block, block.getState()));
            }
        }
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
                .filter(chunk -> !Utilities.isChunkExcluded(chunk))
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
    public static boolean isValidWorld(World world) {
        return Settings.snowPoseWorlds.contains(world.getName())
                && world.getEnvironment() == World.Environment.NORMAL
                && world.hasStorm();
    }

    /**
     * Run a snow pose task for a World
     *
     * @param world World name
     */
    public static void runSnowPoseTaskForWorld(String world) {
        BukkitTask task = Bukkit.getScheduler().runTaskTimer(BetterSnowierSnow.getInstance(), new SnowPoseTask(), Settings.snowPoseFrequency, Settings.snowPoseFrequency);
        cancelSnowPoseTaskForWorld(world);
        Settings.snowPoseTasks.put(world, task);
    }

    /**
     * Cancel a snow pose task for a World
     *
     * @param world World name
     */
    public static void cancelSnowPoseTaskForWorld(String world) {
        BukkitTask task = Settings.snowPoseTasks.getOrDefault(world, null);
        if(task != null) {
            task.cancel();
            Bukkit.getScheduler().cancelTask(task.getTaskId());
        }
    }

    /**
     * Check if Snow could be posed
     *
     * @return True if snow could be posed, False otherwise
     */
    public static boolean shouldPoseSnow() {
        return RANDOM.nextFloat() <= Settings.snowChancePercentage;
    }

    /**
     * Check if Snow could be melted
     *
     * @return True if snow could be melted, False otherwise
     */
    public static boolean shouldMeltSnow() {
        return RANDOM.nextFloat() <= Settings.snowMeltPercentage;
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
     * Check if more layers can be naturally melted
     *
     * @param snow Snow
     * @return True if more layers can be melted, False otherwise
     */
    public static boolean canMoreLayersBeMelted(Snow snow) {
        return snow.getLayers() > Settings.snowMeltMinLayers;
    }

    /**
     * Check if a Chunk is excluded from Vanilla snow posing
     *
     * @param chunk Chunk to check
     * @return True if the Chunk is excluded, False otherwise
     */
    public static boolean isChunkExcludedForVanilla(Chunk chunk) {
        return Settings.excludedChunks.stream().filter(ExcludedChunk::preventVanilla).anyMatch(c -> c.isInExcludedChunk(chunk));
    }

    /**
     * Check if a Chunk is excluded from snow posing
     *
     * @param chunk Chunk to check
     * @return True if the Chunk is excluded, False otherwise
     */
    public static boolean isChunkExcluded(Chunk chunk) {
        return Settings.excludedChunks.stream().anyMatch(c -> c.isInExcludedChunk(chunk));
    }
}
