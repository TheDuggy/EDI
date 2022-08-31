package at.theduggy.edi;

import at.theduggy.edi.settings.SettingsCommand;
import at.theduggy.edi.storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "" + ChatColor.BOLD + "EDI" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE;
    public static String logo = ChatColor.DARK_GRAY + "└" + ChatColor.GOLD + "" + ChatColor.BOLD + "EDInfo" + ChatColor.DARK_GRAY + "┘";
    private static StorageManager storageManager;
    private static HashMap<UUID, EDIManager> ediPlayerData = new HashMap<>();

    @Override
    public void onEnable() {
        this.saveConfig();
        try {
            storageManager = new StorageManager();
            Bukkit.getPluginManager().registerEvents(storageManager, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (Player player:Bukkit.getOnlinePlayers()){
            if (!ediPlayerData.containsKey(player.getUniqueId())){
                storageManager.registerUser(player);
            }else {
                ediPlayerData.get(player.getUniqueId()).setPlayer(player);
            }
        }
        Bukkit.getPluginManager().registerEvents(new GlobalEDIController(), this);
        getCommand("settings").setExecutor(new SettingsCommand());
    }

    @Override
    public void onDisable() {

        for (EDIManager ediManager:ediPlayerData.values()){
            Inventory currentInv = ediManager.getPlayer().getOpenInventory().getTopInventory();
            if (ediManager.getOptionManager().compareSettingsInv(currentInv)||ediManager.getOptionManager().compareDeepOptionIv(currentInv)){
                ediManager.getPlayer().closeInventory();
            }
            ediManager.getRenderManager().getScoreboardRenderer().unregister();
            ediManager.getRenderManager().getFooterRenderer().reset();
            ediManager.getRenderManager().getHeaderRenderer().reset();
        }
        try {
            storageManager.save();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getPrefix() {
        return prefix;
    }

    public static StorageManager getStorageManager() {
        return storageManager;
    }

    public static HashMap<UUID, EDIManager> getEdiPlayerData() {
        return ediPlayerData;
    }
}
