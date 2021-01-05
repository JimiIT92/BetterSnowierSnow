package com.bettersnowiersnow;

import com.bettersnowiersnow.config.Settings;
import com.bettersnowiersnow.event.SnowFallingEvent;
import com.bettersnowiersnow.event.SnowMeltEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Make snow act more realistically!
 * Inspired by the Snowier Snow plugin by hobblyhobo
 */
public final class BetterSnowierSnow extends JavaPlugin {

    /**
     * Plugin Instance
     */
    private static BetterSnowierSnow instance;

    /**
     * Enable the plugin
     */
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        Settings.load();
        this.registerEvents();
    }

    /**
     * Disable the plugin
     */
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    /**
     * Register Plugin Events
     */
    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();
        if(Settings.snowGravity) {
            pluginManager.registerEvents(new SnowFallingEvent(), this);
        }
        pluginManager.registerEvents(new SnowMeltEvent(), this);
    }

    /**
     * Get the Plugin Instance
     *
     * @return Plugin Instance
     */
    public static BetterSnowierSnow getInstance() {
        return instance;
    }
}
