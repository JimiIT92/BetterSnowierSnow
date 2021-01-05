package com.bettersnowiersnow.event;

import com.bettersnowiersnow.utils.Utilities;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockSpreadEvent;

/**
 * Handle the spreading of snowy grass
 *
 * @author JimiIT92
 */
public class SnowyGrassSpreadEvent implements Listener {

    /**
     * Prevent grass from spreading if is snowy
     *
     * @param event Block Spread Event
     */
    @EventHandler
    public void onGrassSpread(BlockSpreadEvent event) {
        Block block = event.getSource();
        event.setCancelled(Utilities.is(block, Material.GRASS_BLOCK) && Utilities.isSnowBlockOrLayer(Utilities.getRelativeBlock(block, BlockFace.UP)));
    }
}
