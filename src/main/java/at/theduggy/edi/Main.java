package at.theduggy.edi;

import at.theduggy.edi.settings.SettingsCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {

    private static final HashMap<UUID, EDIManager> ediPlayerData = new HashMap<>();
    public static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "" + ChatColor.BOLD + "EDI" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE;
    public static String logo = ChatColor.DARK_GRAY + "└" + ChatColor.GOLD + "" + ChatColor.BOLD + "EDInfo" + ChatColor.DARK_GRAY + "┘";

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
        for (EDIManager ediManager:ediPlayerData.values()){
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
        return ediPlayerData.get(player);
    }

    public static HashMap<UUID, EDIManager> getEDIData() {
        return ediPlayerData;
    }

    public static String getPrefix() {
        return prefix;
    }
}
