package at.theduggy.edi.settings;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

public class SettingsInv {

    private final Inventory inventory;

    public SettingsInv(){
        this.inventory = Bukkit.createInventory(null, 81,"Settings");
        inventory.setItem(1, new ItemStack(Material.RED_CONCRETE));
    }

    public Inventory getInventory() {
        return inventory;
    }
}
