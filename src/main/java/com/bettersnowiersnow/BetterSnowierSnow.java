package com.bettersnowiersnow;

import com.bettersnowiersnow.command.ReloadCommand;
import com.bettersnowiersnow.config.Settings;
import com.bettersnowiersnow.event.*;
import com.bettersnowiersnow.utils.Utilities;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

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
        reloadConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        this.registerEvents();
        this.registerTasks();
        if(Settings.metrics) {
            new Metrics(this, 9912);
        }
        Objects.requireNonNull(this.getCommand("reload")).setExecutor(new ReloadCommand());
    }

    /**
     * Load the configuration
     */
    @Override
    public void reloadConfig() {
        super.reloadConfig();
        Settings.load();
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
        if(Settings.excludedChunks != null && !Settings.excludedChunks.isEmpty()) {
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
