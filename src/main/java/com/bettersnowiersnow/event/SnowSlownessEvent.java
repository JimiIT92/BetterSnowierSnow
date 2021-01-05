package com.bettersnowiersnow.event;

import com.bettersnowiersnow.config.Settings;
import com.bettersnowiersnow.utils.Utilities;
import org.bukkit.GameMode;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.Snow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/**
 * Cause slowness to players walking on snow blocks or snow layers
 *
 * @author JimiIT92
 */
public class SnowSlownessEvent implements Listener {

    /**
     * Cause slowness to players walking on snow blocks or snow layers
     *
     * @param event Player Move Event
     */
    @EventHandler
    public void onPlayerWalkingOnSnow(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if(player.isFlying() || player.getGameMode().equals(GameMode.SPECTATOR) ||
                (Settings.slownessSneakingPrevent && player.isSneaking())) {
            return;
        }
        Block block = Utilities.getPlayerGroundBlock(player);
        boolean applySlowness = Utilities.isSnowBlock(block);
        if(!applySlowness) {
            Block upperBlock = Utilities.getRelativeBlock(block, BlockFace.UP);
            if(Utilities.isSnowLayer(upperBlock)) {
                Snow snow = Utilities.cast(upperBlock);
                applySlowness = snow.getLayers() >= Settings.slownessMinLayers;
            }
        }
        if(applySlowness) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, 60, Math.max(0, Settings.slownessStrength - 1), false, false, false));
        }
    }
}
