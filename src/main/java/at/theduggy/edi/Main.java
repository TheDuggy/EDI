package at.theduggy.edi;

import at.theduggy.edi.settings.SettingInvListener;
import at.theduggy.edi.settings.SettingsCommand;
import at.theduggy.edi.settings.SettingsInv;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class Main extends JavaPlugin {


    private static final HashMap<UUID, SettingsInv> settingInvs = new HashMap<>();
    private static String prefix = ChatColor.DARK_GRAY + "[" + ChatColor.GOLD + "EDI" + ChatColor.DARK_GRAY + "] " + ChatColor.WHITE;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new SettingInvListener(), this);
        getCommand("settings").setExecutor(new SettingsCommand());
    }

    @Override
    public void onDisable() {

    }

    public static SettingsInv getSettingsInv(UUID player) {
        return settingInvs.get(player);
    }

    public static HashMap<UUID, SettingsInv> getSettingInvs() {
        return settingInvs;
    }

    public static String getPrefix() {
        return prefix;
    }
}
