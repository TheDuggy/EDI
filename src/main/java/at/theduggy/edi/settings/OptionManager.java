package at.theduggy.edi.settings;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.Main;
import at.theduggy.edi.settings.options.BiomeOption;
import at.theduggy.edi.settings.options.CordsOption;
import at.theduggy.edi.settings.options.Option;
import at.theduggy.edi.settings.options.RealTimeOption;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class OptionManager{

    //The actual inventory
    private Inventory optionInventory = null;
    private DeepOptionInv deepOptionInv = null;

    //basic on-tob options
    private Boolean displayEnabled = true;
    private Boolean headerEnabled = false;
    private Boolean footerEnabled = false;

    private final TreeMap<String, Option> options = new TreeMap<>();

    private final EDIManager ediManager;

    public OptionManager(EDIManager ediManager){
        this.ediManager = ediManager;
        registerOption(new CordsOption("Cords", "Shows your current cords",false, false, true, true));
        registerOption(new BiomeOption("Biome","Shows your current biome", false, false, true, true));
        registerOption(new RealTimeOption("Real-time", "Shows you real-time", false, false, true, true));
    }

    private void refreshOptionInventory(){
        optionInventory = Bukkit.createInventory(null,54,"EDI Settings");
        addFixedButtons(Material.YELLOW_STAINED_GLASS_PANE, "EDInfo-Screen", "Enables/disbales the EDInfo-Screen", displayEnabled, 4);
        addFixedButtons(Material.LEATHER_HELMET, "EDInfo-Header", "Enables/disbales the EDInfo-Header", headerEnabled, 3);
        addFixedButtons(Material.LEATHER_BOOTS, "EDInfo-Footer", "Enables/disbales the EDInfo-Footer", footerEnabled, 5);
        for (Option option: options.values()){
            //Create all options on the gui
            ItemStack sign = new ItemStack(Material.SIGN);
            ItemMeta itemMeta = sign.getItemMeta();
            itemMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC +option.getInfo()));
            itemMeta.setDisplayName(ChatColor.GOLD + option.getName());
            if (option.isFooter()||option.isHeader()||option.isEdiDisplay()){
                itemMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            sign.setItemMeta(itemMeta);
            optionInventory.setItem(option.getInvSlot(), sign);
        }
    }

    public void addFixedButtons(Material type, String displayName, String info, boolean enabled, int slot){
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

    private void refreshDeepOptionInventory(Option option){
        String optionName = option.getName();
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
        refreshOptionInventory();
        ediManager.getPlayer().openInventory(optionInventory);
    }

    public void updateOnTobToggle(int slot){
        switch (slot){
            case 3:
                headerEnabled = updateButton(headerEnabled, "EDInfo-Header", slot, optionInventory);
                break;

            case 4:
                displayEnabled = updateButton(displayEnabled, "EDInfo-Screen", slot, optionInventory);
                break;

            case 5:
                footerEnabled = updateButton(footerEnabled, "EDInfo-Footer", slot, optionInventory);
                break;
        }
    }

    public boolean updateButton(boolean enabled, String displayName, int slot, Inventory inventory){
        ItemStack itemStack = inventory.getItem(slot);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (enabled){
            if (itemMeta.hasEnchant(Enchantment.KNOCKBACK)){
                itemMeta.removeEnchant(Enchantment.KNOCKBACK);
            }
            itemMeta.setDisplayName(ChatColor.RED + displayName);
            itemStack.setItemMeta(itemMeta);
            return false;
        }else {
            itemMeta.setDisplayName(ChatColor.GREEN + displayName);
            itemMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            itemStack.setItemMeta(itemMeta);
            return true;
        }
    }

    //This method is executed from the GlobalOptionController each time an item is clicked in this inv
    public void handleClick(int slot){
        final int ediScreen = 4;
        final int footer = 5;
        final int header = 3;
        HashMap<Integer, Option> optionsWithSlots = new HashMap<>();
        options.forEach((id, option) -> optionsWithSlots.put(option.getInvSlot(), option));
        if (optionsWithSlots.containsKey(slot)) {
            refreshDeepOptionInventory(optionsWithSlots.get(slot));
            ediManager.getPlayer().openInventory(deepOptionInv.getDeepOptionInv());
        }else if (Arrays.asList(ediScreen,footer,header).contains(slot)){
            updateOnTobToggle(slot);
        }
    }

    public void handelDeepOptionInvClick(int slot){
        final int showKeysSlot = 1;
        final int headerSlot = 3;
        final int footerSlot = 4;
        final int edInfoDisplaySlot = 5;
        if (Arrays.asList(showKeysSlot, headerSlot, footerSlot, edInfoDisplaySlot).contains(slot)){
            if (slot == showKeysSlot){
                deepOptionInv.getOption().setEdiDisplay(updateButton(deepOptionInv.getOption().isShowKeys(), "Show keys", slot, deepOptionInv.getDeepOptionInv()));
            }else if (slot == edInfoDisplaySlot){
                deepOptionInv.getOption().setEdiDisplay(updateButton(deepOptionInv.getOption().isEdiDisplay(), "EDInfo-Screen", slot, deepOptionInv.getDeepOptionInv()));
            }else if (slot == headerSlot){
                deepOptionInv.getOption().setHeader(updateButton(deepOptionInv.getOption().isHeader(), "EDInfo-Header", slot, deepOptionInv.getDeepOptionInv()));
            }else if (slot == footerSlot){
                deepOptionInv.getOption().setFooter(updateButton(deepOptionInv.getOption().isFooter(), "EDInfo-Footer", slot, deepOptionInv.getDeepOptionInv()));
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

    public void registerOption(Option option){
        int slot = 13;
        ArrayList<Integer> keys = new ArrayList<>();
        options.forEach((s, option1) -> keys.add(option1.getInvSlot()));
        if (options.size()==0){
            slot = 10;
        }else if (Arrays.asList(37,39,41).contains(keys.get(keys.size()-1))){
                slot = keys.get(keys.size()-1)-25;
        }else if (keys.get(keys.size()-1)<43){
            slot = keys.get(keys.size()-1) + 9;
        }
        System.out.println(slot);
        if (slot > 0){
            option.setInvSlot(slot);
            options.put(new NamespacedKey(Main.getPlugin(Main.class), option.getName() + "_option").toString(), option);
        }else {
            throw new IllegalArgumentException("Options are full!"); //TODO Remove !!!
        }

    }

    public boolean compareDeepOptionIv(Inventory inventory){
        if (deepOptionInv==null){
            return false;
        }else {
            return inventory.equals(deepOptionInv.getDeepOptionInv());
        }
    }

    public TreeMap<String, Option> getRegisteredOptions() {
        return options;
    }

    public void setDeepOptionInv(DeepOptionInv deepOptionInv) {
        this.deepOptionInv = deepOptionInv;
    }
}
