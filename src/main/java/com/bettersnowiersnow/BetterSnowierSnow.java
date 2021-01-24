package com.bettersnowiersnow;

import com.bettersnowiersnow.config.Settings;
import com.bettersnowiersnow.event.*;
import com.bettersnowiersnow.utils.Utilities;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
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
        this.registerTasks();
        if(Settings.metrics) {
            new Metrics(this, 9912);
        }
    }

    /**
     * Disable the plugin
     */
    @Override
    public void onDisable() {
        if(Settings.snowPoseTasks != null) {
            Settings.snowPoseTasks.values().forEach(task -> {
                Bukkit.getScheduler().cancelTask(task.getTaskId());
                task.cancel();
            });
        }
    }

    /**
     * Register Plugin Events
     */
    private void registerEvents() {
        PluginManager pluginManager = getServer().getPluginManager();
        if(Settings.snowGravity) {
            pluginManager.registerEvents(new SnowFallingEvent(), this);
        }
        if(Settings.noSnowyGrassDecay) {
            pluginManager.registerEvents(new SnowyGrassDecayEvent(), this);
        }
        if(Settings.noSnowyGrassSpread) {
            pluginManager.registerEvents(new SnowyGrassSpreadEvent(), this);
        }
        if(Settings.slownessOnSnow) {
            pluginManager.registerEvents(new SnowSlownessEvent(), this);
        }
        pluginManager.registerEvents(new SnowMeltEvent(), this);
        if(Settings.excludedChunks != null && Settings.excludedChunks.size() > 0) {
            pluginManager.registerEvents(new SnowPoseEvent(), this);
        }
    }

    /**
     * Register Plugin Tasks
     */
    private void registerTasks() {
        getServer().getWorlds().stream()
                .filter(world -> Utilities.getLoadedChunks().stream().anyMatch(chunk -> chunk.getWorld().getName().equalsIgnoreCase(world.getName())))
                .forEach(world -> Utilities.runSnowPoseTaskForWorld(world.getName()));
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
