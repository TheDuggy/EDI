/*
 * EDI: Extended Debug Info
 * Copyright (C) 2022  Georg Kollegger(TheDuggy/CoderTheDuggy)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.theduggy.edi;

import at.theduggy.edi.commands.ChangeKeyDisplayNameCommand;
import at.theduggy.edi.commands.SettingsCommand;
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
    public static String logo = ChatColor.DARK_GRAY + "└" + ChatColor.GOLD + "" + ChatColor.BOLD + "EDInfo" + ChatColor.DARK_GRAY + "┘";
    private static StorageManager storageManager;
    private static HashMap<UUID, EDIManager> ediPlayerData = new HashMap<>();

    private static ConfigManager configManager;

    @Override
    public void onEnable() {
        this.saveConfig();
        configManager = new ConfigManager(this.getConfig());
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
        getCommand("edi-change-key-display-name").setExecutor(new ChangeKeyDisplayNameCommand());
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
                //Check if invs are null
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

    public static String getPrefix(boolean color) {
        if (color){
            return  ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "" + ChatColor.BOLD + "EDI" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE;
        }else {
            return "[EDI] ";
        }
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
