package at.theduggy.edi;

import at.theduggy.edi.settings.GlobalEDIController;
import at.theduggy.edi.settings.SettingsCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static final HashMap<UUID, EDIManager> ediPlayerData = new HashMap<>();
    private static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "EDI" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE;

    @Override
    public void onEnable() {
        for (Player player:Bukkit.getOnlinePlayers()){
            ediPlayerData.put(player.getUniqueId(), new EDIManager(player));
        }
        Bukkit.getPluginManager().registerEvents(new GlobalEDIController(), this);
        getCommand("settings").setExecutor(new SettingsCommand());
    }

    @Override
    public void onDisable() {

    }

    public static EDIManager getEDIManager(UUID player) {
        return ediPlayerData.get(player);
    }

    public static HashMap<UUID, EDIManager> getEDIData() {
        return ediPlayerData;
    }

    public static String getPrefix() {
        return prefix;
    }
}
