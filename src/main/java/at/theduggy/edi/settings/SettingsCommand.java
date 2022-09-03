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
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Pink" + ChatColor.DARK_PURPLE + " Magenta" + ChatColor.GOLD + "" + ChatColor.BOLD + "w>");
               Main.getEdiPlayerData().get(player.getUniqueId()).getOptionManager().showSettingsInv();
            }else {
                player.sendMessage(Main.getPrefix() + ChatColor.RED + "No arguments are allowed!");
            }
        }
        return false;
    }
}
