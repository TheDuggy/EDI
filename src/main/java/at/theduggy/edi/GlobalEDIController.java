/*
EDI: Extended Debug Info
Copyright (C) 2022  Georg Kollegger(TheDuggy/CoderTheDuggy)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package at.theduggy.edi;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;


public class GlobalEDIController implements Listener {

    public GlobalEDIController() {
        new BukkitRunnable(){

            @Override
            public void run() {
                for (EDIManager ediManager : Main.getEdiPlayerData().values()){
                    if (ediManager.getPlayer()!=null){
                        ediManager.getRenderManager().update();
                    }
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0,Main.getConfigManager().getUpdateCycleCount());
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        EDIManager ediManager = Main.getEdiPlayerData().get(player.getUniqueId());
        if (e.getClickedInventory()!=null){
            Inventory settingsInv = ediManager.getOptionManager().getSettingsInvController().getInventory();
            Inventory optionSettingsInv = ediManager.getOptionManager().getOptionSettingsInvController().getInventory();
            Inventory keyFontInv = ediManager.getOptionManager().getKeyFontSettingsInvController().getInventory();
            Inventory separatorFontInv = ediManager.getOptionManager().getSeparatorFontSettingsInvController().getInventory();
            Inventory valueFontInv = ediManager.getOptionManager().getValueFontSettingsInvController().getInventory();
            int button = (e.isLeftClick()?0:1);
            if (settingsInv!=null && settingsInv.equals(e.getInventory())){
                e.setCancelled(true);
                ediManager.getOptionManager().getSettingsInvController().handleClick(e.getRawSlot(), button);
            }else if (optionSettingsInv!=null && optionSettingsInv.equals(e.getClickedInventory())){
                e.setCancelled(true);
                ediManager.getOptionManager().getOptionSettingsInvController().handleClick(e.getRawSlot(), button);//0=LEFT_CLICK 1=RIGHT_CLICK
            }else if (keyFontInv!=null && keyFontInv.equals(e.getInventory())){
                e.setCancelled(true);
                ediManager.getOptionManager().getKeyFontSettingsInvController().handleClick(e.getRawSlot(), button);
            }else if (separatorFontInv!=null && separatorFontInv.equals(e.getInventory())){
                e.setCancelled(true);
                ediManager.getOptionManager().getSeparatorFontSettingsInvController().handleClick(e.getRawSlot(), button);
            }else if (valueFontInv!=null && valueFontInv.equals(e.getInventory())){
                e.setCancelled(true);
                ediManager.getOptionManager().getValueFontSettingsInvController().handleClick(e.getRawSlot(), button);
            }
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        Main.getEdiPlayerData().get(player.getUniqueId()).getRenderManager().getScoreboardRenderer().unregister();
        Main.getEdiPlayerData().get(player.getUniqueId()).setPlayer(null);
        Main.getEdiPlayerData().remove(player.getUniqueId());
    }

}
