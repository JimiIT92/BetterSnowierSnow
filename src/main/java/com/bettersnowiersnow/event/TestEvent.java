package com.bettersnowiersnow.event;

import com.bettersnowiersnow.utils.Utilities;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

/**
 * @author JimiIT92
 */
public class TestEvent implements Listener {

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        event.getPlayer().sendMessage("valid? " + Utilities.isValidMaterial(event.getBlock()));
    }
}
