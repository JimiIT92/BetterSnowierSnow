package com.bettersnowiersnow.event;

import com.bettersnowiersnow.utils.Utilities;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.FallingBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.entity.EntityDropItemEvent;

/**
 * Handle the falling of snow
 * when the block below is broken
 *
 * @author JimiIT92
 */
public class SnowFallingEvent implements Listener {

    /**
     * Make snow fall if the block below is broken
     *
     * @param event Block Physics Event
     */
    @EventHandler
    public void onSnowFalling(BlockPhysicsEvent event) {
        Block block = event.getBlock();
        if(Utilities.isSnowLayer(block)) {
            Snow snowLayer = Utilities.cast(block);
            if(snowLayer.getLayers() == snowLayer.getMaximumLayers()) {
                block.setType(Material.SNOW_BLOCK);
            }
        }

        if(Utilities.isSnowBlockOrLayer(block)) {
            if(!Utilities.isValidBlockBelowForFalling(block)) {
                Utilities.spawnFallingSnow(block);
            }
        }
    }

    /**
     * Handle drops for the falling snow blocks
     *
     * @param event Entity Drop Item Event
     */
    @EventHandler
    public void onFallingSnowDrop(EntityDropItemEvent event) {
        Entity entity = event.getEntity();
        if(entity instanceof FallingBlock) {
            FallingBlock fallingBlock = (FallingBlock) entity;
            BlockData fallingBlockData = fallingBlock.getBlockData();
            Material material = fallingBlockData.getMaterial();
            if(Utilities.isSnowBlock(material)) {
                Utilities.dropItemAtEntity(fallingBlock, Material.SNOWBALL, 2);
            }
            if(Utilities.isSnowLayer(material)) {
                Snow snow = (Snow) fallingBlockData;
                Utilities.increaseSnowLayersAt(fallingBlock.getLocation(), snow.getLayers());
            }
            event.setCancelled(Utilities.isSnowBlockOrLayer(material));
        }
    }
}
