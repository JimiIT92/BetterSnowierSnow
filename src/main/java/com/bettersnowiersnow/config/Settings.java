package com.bettersnowiersnow.config;

import com.bettersnowiersnow.BetterSnowierSnow;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Plugin Settings
 *
 * @author JimiIT92
 */
public class Settings {

    /**
     * Config Instance
     */
    private static final FileConfiguration CONFIG = BetterSnowierSnow.getInstance().getConfig();
    /**
     * If snow layers and blocks will fall when
     * the Block below is broken
     */
    public static boolean snowGravity;
    /**
     * If snow won't melt in cold biomes
     */
    public static boolean noMeltInColdBiomes;
    /**
     * The minimum light level at which the snow will melt
     */
    public static int meltAboveLightLevel;
    /**
     * If grass won't decay if is Snowy
     */
    public static boolean noSnowyGrassDecay;
    /**
     * If grass won't spread if is Snowy
     */
    public static boolean noSnowyGrassSpread;
    /**
     * If Players will get the Slowness effect on snow blocks or snow layers
     */
    public static boolean slownessOnSnow;
    /**
     * Minimum amount of snow layers for which Players won't get
     * the Slowness effect (if active)
     */
    public static int slownessMinLayers;
    /**
     * Slowness Effect strength (if active)
     */
    public static int slownessStrength;
    /**
     * If the Slowness Effect (if active) won't be applied
     * if the Player is sneaking
     */
    public static boolean slownessSneakingPrevent;
    /**
     * The chance of snow to be posed
     */
    public static double snowChancePercentage;
    /**
     * How often the plugin will try to pose the snow
     */
    public static int snowPoseFrequency;
    /**
     * Worlds where snow will be posed by strategies
     */
    public static List<String> snowPoseWorlds;
    /**
     * The maximum amount of snow layers that can be posed from snowing
     */
    public static int snowPoseMaxLayers;
    /**
     * How many blocks will be posed per tick
     */
    public static int snowPoseBlocks;
    /**
     * Chunks where Snow won't pose
     */
    public static Set<ExcludedChunk> excludedChunks;
    /**
     * If metrics are allowed
     */
    public static boolean metrics;
    /**
     * Snow Pose Tasks
     */
    public static HashMap<String, BukkitTask> snowPoseTasks = new HashMap<>();

    /**
     * Load configuration
     */
    public static void load() {
        snowGravity = getBoolean("snowGravity");
        noMeltInColdBiomes = getBoolean("noMeltInColdBiomes");
        meltAboveLightLevel = Math.min(Math.max(0, getInt("meltAboveLightLevel")), 15);
        noSnowyGrassDecay = getBoolean("noSnowyGrassDecay");
        noSnowyGrassSpread = getBoolean("noSnowyGrassSpread");
        slownessOnSnow = getBoolean("slownessOnSnow");
        slownessMinLayers = Math.min(7, Math.max(0, getInt("slownessMinLayers")));
        slownessStrength = getInt("slownessStrength");
        slownessSneakingPrevent = getBoolean("slownessSneakingPrevent");
        snowChancePercentage = getDouble("snowChancePercentage");
        snowPoseFrequency = getInt("snowPoseFrequency");
        snowPoseWorlds = getStringList("snowPoseWorlds");
        snowPoseMaxLayers = Math.min(7, Math.max(0, getInt("snowPoseMaxLayers")));
        snowPoseBlocks = Math.min(Math.max(0, getInt("snowPoseBlocks")), 256);
        excludedChunks = getExcludedChunks();
        metrics = getBoolean("metrics");
    }

    /**
     * Get a boolean value from the Configuration
     *
     * @param key Config Key
     * @return Boolean Value
     */
    private static boolean getBoolean(String key) {
        return CONFIG.getBoolean(key);
    }

    /**
     * Get an integer value from the Configuration
     *
     * @param key Config Key
     * @return Integer Value
     */
    private static int getInt(String key) {
        return CONFIG.getInt(key);
    }

    /**
     * Get a double value from the Configuration
     *
     * @param key Config Key
     * @return Double Value
     */
    private static double getDouble(String key) {
        return CONFIG.getDouble(key);
    }

    /**
     * Get a string list value from the Configuration
     *
     * @param key Config Key
     * @return String value
     */
    private static List<String> getStringList(String key) {
        return CONFIG.getStringList(key);
    }

    /**
     * Get the List of Excluded Chunks from the Configuration
     *
     * @return List of Excluded Chunks
     */
    private static Set<ExcludedChunk> getExcludedChunks() {
        excludedChunks = new HashSet<>();
        getStringList("snowPoseIgnoredChunks").forEach(chunk -> {
            String[] splitChunk = chunk.split(",");
            int fromX = Integer.parseInt(splitChunk[0]);
            int fromZ = Integer.parseInt(splitChunk[1]);
            int toX = Integer.parseInt(splitChunk[2]);
            int toZ = Integer.parseInt(splitChunk[3]);
            boolean preventVanilla = Boolean.parseBoolean(splitChunk[4]);
            excludedChunks.add(new ExcludedChunk(fromX, fromZ, toX, toZ, preventVanilla));
        });
        return excludedChunks;
    }
}