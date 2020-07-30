package me.shakeforprotein.configsplaceholders;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class ConfigsPlaceholders extends JavaPlugin {

    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        this.getCommand("cpreload").setExecutor(new CommandReload(this));
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null){
            new PapiClass(this).register();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
