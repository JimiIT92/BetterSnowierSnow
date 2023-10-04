package com.bettersnowiersnow.event;

import com.bettersnowiersnow.config.Settings;
import com.bettersnowiersnow.utils.Utilities;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.Snow;
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
        event.setCancelled(true);
        if(Utilities.isSnowBlockOrLayer(block)) {
            if ((Settings.noMeltInColdBiomes && Utilities.isInColdBiome(block)) || Utilities.isBelowMinimumLightLevel(block)) {
                return;
            }
            if(Utilities.shouldMeltSnow() && Utilities.isSnowLayer(block)) {
                try {
                    Snow snow = Utilities.cast(block);
                    Utilities.decreaseSnowLayersFromMelting(block, snow, 1);
                } catch (Exception ignored) { }
            }
        }
    }
}
