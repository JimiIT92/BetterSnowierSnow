package com.bettersnowiersnow.event;

import com.bettersnowiersnow.config.Settings;
import com.bettersnowiersnow.utils.Utilities;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

/**
 * Handle the melting of snow in cold biomes
 *
 * @author JimiIT92
 */
public class SnowMeltEvent implements Listener {

    /**
     * Prevent snow from melting in cold biomes
     *
     * @param event Block Fade Event
     */
    @EventHandler
    public void onSnowMelt(BlockFadeEvent event) {
        Block block = event.getBlock();
        if(Utilities.isSnowBlockOrLayer(block)) {
            event.setCancelled((Settings.noMeltInColdBiomes && Utilities.isInColdBiome(block)) || Utilities.isBelowMinimumLightLevel(block));
        }
    }
}
