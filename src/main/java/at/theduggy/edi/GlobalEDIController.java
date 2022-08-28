package at.theduggy.edi;

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
                for (EDIManager ediManager : Main.getEDIData().values()){
                    ediManager.getRenderManager().update();
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0,1);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        EDIManager ediManager = Main.getEDIManager(player.getUniqueId());
        if (ediManager.getOptionManager().compareInv(e.getInventory())){
            e.setCancelled(true);
            player.sendMessage("Ev: " + e.getRawSlot());
            ediManager.getOptionManager().handleClick(e.getRawSlot());
        }else if (ediManager.getOptionManager().compareDeepOptionIv(e.getClickedInventory())){
            e.setCancelled(true);
            ediManager.getOptionManager().handelDeepOptionInvClick(e.getRawSlot());
        }
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (!Main.getEDIData().containsKey(player.getUniqueId())){

            Main.getEDIData().put(player.getUniqueId(), new EDIManager(player));
        }
    }

    @EventHandler
    public void onInvClose(InventoryCloseEvent e){
        EDIManager ediManager = Main.getEDIManager(e.getPlayer().getUniqueId());
        if (ediManager.getOptionManager().compareDeepOptionIv(e.getInventory())){
            ediManager.getOptionManager().setDeepOptionInv(null);
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        Main.getEDIData().get(player.getUniqueId()).getRenderManager().getScoreboardRenderer().unregister();
        Main.getEDIData().remove(player.getUniqueId());
    }


    public enum OptionBackend {
        TOGGLE_EDI(4, 0),
        TOGGLE_FOOTER(5,0),
        TOGGLE_HEADER(3,0),
        COORDINATES(10,1),
        BIOMES(19,2);

        private final int slot;
        private final int displayIndex;
        OptionBackend(int slot, int displayIndex) {
            this.slot = slot;
            this.displayIndex = displayIndex;
        }

        public int getMainSlot(){
            return slot;
        }

        public int getDisplayIndex(){
            return displayIndex;
        }

        private static final Map<Integer, OptionBackend> slotToOption = new HashMap<>();
        static {
            for (OptionBackend option:values()){
                slotToOption.put(option.slot, option); //Add the main slot
            }
        }
        public static OptionBackend fromSlot(int slot){
            return slotToOption.getOrDefault(slot, null);
        }

    }

}
