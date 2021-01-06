package com.bettersnowiersnow.event;

import com.bettersnowiersnow.utils.Utilities;
import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFormEvent;

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
}
