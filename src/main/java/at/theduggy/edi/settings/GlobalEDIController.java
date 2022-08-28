package at.theduggy.edi.settings;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
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
                    ediManager.getOrganisedObjectiveManager().update();
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class), 0,5);
    }

    @EventHandler
    public void onClick(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();

        if (Main.getEDIManager(player.getUniqueId()).getOptionManager().compareInv(e.getInventory())){
            System.out.println("check");
            e.setCancelled(true);
            player.sendMessage("Ev: " + e.getRawSlot());
            Main.getEDIManager(player.getUniqueId()).getOptionManager().handleClick(e.getRawSlot());
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
    public void onQuit(PlayerQuitEvent e){
        Player player = e.getPlayer();
        Main.getEDIData().get(player.getUniqueId()).getOrganisedObjectiveManager().unregister();
        Main.getEDIData().remove(player.getUniqueId());
    }


    public enum Option {
        TOGGLE_EDI(4),
        COORDINATES(10),
        BIOMES(19);

        private final int slot;
        Option(int slot) {
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

        private static final Map<Integer, Option> slotToOption = new HashMap<>();
        static {
            for (Option option:values()){
                slotToOption.put(option.slot, option); //Add the main slot
                slotToOption.put(option.slot+1, option); //Add enable-slot
                slotToOption.put(option.slot+2, option); //Add disable-slot
            }
        }
        public static Option fromSlot(int slot){
            return slotToOption.getOrDefault(slot, null);
        }

        public static List<Integer> getEnableButtonSlots(){
            List<Integer> enableButtonSlots = new ArrayList<>();
            Arrays.stream(values()).forEach(option -> enableButtonSlots.add(option.getEnableButtonSlot()));
            System.out.println(enableButtonSlots);
            return enableButtonSlots;
        }

        public static List<Integer> getDisableButtonSlots(){
            List<Integer> disableButtonSlots = new ArrayList<>();
            Arrays.stream(values()).forEach(option -> disableButtonSlots.add(option.getDisableButtonSlot()));
            System.out.println(disableButtonSlots);
            return disableButtonSlots;
        }

    }

}
