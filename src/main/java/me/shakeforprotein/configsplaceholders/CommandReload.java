package me.shakeforprotein.configsplaceholders;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandReload implements CommandExecutor {

    private ConfigsPlaceholders plugin;

    public CommandReload(ConfigsPlaceholders main){
        this.plugin = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String Label, String[] args) {

        return true;
    }
}
