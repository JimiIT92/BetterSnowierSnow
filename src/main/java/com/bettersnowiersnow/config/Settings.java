package com.bettersnowiersnow.config;

import com.bettersnowiersnow.BetterSnowierSnow;
import org.bukkit.configuration.file.FileConfiguration;

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
     * Load configuration
     */
    public static void load() {
        snowGravity = getBoolean("snowGravity");
        noMeltInColdBiomes = getBoolean("noMeltInColdBiomes");
        meltAboveLightLevel = getInt("meltAboveLightLevel");
        noSnowyGrassDecay = getBoolean("noSnowyGrassDecay");
        noSnowyGrassSpread = getBoolean("noSnowyGrassSpread");
        slownessOnSnow = getBoolean("slownessOnSnow");
        slownessMinLayers = getInt("slownessMinLayers");
        slownessStrength = getInt("slownessStrength");
        slownessSneakingPrevent = getBoolean("slownessSneakingPrevent");
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
}
