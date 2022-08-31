package at.theduggy.edi;

import at.theduggy.edi.storage.StorageManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;


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
        }.runTaskTimer(Main.getPlugin(Main.class), 0,1);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        EDIManager ediManager = Main.getEdiPlayerData().get(player.getUniqueId());
        if (ediManager.getOptionManager().compareInv(e.getInventory())){
            e.setCancelled(true);
            ediManager.getOptionManager().handleClick(e.getRawSlot());
        }else if (ediManager.getOptionManager().compareDeepOptionIv(e.getClickedInventory())){
            e.setCancelled(true);
            int button; //0=LEFT_CLICK 1=RIGHT_CLICK
            if (e.isLeftClick()){
                button = 0;
            }else {
                button = 1;
            }
            ediManager.getOptionManager().handelDeepOptionInvClick(e.getRawSlot(), button);
        }
    }
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        Main.getEdiPlayerData().get(player.getUniqueId()).getRenderManager().getScoreboardRenderer().unregister();
        Main.getEdiPlayerData().remove(player.getUniqueId());
        Main.getEdiPlayerData().get(player.getUniqueId()).setPlayer(null);
    }

}
