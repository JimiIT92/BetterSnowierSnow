package com.bettersnowiersnow.event;

import com.bettersnowiersnow.utils.Utilities;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFadeEvent;

/**
 * Handle the decaying of snowy grass
 *
 * @author JimiIT92
 */
public class SnowyGrassDecayEvent implements Listener {

    /**
     * Prevent grass from decaying if is snowy
     *
     * @param event Block Fade Event
     */
    @EventHandler
    public void onGrassDecay(BlockFadeEvent event) {
        Block block = event.getBlock();
        event.setCancelled(Utilities.is(block, Material.GRASS_BLOCK) && Utilities.isSnowBlockOrLayer(Utilities.getRelativeBlock(block, BlockFace.UP)));
    }
}
