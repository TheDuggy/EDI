package at.theduggy.edi.settings;

import at.theduggy.edi.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class SettingInvListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if (Main.getSettingsInv(player.getUniqueId()).getInventory().equals(e.getInventory())){
            System.out.println("Check");
        }
    }

    @EventHandler
    public void onInvDrag(InventoryDragEvent e){
        Player player = (Player) e.getWhoClicked();
        if (Main.getSettingsInv(player.getUniqueId()).equals(e.getInventory())){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        Main.getSettingInvs().put(player.getUniqueId(), new SettingsInv());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        Main.getSettingInvs().remove(player.getUniqueId());
    }

}
