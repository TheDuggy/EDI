package at.theduggy.edi.settings;

import at.theduggy.edi.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class GlobalOptionController implements Listener {



    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if (Main.getSettingsInv(player.getUniqueId()).getInventory().equals(e.getInventory())){
            System.out.println("check");
            Main.getSettingsInv(player.getUniqueId()).onClick(e.getRawSlot(), player);
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (!Main.getSettingInvs().containsKey(player.getUniqueId())){
            Main.getSettingInvs().put(player.getUniqueId(), new SettingsInv());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        Main.getSettingInvs().remove(player.getUniqueId());
    }


    public enum Options {
        TOGGLE_EDI(4),
        COORDINATES(10);

        private final int slot;
        Options(int slot) {
            this.slot = slot;
        }

        public int getMainSlot(){
            return slot;
        }

        public int getEnableButtonSlot(){
            return slot+1;
        }
        public int getDisableButtonSlot(){
            return slot+2;
        }

        public static List<Integer> getEnableButtonSlots(){
            List<Integer> enableButtonSlots = new ArrayList<>();
            Arrays.stream(values()).forEach(option -> enableButtonSlots.add(option.getEnableButtonSlot()));
            return enableButtonSlots;
        }

        public static List<Integer> getDisableButtonSlots(){
            List<Integer> disableButtonSlots = new ArrayList<>();
            Arrays.stream(values()).forEach(option -> disableButtonSlots.add(option.getDisableButtonSlot()));
            return disableButtonSlots;
        }

    }

}
