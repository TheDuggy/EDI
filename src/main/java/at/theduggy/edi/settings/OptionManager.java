package at.theduggy.edi.settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import at.theduggy.edi.settings.GlobalOptionController.Options;

public class OptionManager {

    //The actual inventory
    private Inventory optionInventory = Bukkit.createInventory(null,54, "EDI Settings");

    //all options which can be used
    private boolean displayEnabled = true;
    private boolean cordsEnabled = false;
    private UUID player;

    public OptionManager(UUID player){
        this.player = player;
        optionInventory.setItem(Options.TOGGLE_EDI.getMainSlot(), new ItemStack(displayEnabled?Material.GREEN_STAINED_GLASS_PANE:Material.RED_STAINED_GLASS_PANE));
        addSettingsOption(Options.COORDINATES.getMainSlot(), true, "Cords", "Shows you your current block-coordinates!");
    }

    //Method to create each option
    private void addSettingsOption(int slot,boolean enabled,String name, String info){
        ItemStack enableButton = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta enableButtonMeta = enableButton.getItemMeta();
        enableButtonMeta.setDisplayName(ChatColor.GREEN + "Enable");
        ItemStack disableButton = new ItemStack(Material.RED_CONCRETE);
        ItemMeta disableButtonMeta = disableButton.getItemMeta();
        disableButtonMeta.setDisplayName(ChatColor.RED + "Disable");
        ItemStack optionInfo = new ItemStack(Material.SIGN);
        ItemMeta optionInfoMeta = optionInfo.getItemMeta();
        optionInfoMeta.setDisplayName(ChatColor.GOLD + name);
        optionInfoMeta.setLore(new ArrayList<>(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + info)));
        optionInfo.setItemMeta(optionInfoMeta);
        if (enabled){
            enableButtonMeta.addEnchant(Enchantment.KNOCKBACK,1, true);
            enableButtonMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }else {
            disableButtonMeta.addEnchant(Enchantment.KNOCKBACK,1, true);
            disableButtonMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }

        enableButton.setItemMeta(enableButtonMeta);
        disableButton.setItemMeta(disableButtonMeta);
        optionInventory.setItem(slot, optionInfo);
        optionInventory.setItem(slot+1, enableButton);
        optionInventory.setItem(slot+2, disableButton);
    }

    //This method is used to show the inv to the player and update it
    public void show(){
        Bukkit.getPlayer(player).openInventory(optionInventory);
    }

    //This method is only to render a click (=> render the toggle)
    public void renderToggle(int slot){
        if (slot>0){
            if (Options.getDisableButtonSlots().contains(slot)){
                ItemStack enableButton = optionInventory.getItem(slot);
                ItemMeta enableButtonMeta = enableButton.getItemMeta();
                enableButtonMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
                enableButtonMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                enableButton.setItemMeta(enableButtonMeta);
                ItemStack disableButton = optionInventory.getItem(slot+1);
                ItemMeta disableButtonMeta = disableButton.getItemMeta();
                disableButtonMeta.removeEnchant(Enchantment.KNOCKBACK);
                disableButton.setItemMeta(disableButtonMeta);
                show();
            }else if (Options.getEnableButtonSlots().contains(slot)){
                ItemStack enableButton = optionInventory.getItem(slot);
                ItemMeta enableButtonMeta = enableButton.getItemMeta();
                enableButtonMeta.removeEnchant(Enchantment.KNOCKBACK);
                enableButton.setItemMeta(enableButtonMeta);
                ItemStack disableButton = optionInventory.getItem(slot+1);
                ItemMeta disableButtonMeta = disableButton.getItemMeta();
                disableButtonMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
                disableButtonMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                disableButton.setItemMeta(disableButtonMeta);
                show();
            }else if (slot == Options.TOGGLE_EDI.getMainSlot()){
                if (displayEnabled){
                    optionInventory.setItem(slot, new ItemStack(Material.RED_STAINED_GLASS_PANE));
                }else {
                    optionInventory.setItem(slot, new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
                }
            }
        }
    }

    //This method is executet from The GlobalOptionController each time an item is clicked in this inv
    public void handleClick(int slot){
        if (slot== Options.COORDINATES.getEnableButtonSlot()){
            cordsEnabled =true;
            renderToggle(slot);
        }else if(slot == Options.COORDINATES.getDisableButtonSlot()){
            cordsEnabled = false;
            renderToggle(slot);
        }else if (slot == Options.TOGGLE_EDI.getMainSlot()){
            displayEnabled= !displayEnabled;
            show();
        }
    }

    //Some standard getter-methods

    public boolean isDisplayEnabled(){
        return displayEnabled;
    }

    public boolean isCordsEnabled() {
        return cordsEnabled;
    }
}
