package at.theduggy.edi.settings;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.Main;
import at.theduggy.edi.settings.options.*;
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
    private boolean displayEnabled = true;
    private boolean headerEnabled = false;
    private boolean footerEnabled = false;

    private final TreeMap<String, Option> options = new TreeMap<>();

    private final EDIManager ediManager;

    public OptionManager(EDIManager ediManager){
        this.ediManager = ediManager;
        registerOption(new CordsOption("Cords", "Shows your current cords",false, false, true, true));
        registerOption(new BiomeOption("Biome","Shows your current biome", false, false, true, true));
        registerOption(new RealTimeOption("Real-time", "Shows you the current real-time", false, false, true, true));
        registerOption(new IngameTimeOption("Ingame-time", "Shows you the current ingame-time", false, false, true, true));
    }

    private void refreshOptionInventory(){
        optionInventory = Bukkit.createInventory(null,54,"EDI Settings");
        addFixedButtons(Material.YELLOW_STAINED_GLASS_PANE, "EDInfo-Screen", "Enables/disbales the EDInfo-Screen", displayEnabled, 4);
        addFixedButtons(Material.LEATHER_HELMET, "EDInfo-Header", "Enables/disbales the EDInfo-Header", headerEnabled, 3);
        addFixedButtons(Material.LEATHER_BOOTS, "EDInfo-Footer", "Enables/disbales the EDInfo-Footer", footerEnabled, 5);

        //Crate the close-button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();;
        closeMeta.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Close" + ChatColor.DARK_GRAY + "]");
        close.setItemMeta(closeMeta);
        optionInventory.setItem(49, close);

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
        ItemStack back = new ItemStack(Material.ARROW);
        ItemStack upDownPosition = new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA);
        ItemMeta upDownPositionMeta = upDownPosition.getItemMeta();
        ItemMeta backMeta = back.getItemMeta();
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

        backMeta.setDisplayName( ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Go back" + ChatColor.DARK_GRAY + "]");
        backMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Go back to EDI-Settings"));

        upDownPositionMeta.setDisplayName(ChatColor.AQUA + "Change position");
        upDownPositionMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "RIGHT CLICK" + ChatColor.GOLD + "▲", ChatColor.DARK_GRAY + "LEFT CLICK" + ChatColor.GOLD + "▼"));

        header.setItemMeta(headerMeta);
        footer.setItemMeta(footerMeta);
        ediDisplay.setItemMeta(ediDisplayMeta);
        showKeys.setItemMeta(showKeysMeta);
        back.setItemMeta(backMeta);
        upDownPosition.setItemMeta(upDownPositionMeta);

        inventory.setItem(2, showKeys);
        inventory.setItem(3, header);
        inventory.setItem(4, footer);
        inventory.setItem(5, ediDisplay);
        inventory.setItem(8, back);
        inventory.setItem(6, upDownPosition);
    }

    //This method is used to show the inv to the player and update it
    public void showOptionInv(){
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

    //This method is executed from the GlobalOptionController if someone clicks in the options
    public void handleClick(int slot){
        final int ediScreen = 4;
        final int footer = 5;
        final int header = 3;
        final int close = 49;
        HashMap<Integer, Option> optionsWithSlots = new HashMap<>();
        options.forEach((id, option) -> optionsWithSlots.put(option.getInvSlot(), option));
        if (optionsWithSlots.containsKey(slot)) {
            refreshDeepOptionInventory(optionsWithSlots.get(slot));
            ediManager.getPlayer().openInventory(deepOptionInv.getDeepOptionInv());
        }else if (Arrays.asList(ediScreen,footer,header).contains(slot)){
            updateOnTobToggle(slot);
        }else if (slot == close){
            ediManager.getPlayer().closeInventory();
        }
    }

    public void handelDeepOptionInvClick(int slot){
        final int showKeysSlot = 2;
        final int headerSlot = 3;
        final int footerSlot = 4;
        final int edInfoDisplaySlot = 5;
        final int goBackSlot = 8;
        if (Arrays.asList(showKeysSlot, headerSlot, footerSlot, edInfoDisplaySlot, goBackSlot).contains(slot)){
            if (slot == showKeysSlot){
                deepOptionInv.getOption().setShowKeys(updateButton(deepOptionInv.getOption().isShowKeys(), "Show keys", slot, deepOptionInv.getDeepOptionInv()));
            }else if (slot == edInfoDisplaySlot){
                deepOptionInv.getOption().setEdiDisplay(updateButton(deepOptionInv.getOption().isEdiDisplay(), "EDInfo-Screen", slot, deepOptionInv.getDeepOptionInv()));
            }else if (slot == headerSlot){
                deepOptionInv.getOption().setHeader(updateButton(deepOptionInv.getOption().isHeader(), "EDInfo-Header", slot, deepOptionInv.getDeepOptionInv()));
            }else if (slot == footerSlot){
                deepOptionInv.getOption().setFooter(updateButton(deepOptionInv.getOption().isFooter(), "EDInfo-Footer", slot, deepOptionInv.getDeepOptionInv()));
            }else if (slot == goBackSlot){
                showOptionInv();
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

    private int slot;

    public void registerOption(Option option){
        ArrayList<Integer> keys = new ArrayList<>();
        options.forEach((s, option1) -> keys.add(option1.getInvSlot()));
        if (options.size()==0){
            slot = 10;
            System.out.println("Br 1: " + slot + " " + option.getName());
        }else if (Arrays.asList(37,39,41).contains(slot)){
                slot = slot-25;
            System.out.println("Br 2: " + slot + " " + option.getName());
        }else if (slot<43){
            slot += 9;
            System.out.println(keys);
            System.out.println("Br 3: " + slot + " " + option.getName());
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
