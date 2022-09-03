package at.theduggy.edi;

import at.theduggy.edi.settings.OptionManager;
import at.theduggy.edi.settings.SettingsCommand;
import at.theduggy.edi.storage.StorageManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {
    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "" + ChatColor.BOLD + "EDI" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE;
    public static String logo = ChatColor.DARK_GRAY + "└" + ChatColor.GOLD + "" + ChatColor.BOLD + "EDInfo" + ChatColor.DARK_GRAY + "┘";
    private static StorageManager storageManager;
    private static HashMap<UUID, EDIManager> ediPlayerData = new HashMap<>();

    public ConfigManager configManager;

    @Override
    public void onEnable() {
        this.saveConfig();
        this.configManager = new ConfigManager(this.getConfig());
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
            if (ediManager.getPlayer()!=null){
                Inventory currentInv = ediManager.getPlayer().getOpenInventory().getTopInventory();
                Inventory settingsInv = ediManager.getOptionManager().getSettingsInvController().getInventory();
                Inventory optionSettingsInv = ediManager.getOptionManager().getOptionSettingsInvController().getInventory();
                Inventory keyFontInv = ediManager.getOptionManager().getKeyFontSettingsInvController().getInventory();
                Inventory separatorFontInv = ediManager.getOptionManager().getSeparatorFontSettingsInvController().getInventory();
                Inventory valueFontInv = ediManager.getOptionManager().getValueFontSettingsInvController().getInventory();
                //Check if ins are null
                if (currentInv.equals(settingsInv)||currentInv.equals(optionSettingsInv)||currentInv.equals(keyFontInv)||currentInv.equals(separatorFontInv)||currentInv.equals(valueFontInv)){
                    ediManager.getPlayer().closeInventory();
                }
                ediManager.getRenderManager().getScoreboardRenderer().unregister();
                ediManager.getRenderManager().getFooterRenderer().reset();
                ediManager.getRenderManager().getHeaderRenderer().reset();
            }
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

    public static ConfigManager getConfigManager() {
        return configManager;
    }
}
