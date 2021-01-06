package com.bettersnowiersnow;

import com.bettersnowiersnow.config.Settings;
import com.bettersnowiersnow.event.*;
import com.bettersnowiersnow.task.SnowPoseTask;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

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
        if(Settings.snowPoseTask != null) {
            Settings.snowPoseTask.cancel();
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
        BukkitScheduler scheduler = Bukkit.getScheduler();
        Settings.snowPoseTask = scheduler.runTaskTimer(this, new SnowPoseTask(), Settings.snowPoseFrequency, Settings.snowPoseFrequency);
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
