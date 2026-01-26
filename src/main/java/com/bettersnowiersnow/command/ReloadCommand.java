package com.bettersnowiersnow.command;

import com.bettersnowiersnow.BetterSnowierSnow;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Reload the configuration
 */
public class ReloadCommand implements CommandExecutor {

    /**
     * Reload the config file
     *
     * @param commandSender {@link CommandExecutor The issuer of the Command}
     * @param command {@link Command The command to run}
     * @param label {@link String The command label}
     * @param args {@link String The command arguments}
     * @return {@link Boolean True}
     */
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String label, String[] args) {
        BetterSnowierSnow.getInstance().reloadConfig();
        return true;
    }
}
