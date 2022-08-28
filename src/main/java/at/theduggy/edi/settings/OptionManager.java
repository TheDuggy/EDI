package at.theduggy.edi.settings;

import at.theduggy.edi.EDIManager;
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

import at.theduggy.edi.settings.GlobalEDIController.Option;

public class OptionManager {

    //The actual inventory
    private final Inventory optionInventory = Bukkit.createInventory(null,54,"EDI Settings");

    //all options which can be used
    private boolean displayEnabled = true;
    private boolean cordsEnabled = true;
    private boolean biomesEnabled = true;
    private final EDIManager ediManager;

    public OptionManager(EDIManager ediManager){

        this.ediManager = ediManager;
        optionInventory.setItem(Option.TOGGLE_EDI.getMainSlot(), new ItemStack(displayEnabled?Material.GREEN_STAINED_GLASS_PANE:Material.RED_STAINED_GLASS_PANE));
        addSettingsOption(Option.COORDINATES.getMainSlot(), cordsEnabled, "Cords", "Shows you your current block-coordinates!");
        addSettingsOption(Option.BIOMES.getMainSlot(), biomesEnabled, "Biom", "Shows your current biom!");
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
        ediManager.getPlayer().openInventory(optionInventory);
    }

    //This method is only to render a click (=> switches the glint)
    private void renderClick(int slot){
        if (Option.getDisableButtonSlots().contains(slot)){
            ItemStack enableButton = optionInventory.getItem(slot-1);
            ItemMeta enableButtonMeta = enableButton.getItemMeta();
            enableButtonMeta.removeEnchant(Enchantment.KNOCKBACK);
            enableButton.setItemMeta(enableButtonMeta);
            ItemStack disableButton = optionInventory.getItem(slot);
            ItemMeta disableButtonMeta = disableButton.getItemMeta();
            disableButtonMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            disableButtonMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            disableButton.setItemMeta(disableButtonMeta);
            ediManager.getPlayer().sendMessage(String.valueOf(2));
            ediManager.getPlayer().sendMessage(String.valueOf(1));
        }else if (Option.getEnableButtonSlots().contains(slot)){
            ItemStack enableButton = optionInventory.getItem(slot);
            ItemMeta enableButtonMeta = enableButton.getItemMeta();
            enableButtonMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            enableButtonMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            enableButton.setItemMeta(enableButtonMeta);
            ItemStack disableButton = optionInventory.getItem(slot+1);
            ItemMeta disableButtonMeta = disableButton.getItemMeta();
            disableButtonMeta.removeEnchant(Enchantment.KNOCKBACK);
            disableButton.setItemMeta(disableButtonMeta);

        }else if (slot == Option.TOGGLE_EDI.getMainSlot()){
            if (displayEnabled){
                optionInventory.setItem(slot, new ItemStack(Material.GREEN_STAINED_GLASS_PANE));
            }else {
                optionInventory.setItem(slot, new ItemStack(Material.RED_STAINED_GLASS_PANE));
            }
        }
    }

    //This method is executed from the GlobalOptionController each time an item is clicked in this inv
    public void handleClick(int slot){
        if (Option.fromSlot(slot)==Option.COORDINATES){
            cordsEnabled = slot == Option.COORDINATES.getEnableButtonSlot();
            renderClick(slot);
        }else if (Option.fromSlot(slot)==Option.BIOMES){
            biomesEnabled = slot == Option.BIOMES.getEnableButtonSlot();
            renderClick(slot);
        } else if (slot == Option.TOGGLE_EDI.getMainSlot()){
            displayEnabled = !displayEnabled;
            renderClick(slot);
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

    public boolean isBiomesEnabled(){
        return biomesEnabled;
    }

    public boolean compareInv(Inventory toCompare){
        return toCompare.equals(optionInventory);
    }

}
