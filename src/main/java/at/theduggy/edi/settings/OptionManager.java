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

import java.util.Arrays;
import java.util.Collections;

import at.theduggy.edi.GlobalEDIController.OptionBackend;

public class OptionManager {

    //The actual inventory
    private Inventory optionInventory = null;
    private DeepOptionInv deepOptionInv = null;

    //basic on-tob options
    private boolean displayEnabled = true;
    private boolean headerEnabled = false;
    private boolean footerEnabled = false;

    public Option getCords() {
        return cords;
    }

    public Option getBiome() {
        return biomes;
    }

    //all options which can be used
    private final Option cords = new Option(false, false, true, true);
    private final Option biomes  = new Option(false, false, true, true);
    private final EDIManager ediManager;

    public OptionManager(EDIManager ediManager){
        this.ediManager = ediManager;
    }

    public void addOnTopToggle(Material type, String displayName, String info, boolean enabled, int slot){
        ItemStack itemStack = new ItemStack(type);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(Arrays.asList( ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + info));
        if (enabled){
            itemMeta.setDisplayName(ChatColor.GREEN + displayName);
            itemMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }else {
            itemMeta.setDisplayName(ChatColor.RED + displayName);
        }
        itemStack.setItemMeta(itemMeta);
        optionInventory.setItem(slot, itemStack);
    }

    //Method to create each option
    private void addSettingsOption(int slot,String name, String info, Option option){
        ItemStack sign = new ItemStack(Material.SIGN);
        ItemMeta itemMeta = sign.getItemMeta();
        itemMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + info));
        itemMeta.setDisplayName(ChatColor.GOLD + name);
        if (option.isFooter()||option.isHeader()||option.isEdiDisplay()){
            itemMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        sign.setItemMeta(itemMeta);
        optionInventory.setItem(slot, sign);
    }

    private void refreshOptionInventorry(){
        optionInventory = Bukkit.createInventory(null,54,"EDI Settings");
        addOnTopToggle(Material.YELLOW_STAINED_GLASS_PANE, "EDInfo-Screen", "Enables/disbales the EDInfo-Screen", displayEnabled, 4);
        addOnTopToggle(Material.LEATHER_HELMET, "EDInfo-Header", "Enables/disbales the EDInfo-Header", headerEnabled, 3);
        addOnTopToggle(Material.LEATHER_BOOTS, "EDInfo-Footer", "Enables/disbales the EDInfo-Footer", footerEnabled, 5);
        addSettingsOption(OptionBackend.COORDINATES.getMainSlot(), "Cords", "Shows you your current block-coordinates!", cords);
        addSettingsOption(OptionBackend.BIOMES.getMainSlot(), "Biom", "Shows your current biom!", biomes);
    }

    private void refreshDeepOptionInventory(String optionName, Option option){
        Inventory inventory = Bukkit.createInventory(null,9, "[EDI] " + optionName);
        deepOptionInv = new DeepOptionInv(inventory, option);
        ItemStack header = new ItemStack(Material.LEATHER_HELMET);
        ItemStack footer = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack ediDisplay = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemStack showKeys = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemMeta headerMeta = header.getItemMeta();
        ItemMeta footerMeta = footer.getItemMeta();
        ItemMeta ediDisplayMeta = ediDisplay.getItemMeta();
        ItemMeta showKeysMeta = showKeys.getItemMeta();

        if (option.isShowKeys()){
            showKeysMeta.setDisplayName(ChatColor.GREEN + "Show keys");
            showKeysMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            showKeysMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }else {
            showKeysMeta.setDisplayName(ChatColor.RED + "Show keys");
        }
        showKeysMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Shows the option " + optionName + " in key:value pairs if true"));

        if (option.isEdiDisplay()){
            ediDisplayMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            ediDisplayMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            ediDisplayMeta.setDisplayName(ChatColor.GREEN + "EDInfo-Screen");
        }else {
            ediDisplayMeta.setDisplayName(ChatColor.RED + "EDInfo-Screen");
        }
        ediDisplayMeta.setLore(Collections.singletonList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Shows " + optionName + " on the EDInfo-Screen"));

        if (option.isFooter()){
            footerMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            footerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            footerMeta.setDisplayName(ChatColor.GREEN + "EDInfo-Footer");
        }else {
            footerMeta.setDisplayName(ChatColor.RED + "EDInfo-Footer");
        }
        footerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        footerMeta.setLore(Collections.singletonList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Shows " + optionName + " on the player-list-footer"));

        if (option.isHeader()){
            headerMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            headerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            headerMeta.setDisplayName(ChatColor.GREEN + "EDInfo-Header");
        }else {
            headerMeta.setDisplayName(ChatColor.RED + "EDInfo-Header");
        }
        headerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        headerMeta.setLore(Collections.singletonList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Shows " + optionName + " on the player-list-header"));

        header.setItemMeta(headerMeta);
        footer.setItemMeta(footerMeta);
        ediDisplay.setItemMeta(ediDisplayMeta);
        showKeys.setItemMeta(showKeysMeta);

        inventory.setItem(1, showKeys);
        inventory.setItem(3, header);
        inventory.setItem(4, footer);
        inventory.setItem(5, ediDisplay);
    }

    //This method is used to show the inv to the player and update it
    public void show(){
        refreshOptionInventorry();
        ediManager.getPlayer().openInventory(optionInventory);
    }

    //This method is executed from the GlobalOptionController each time an item is clicked in this inv
    public void handleClick(int slot){
        if (OptionBackend.fromSlot(slot)== OptionBackend.COORDINATES){
            refreshDeepOptionInventory("Cords", cords);
            ediManager.getPlayer().openInventory(deepOptionInv.getDeepOptionInv());
        }else if (OptionBackend.fromSlot(slot)== OptionBackend.BIOMES){
            refreshDeepOptionInventory("Biomes", biomes);
            ediManager.getPlayer().openInventory(deepOptionInv.getDeepOptionInv());
        } else if (OptionBackend.fromSlot(slot) == OptionBackend.TOGGLE_EDI){
            ItemStack ediDisplay = optionInventory.getItem(slot);
            ItemMeta ediDisplayMeta = ediDisplay.getItemMeta();
            if (displayEnabled){
                displayEnabled = false;
                if (ediDisplayMeta.hasEnchant(Enchantment.KNOCKBACK)){
                    ediDisplayMeta.removeEnchant(Enchantment.KNOCKBACK);
                }
                ediDisplayMeta.setDisplayName(ChatColor.RED + "EDInfo-Screen");
            }else {
                displayEnabled = true;
                ediDisplayMeta.setDisplayName(ChatColor.GREEN + "EDInfo-Screen");
                ediDisplayMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
                ediDisplayMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            ediDisplay.setItemMeta(ediDisplayMeta);
        }else if (OptionBackend.fromSlot(slot) == OptionBackend.TOGGLE_HEADER){
            ItemStack header = optionInventory.getItem(slot);
            ItemMeta headerMeta = header.getItemMeta();
            if (headerEnabled){
                headerMeta.setDisplayName(ChatColor.RED + "EDInfo-Header");
                if (headerMeta.hasEnchant(Enchantment.KNOCKBACK)){
                    headerMeta.removeEnchant(Enchantment.KNOCKBACK);
                }
                headerEnabled = false;
            }else {
                headerMeta.setDisplayName(ChatColor.GREEN + "EDInfo-Header");
                headerMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                headerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                headerEnabled = true;
            }
            header.setItemMeta(headerMeta);
        }else if (OptionBackend.fromSlot(slot) == OptionBackend.TOGGLE_FOOTER){
            ItemStack footer = optionInventory.getItem(slot);
            ItemMeta footerMeta = footer.getItemMeta();
            if (footerEnabled){
                footerMeta.setDisplayName(ChatColor.RED + "EDInfo-Footer");
                if (footerMeta.hasEnchant(Enchantment.KNOCKBACK)){
                    footerMeta.removeEnchant(Enchantment.KNOCKBACK);
                }
                footerEnabled = false;
            }else {
                footerMeta.setDisplayName(ChatColor.GREEN + "EDInfo-Footer");
                footerMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                footerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                footerEnabled = true;
            }
            footer.setItemMeta(footerMeta);
        }
    }

    public void handelDeepOptionInvClick(int slot){
        final int showKeysSlot = 1;
        final int headerSlot = 3;
        final int footerSlot = 4;
        final int edInfoDisplaySlot = 5;

        if (Arrays.asList(showKeysSlot, headerSlot, footerSlot, edInfoDisplaySlot).contains(slot)){
            if (slot == showKeysSlot){
                ItemStack showKeys = deepOptionInv.getDeepOptionInv().getItem(slot);
                ItemMeta showKeysMeta = showKeys.getItemMeta();
                if (deepOptionInv.getOption().isShowKeys()){
                    showKeysMeta.setDisplayName(ChatColor.RED + "Show keys");
                    if (showKeysMeta.hasEnchant(Enchantment.KNOCKBACK)){
                        showKeysMeta.removeEnchant(Enchantment.KNOCKBACK);
                    }
                    deepOptionInv.getOption().setShowKeys(false);
                }else {
                    showKeysMeta.setDisplayName(ChatColor.GREEN + "Show keys");
                    showKeysMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                    showKeysMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    deepOptionInv.getOption().setShowKeys(true);
                }
                showKeys.setItemMeta(showKeysMeta);
            }else if (slot == edInfoDisplaySlot){
                ItemStack edInfoDisplay = deepOptionInv.getDeepOptionInv().getItem(slot);
                ItemMeta edInfoDisplayMeta = edInfoDisplay.getItemMeta();
                if (deepOptionInv.getOption().isEdiDisplay()){
                    edInfoDisplayMeta.setDisplayName(ChatColor.RED + "EDInfo-Screen");
                    if (edInfoDisplayMeta.hasEnchant(Enchantment.KNOCKBACK)){
                        edInfoDisplayMeta.removeEnchant(Enchantment.KNOCKBACK);
                    }
                    deepOptionInv.getOption().setEdiDisplay(false);
                }else {
                    edInfoDisplayMeta.setDisplayName(ChatColor.GREEN + "EDInfo-Screen");
                    edInfoDisplayMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                    edInfoDisplayMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    deepOptionInv.getOption().setEdiDisplay(true);
                }
                edInfoDisplay.setItemMeta(edInfoDisplayMeta);
            }else if (slot == headerSlot){
                ItemStack header = deepOptionInv.getDeepOptionInv().getItem(slot);
                ItemMeta headerMeta = header.getItemMeta();
                if (deepOptionInv.getOption().isHeader()){
                    headerMeta.setDisplayName(ChatColor.RED + "EDInfo-Header");
                    if (headerMeta.hasEnchant(Enchantment.KNOCKBACK)){
                        headerMeta.removeEnchant(Enchantment.KNOCKBACK);
                    }
                    deepOptionInv.getOption().setHeader(false);
                }else {
                    headerMeta.setDisplayName(ChatColor.GREEN + "EDInfo-Header");
                    headerMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                    headerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    deepOptionInv.getOption().setHeader(true);
                }
                header.setItemMeta(headerMeta);
            }else if (slot == footerSlot){
                ItemStack footer = deepOptionInv.getDeepOptionInv().getItem(slot);
                ItemMeta footerMeta = footer.getItemMeta();
                if (deepOptionInv.getOption().isFooter()){
                    footerMeta.setDisplayName(ChatColor.RED + "EDInfo-Footer");
                    if (footerMeta.hasEnchant(Enchantment.KNOCKBACK)){
                        footerMeta.removeEnchant(Enchantment.KNOCKBACK);
                    }
                    deepOptionInv.getOption().setFooter(false);
                }else {
                    footerMeta.setDisplayName(ChatColor.GREEN + "EDInfo-Footer");
                    footerMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
                    footerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                    deepOptionInv.getOption().setFooter(true);
                }
                footer.setItemMeta(footerMeta);
            }
        }
    }

    //Some standard getter-methods

    public boolean isDisplayEnabled(){
        return displayEnabled;
    }

    public boolean isHeaderEnabled() {
        return headerEnabled;
    }

    public boolean isFooterEnabled() {
        return footerEnabled;
    }

    public boolean compareInv(Inventory toCompare){
        return toCompare.equals(optionInventory);
    }

    public boolean compareDeepOptionIv(Inventory inventory){
        if (deepOptionInv==null){
            return false;
        }else {
            return inventory.equals(deepOptionInv.getDeepOptionInv());
        }
    }

    public void setDeepOptionInv(DeepOptionInv deepOptionInv) {
        this.deepOptionInv = deepOptionInv;
    }
}
