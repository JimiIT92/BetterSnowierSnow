package com.bettersnowiersnow.config;

import com.bettersnowiersnow.BetterSnowierSnow;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

/**
 * Plugin Settings
 *
 * @author JimiIT92
 */
public class Settings {

    /**
     * Config Instance
     */
    private static FileConfiguration CONFIG;
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
     * How often the plugin will try to melt the snow
     */
    public static int snowMeltFrequency;
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
     * The chance of snow to be melted
     */
    public static double snowMeltPercentage;
    /**
     * The minimum amount of snow layers that can be left from melting
     */
    public static int snowMeltMinLayers;
    /**
     * If metrics are allowed
     */
    public static boolean metrics;
    /**
     * Snow Pose Tasks
     */
    public static HashMap<String, BukkitTask> snowPoseTasks = new HashMap<>();
    /**
     * Snow Melt Tasks
     */
    public static HashMap<String, BukkitTask> snowMeltTasks = new HashMap<>();

    /**
     * Load configuration
     */
    public static void load() {
        CONFIG = BetterSnowierSnow.getInstance().getConfig();
        snowGravity = getBoolean("snowGravity", true);
        noMeltInColdBiomes = getBoolean("noMeltInColdBiomes", true);
        meltAboveLightLevel = Math.min(Math.max(0, getInt("meltAboveLightLevel", 10)), 15);
        noSnowyGrassDecay = getBoolean("noSnowyGrassDecay", true);
        noSnowyGrassSpread = getBoolean("noSnowyGrassSpread", true);
        snowChancePercentage = getDouble("snowChancePercentage", 0.0625D);
        snowPoseFrequency = getInt("snowPoseFrequency", 20);
        snowMeltFrequency = getInt("snowMeltFrequency", 20);
        snowPoseMaxLayers = Math.min(7, Math.max(0, getInt("snowPoseMaxLayers", 4)));
        snowPoseBlocks = Math.min(Math.max(0, getInt("snowPoseBlocks", 1)), 256);
        snowPoseWorlds = getStringList("snowPoseWorlds", Collections.singletonList("world"));
        excludedChunks = getExcludedChunks();
        slownessOnSnow = getBoolean("slownessOnSnow", true);
        slownessMinLayers = Math.min(7, Math.max(0, getInt("slownessMinLayers", 1)));
        slownessStrength = getInt("slownessStrength", 1);
        slownessSneakingPrevent = getBoolean("slownessSneakingPrevent", false);
        metrics = getBoolean("metrics", false);
        snowMeltPercentage = getDouble("snowMeltPercentage", 0.0625D);
        snowMeltMinLayers = Math.max(0, Math.min(7, getInt("snowMeltMinLayers", 1)));
    }

    /**
     * Get a boolean value from the Configuration
     *
     * @param key Config Key
     * @param defaultValue The default value if not found
     * @return Boolean Value
     */
    private static boolean getBoolean(String key, boolean defaultValue) {
        setDefault(key, defaultValue);
        return CONFIG.getBoolean(key);
    }

    /**
     * Get an integer value from the Configuration
     *
     * @param key Config Key
     * @param defaultValue The default value if not found
     * @return Integer Value
     */
    private static int getInt(String key, int defaultValue) {
        setDefault(key, defaultValue);
        return CONFIG.getInt(key);
    }

    /**
     * Get a double value from the Configuration
     *
     * @param key Config Key
     * @param defaultValue The default value if not found
     * @return Double Value
     */
    private static double getDouble(String key, double defaultValue) {
        setDefault(key, defaultValue);
        return CONFIG.getDouble(key);
    }

    /**
     * Set the default value of a configuration if not found
     *
     * @param key Config Key
     * @param defaultValue The default value if not found
     */
    private static void setDefault(String key, Object defaultValue) {
        if(!CONFIG.contains(key)) {
            CONFIG.set(key, defaultValue);
        }
    }

    /**
     * Get a string list value from the Configuration
     *
     * @param key Config Key
     * @param defaultValue The default value if not found
     * @return String value
     */
    private static List<String> getStringList(String key, List<String> defaultValue) {
        setDefault(key, defaultValue);
        return CONFIG.getStringList(key);
    }

    /**
     * Get the Set of Excluded Chunks from the Configuration
     *
     * @return Set of Excluded Chunks
     */
    private static Set<ExcludedChunk> getExcludedChunks() {
        excludedChunks = new HashSet<>();
        getStringList("snowPoseIgnoredChunks", Collections.singletonList("0,0,0,0,false")).forEach(chunk -> {
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