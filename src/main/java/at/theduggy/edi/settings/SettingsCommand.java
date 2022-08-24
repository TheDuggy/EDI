package at.theduggy.edi.settings;

import at.theduggy.edi.Main;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SettingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player){
            Player player = (Player) commandSender;
            if (strings.length==0){
                player.openInventory(Main.getSettingsInv(player.getUniqueId()).getInventory());
            }else {
                player.sendMessage(Main.getPrefix() + ChatColor.RED + "No arguments a re allowed!");
            }
        }
        return false;
    }
}
