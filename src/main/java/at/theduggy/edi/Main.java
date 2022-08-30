package at.theduggy.edi;

import at.theduggy.edi.settings.SettingsCommand;
import at.theduggy.edi.storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "" + ChatColor.BOLD + "EDI" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE;
    public static String logo = ChatColor.DARK_GRAY + "└" + ChatColor.GOLD + "" + ChatColor.BOLD + "EDInfo" + ChatColor.DARK_GRAY + "┘";

    @Override
    public void onEnable() {
        for (Player player:Bukkit.getOnlinePlayers()){
            StorageManager.getEDIData().put(player.getUniqueId(), new EDIManager(player));
        }
        Bukkit.getPluginManager().registerEvents(new GlobalEDIController(), this);
        getCommand("settings").setExecutor(new SettingsCommand());
    }

    @Override
    public void onDisable() {
        for (EDIManager ediManager:StorageManager.getEDIData().values()){
            Inventory currentInv = ediManager.getPlayer().getOpenInventory().getTopInventory();
            if (ediManager.getOptionManager().compareInv(currentInv)||ediManager.getOptionManager().compareDeepOptionIv(currentInv)){
                ediManager.getPlayer().closeInventory();
            }
            ediManager.getRenderManager().getScoreboardRenderer().unregister();
            ediManager.getRenderManager().getFooterRenderer().reset();
            ediManager.getRenderManager().getHeaderRenderer().reset();
        }
    }

    public static EDIManager getEDIManager(UUID player) {
        return StorageManager.getEDIData().get(player);
    }

    public static String getPrefix() {
        return prefix;
    }
}
