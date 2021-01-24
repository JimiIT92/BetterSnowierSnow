package com.bettersnowiersnow.event;

import com.bettersnowiersnow.config.Settings;
import com.bettersnowiersnow.utils.Utilities;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.weather.WeatherChangeEvent;

/**
 * Check if Vanilla Snow Pose has to be cancelled
 *
 * @author JimiIT92
 */
public class SnowPoseEvent implements Listener {

    /**
     * Prevent Vanilla Snow Posing in excluded Chunks
     *
     * @param event Block Form Event
     */
    @EventHandler
    public void onSnowPose(BlockFormEvent event) {
        if(event.getNewState().getType() == Material.SNOW) {
            Chunk chunk = event.getBlock().getChunk();
            event.setCancelled(Utilities.isChunkExcludedForVanilla(chunk));
        }
    }

    /**
     * Run the snow pose task only if is snowing
     * and cancel it when it's clear weather
     *
     * @param event Weather Change Event
     */
    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event) {
        if(!event.isCancelled()) {
            String world = event.getWorld().getName();
            if(event.toWeatherState()) {
                Utilities.runSnowPoseTaskForWorld(world);
            } else {
                Utilities.cancelSnowPoseTaskForWorld(world);
            }
        }
    }

    /**
     * If a Player iss the first joining a world
     * than a snow pose task for that world will be fired
     * if is snowing and there isn't a snow pose task already running
     *
     * @param event Player Login Event
     */
    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent event) {
        World world = event.getPlayer().getWorld();
        if(Utilities.isValidWorld(world) && !Settings.snowPoseTasks.containsKey(world.getName())) {
            Utilities.runSnowPoseTaskForWorld(world.getName());
        }
    }

    /**
     * If there are no players left online
     * all snow pose tasks are cancelled
     *
     * @param event Player Quit Event
     */
    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        if(Bukkit.getOnlinePlayers().size() - 1 == 0) {
            Settings.snowPoseTasks.forEach((world, bukkitTask) -> Utilities.cancelSnowPoseTaskForWorld(world));
            Settings.snowPoseTasks.clear();
        }
    }
}
