package me.shakeforprotein.configsplaceholders;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;

public class PapiClass extends PlaceholderExpansion {

    private ConfigsPlaceholders plugin;

    /**
     * Since we register the expansion inside our own plugin, we
     * can simply use this method here to get an instance of our
     * plugin.
     *
     * @param plugin The instance of our plugin.
     */
    public PapiClass(ConfigsPlaceholders plugin) {
        this.plugin = plugin;
    }

    /**
     * Because this is an internal class,
     * you must override this method to let PlaceholderAPI know to not unregister your expansion class when
     * PlaceholderAPI is reloaded
     *
     * @return true to persist through reloads
     */
    @Override
    public boolean persist() {
        return true;
    }

    /**
     * Because this is a internal class, this check is not needed
     * and we can simply return {@code true}
     *
     * @return Always true since it's an internal class.
     */
    @Override
    public boolean canRegister() {
        return true;
    }

    /**
     * The name of the person who created this expansion should go here.
     * <br>For convienience do we return the author from the plugin.yml
     *
     * @return The name of the author as a String.
     */
    @Override
    public String getAuthor() {
        return plugin.getDescription().getAuthors().toString();
    }

    /**
     * The placeholder identifier should go here.
     * <br>This is what tells PlaceholderAPI to call our onRequest
     * method to obtain a value if a placeholder starts with our
     * identifier.
     * <br>This must be unique and can not contain % or _
     *
     * @return The identifier in {@code %<identifier>_<value>%} as String.
     */
    @Override
    public String getIdentifier() {
        return "ConfigsPlaceholder";
    }

    /**
     * This is the version of the expansion.
     * <br>You don't have to use numbers, since it is set as a String.
     * <p>
     * For convienience do we return the version from the plugin.yml
     *
     * @return The version as a String.
     */
    @Override
    public String getVersion() {
        return plugin.getDescription().getVersion();
    }

    /**
     * This is the method called when a placeholder with our identifier
     * is found and needs a value.
     * <br>We specify the value identifier in this method.
     * <br>Since version 2.9.1 can you use OfflinePlayers in your requests.
     *
     * @param player     A {@link Player Player}.
     * @param identifier A String containing the identifier/value.
     * @return possibly-null String of the requested identifier.
     */
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        if (player == null) {
            return "";
        }

        for (String blockedWord : plugin.getConfig().getStringList("BlockedWords")) {
            if (identifier.toLowerCase().contains(blockedWord.toLowerCase())) {
                return ChatColor.translateAlternateColorCodes('&', "&cThis has been blocked for security reasons");
            }
        }
        if (identifier.toLowerCase().startsWith("$")) {
            identifier = identifier.replaceFirst("\\$", "");
            System.out.println(identifier);
            System.out.println(plugin.getConfig().getString("Shortcuts." + identifier));
            identifier = plugin.getConfig().getString("Shortcuts." + identifier);
        }
        if (identifier != null && identifier.split("\\|").length > 1) {
            File targetFile = new File(plugin.getDataFolder().getParent() + File.separator + identifier.split("\\|")[0], "config.yml");

            if (identifier.split("\\|").length == 3) {
                targetFile = new File(plugin.getDataFolder().getParent() + File.separator + identifier.split("\\|")[0], identifier.split("\\|")[1] + ".yml");
            }

            if (targetFile.exists() && identifier.split("\\|").length == 3) {
                YamlConfiguration targetYml = YamlConfiguration.loadConfiguration(targetFile);
                return targetYml.getString(identifier.split("\\|")[2]);
            } else if (targetFile.exists()) {
                YamlConfiguration targetYml = YamlConfiguration.loadConfiguration(targetFile);
                return targetYml.getString(identifier.split("\\|")[1]);
            } else {
                return "Could not find requested config.";
            }
        }
        return null;
    }
}
