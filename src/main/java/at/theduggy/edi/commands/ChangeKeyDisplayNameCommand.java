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

package at.theduggy.edi.commands;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.Main;
import at.theduggy.edi.settings.options.Option;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChangeKeyDisplayNameCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (sender instanceof Player){
            String failed = Main.getPrefix(true) + ChatColor.RED + "Failed to change key-display-name!";
            Player player = (Player) sender;
            if (args.length == 2){
                EDIManager ediManager = Main.getEdiPlayerData().get(player.getUniqueId());
                if (ediManager.getOptionManager().getRegisteredOptions().containsKey(args[0])){
                    Option option = ediManager.getOptionManager().getRegisteredOptions().get(args[0]);
                    String oldKeyDisplayName = option.getDisplayName();
                    option.setKeyDisplayName(args[1]);
                    player.sendMessage(Main.getPrefix(true) + ChatColor.GREEN + "Successfully changed key-display-name for option\n        " + args[0] + " from " + ChatColor.GOLD + oldKeyDisplayName + ChatColor.GREEN + " to " + ChatColor.GOLD + args[1] + ChatColor.GREEN + "!");
                }else {
                    player.sendMessage(failed);
                }
            }else {
                player.sendMessage(failed);
            }
        }else {
            sender.sendMessage(Main.getPrefix(false) + " You are not a player!");
        }
        return false;
    }
}
