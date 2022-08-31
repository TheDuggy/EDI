package at.theduggy.edi.settings.invControllers;

import at.theduggy.edi.settings.OptionManager;
import at.theduggy.edi.settings.options.Option;
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
import java.util.Collections;
import java.util.Comparator;

public class OptionSettingsInvController {
    private Inventory optionSettingsInv;
    private Option option;
    private final OptionManager optionManager;

    public OptionSettingsInvController(OptionManager optionManager) {
        this.optionManager = optionManager;
    }

    public Inventory getOptionSettingsInv() {
        return optionSettingsInv;
    }

    public void refresh(Option option){
        String optionName = option.getName();
        optionSettingsInv = Bukkit.createInventory(null,9, "[EDI] " + optionName);
        this.option = option;
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
        upDownPositionMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "RIGHT CLICK " + ChatColor.GOLD + "▲", ChatColor.DARK_GRAY + "LEFT CLICK  " + ChatColor.GOLD + "▼"));

        header.setItemMeta(headerMeta);
        footer.setItemMeta(footerMeta);
        ediDisplay.setItemMeta(ediDisplayMeta);
        showKeys.setItemMeta(showKeysMeta);
        back.setItemMeta(backMeta);
        upDownPosition.setItemMeta(upDownPositionMeta);

        optionSettingsInv.setItem(2, showKeys);
        optionSettingsInv.setItem(3, header);
        optionSettingsInv.setItem(4, footer);
        optionSettingsInv.setItem(5, ediDisplay);
        optionSettingsInv.setItem(8, back);
        optionSettingsInv.setItem(6, upDownPosition);
    }
    
    public void handleClick(int slot, int BUTTON_TYPE){
        final int showKeysSlot = 2;
        final int headerSlot = 3;
        final int footerSlot = 4;
        final int edInfoDisplaySlot = 5;
        final int goBackSlot = 8;
        final int changePosition = 6;
        if (Arrays.asList(showKeysSlot, headerSlot, footerSlot, edInfoDisplaySlot, goBackSlot, changePosition).contains(slot)){
            if (slot == showKeysSlot){
                option.setShowKeys(updateButton(option.isShowKeys(), "Show keys", slot));
            }else if (slot == edInfoDisplaySlot){
                option.setEdiDisplay(updateButton(option.isEdiDisplay(), "EDInfo-Screen", slot));
            }else if (slot == headerSlot){
                option.setHeader(updateButton(option.isHeader(), "EDInfo-Header", slot));
            }else if (slot == footerSlot){
                option.setFooter(updateButton(option.isFooter(), "EDInfo-Footer", slot));
            }else if (slot == goBackSlot){
                optionManager.showSettingsInv();
            }else if (slot == changePosition){
                if (BUTTON_TYPE == 0){//Left-click
                    if (option.getDisplayIndex()==1){
                        option.setDisplayIndex(optionManager.getDisplayIndexList().size());
                        ArrayList<Option> temp = new ArrayList<>();
                        optionManager.getDisplayIndexList().remove(option);
                        temp.addAll(optionManager.getDisplayIndexList());
                        temp.add(option);
                        optionManager.setDisplayIndexList(temp);
                        for (int i = optionManager.getDisplayIndexList().size()-2;i>=0;i--){
                            optionManager.getDisplayIndexList().get(i).setDisplayIndex(optionManager.getDisplayIndexList().get(i+1).getDisplayIndex()-1);
                        }
                        optionManager.getDisplayIndexList().forEach(option1 -> optionManager.getEdiManager().getPlayer().sendMessage(option1.getName()));
                        Collections.sort(optionManager.getDisplayIndexList(), Comparator.comparing(Option::getDisplayIndex));
                    }else {
                        option.setDisplayIndex(option.getDisplayIndex() - 1);
                        optionManager.getDisplayIndexList().get(optionManager.getDisplayIndexList().indexOf(option)-1).setDisplayIndex(optionManager.getDisplayIndexList().get(optionManager.getDisplayIndexList().indexOf(option)-1).getDisplayIndex()+1);
                    }
                    Collections.sort(optionManager.getDisplayIndexList(), Comparator.comparing(Option::getDisplayIndex));
                }else {//Right-click
                    if (option.getDisplayIndex()==optionManager.getDisplayIndexList().size()){
                        option.setDisplayIndex(1);
                        ArrayList<Option> temp = new ArrayList<>();
                        temp.add(option);
                        optionManager.getDisplayIndexList().remove(option);
                        temp.addAll(optionManager.getDisplayIndexList());
                        optionManager.setDisplayIndexList(temp);
                        for (int i = 1;i<optionManager.getDisplayIndexList().size();i++){
                            optionManager.getDisplayIndexList().get(i).setDisplayIndex(optionManager.getDisplayIndexList().get(i-1).getDisplayIndex()+1);
                        }
                    }else {
                        option.setDisplayIndex(option.getDisplayIndex() + 1);
                        optionManager.getDisplayIndexList().get(optionManager.getDisplayIndexList().indexOf(option)+1).setDisplayIndex(optionManager.getDisplayIndexList().get(optionManager.getDisplayIndexList().indexOf(option)+1).getDisplayIndex()-1);
                    }
                    Collections.sort(optionManager.getDisplayIndexList(), Comparator.comparing(Option::getDisplayIndex));
                }
            }
        }
    }

    public boolean updateButton(boolean enabled, String displayName, int slot){
        ItemStack itemStack = optionSettingsInv.getItem(slot);
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
}
