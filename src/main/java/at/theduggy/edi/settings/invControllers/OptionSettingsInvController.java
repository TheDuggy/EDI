/*
 * EDI: Extended Debug Info
 * Copyright (C) 2022  Georg Kollegger(TheDuggy/CoderTheDuggy)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.theduggy.edi.settings.invControllers;

import at.theduggy.edi.Main;
import at.theduggy.edi.settings.OptionManager;
import at.theduggy.edi.settings.options.Option;
import net.md_5.bungee.api.chat.*;
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

public class OptionSettingsInvController extends InvController{
    private Inventory optionSettingsInv;
    private Option option;
    private final OptionManager optionManager;

    public OptionSettingsInvController(OptionManager optionManager) {
        this.optionManager = optionManager;
    }

    public Inventory getInventory() {
        return optionSettingsInv;
    }

    public void refresh(Option o){
        this.option = o;
        String optionName = o.getKeyDisplayName();
        optionSettingsInv = Bukkit.createInventory(null,18, "[EDI] " + optionName);
        ItemStack header = new ItemStack(Material.LEATHER_HELMET);
        ItemStack footer = new ItemStack(Material.LEATHER_BOOTS);
        ItemStack ediDisplay = new ItemStack(Material.YELLOW_STAINED_GLASS_PANE);
        ItemStack showKeys = new ItemStack(Material.TRIPWIRE_HOOK);
        ItemStack back = new ItemStack(Material.ARROW);
        ItemStack upDownPosition = new ItemStack(Material.MAGENTA_GLAZED_TERRACOTTA);
        ItemStack keyFontSettings = new ItemStack(Material.WRITABLE_BOOK);
        ItemStack separatorFontSettings = new ItemStack(Material.WRITABLE_BOOK);
        ItemStack valueFontSettings = new ItemStack(Material.WRITABLE_BOOK);
        ItemStack changeKeyDisplayName = new ItemStack(Material.FEATHER);
        ItemMeta changeKeyDisplayNameMeta = changeKeyDisplayName.getItemMeta();
        ItemMeta keyFontSettingsMeta = keyFontSettings.getItemMeta();
        ItemMeta separatorFontSettingsMeta = separatorFontSettings.getItemMeta();
        ItemMeta valueFontSettingsMeta = valueFontSettings.getItemMeta();
        ItemMeta upDownPositionMeta = upDownPosition.getItemMeta();
        ItemMeta backMeta = back.getItemMeta();
        ItemMeta headerMeta = header.getItemMeta();
        ItemMeta footerMeta = footer.getItemMeta();
        ItemMeta ediDisplayMeta = ediDisplay.getItemMeta();
        ItemMeta showKeysMeta = showKeys.getItemMeta();
        if (o.isShowKeys()){
            showKeysMeta.setDisplayName(ChatColor.GREEN + "Show keys");
            showKeysMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            showKeysMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }else {
            showKeysMeta.setDisplayName(ChatColor.RED + "Show keys");
        }
        showKeysMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Shows the option " + optionName + " in key:value pairs if true"));

        if (o.isEdiDisplay()){
            ediDisplayMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            ediDisplayMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            ediDisplayMeta.setDisplayName(ChatColor.GREEN + "EDInfo-Screen");
        }else {
            ediDisplayMeta.setDisplayName(ChatColor.RED + "EDInfo-Screen");
        }
        ediDisplayMeta.setLore(Collections.singletonList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Shows " + optionName + " on the EDInfo-Screen"));

        if (o.isFooter()){
            footerMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
            footerMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            footerMeta.setDisplayName(ChatColor.GREEN + "EDInfo-Footer");
        }else {
            footerMeta.setDisplayName(ChatColor.RED + "EDInfo-Footer");
        }
        footerMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        footerMeta.setLore(Collections.singletonList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Shows " + optionName + " on the player-list-footer"));

        if (o.isHeader()){
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

        keyFontSettingsMeta.setDisplayName(ChatColor.AQUA + "Key-font-settings");
        keyFontSettingsMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Current: ", o.getKeyFontData().format("Key") + o.getSeparatorFontData().format(": ") + o.getValueFontData().format("Value")));

        separatorFontSettingsMeta.setDisplayName(ChatColor.AQUA + "Separator-font-settings");
        separatorFontSettingsMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Current: ", o.getKeyFontData().format("Key") + o.getSeparatorFontData().format(": ") + o.getValueFontData().format("Value")));

        valueFontSettingsMeta.setDisplayName(ChatColor.AQUA + "Value-font-settings");
        valueFontSettingsMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Current: ", o.getKeyFontData().format("Key") + o.getSeparatorFontData().format(": ") + o.getValueFontData().format("Value")));

        changeKeyDisplayNameMeta.setDisplayName(ChatColor.AQUA + "Change key-display-name");
        changeKeyDisplayNameMeta.setLore(Arrays.asList(
                ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Current: " + option.getKeyFontData().format(option.getKeyDisplayName()),
                ChatColor.DARK_GRAY + "Click at the chat-msg you receive,",
                ChatColor.DARK_GRAY + "enter the new name into the chat",
                ChatColor.DARK_GRAY + "and run it"
        ));

        changeKeyDisplayName.setItemMeta(changeKeyDisplayNameMeta);
        keyFontSettings.setItemMeta(keyFontSettingsMeta);
        valueFontSettings.setItemMeta(valueFontSettingsMeta);
        separatorFontSettings.setItemMeta(separatorFontSettingsMeta);
        header.setItemMeta(headerMeta);
        footer.setItemMeta(footerMeta);
        ediDisplay.setItemMeta(ediDisplayMeta);
        showKeys.setItemMeta(showKeysMeta);
        back.setItemMeta(backMeta);
        upDownPosition.setItemMeta(upDownPositionMeta);

        optionSettingsInv.setItem(15, valueFontSettings);
        optionSettingsInv.setItem(13, separatorFontSettings);
        optionSettingsInv.setItem(11, keyFontSettings);
        optionSettingsInv.setItem(2, showKeys);
        optionSettingsInv.setItem(3, header);
        optionSettingsInv.setItem(4, footer);
        optionSettingsInv.setItem(5, ediDisplay);
        optionSettingsInv.setItem(17, back);
        optionSettingsInv.setItem(9, changeKeyDisplayName);
        optionSettingsInv.setItem(6, upDownPosition);
    }

    @Override
    public void handleClick(int slot, int BUTTON_TYPE){
        final int showKeysSlot = 2;
        final int headerSlot = 3;
        final int footerSlot = 4;
        final int edInfoDisplaySlot = 5;
        final int goBackSlot = 17;
        final int changePosition = 6;
        final int keyFontSettings = 11;
        final int separatorFontSettings = 13;
        final int valueFontSettings = 15;
        final int changeKeyDisplayName = 9;
        if (slot == showKeysSlot){
            option.setShowKeys(updateButton(option.isShowKeys(), "Show-keys", slot, optionSettingsInv));
        }else if (slot == edInfoDisplaySlot){
            option.setEdiDisplay(updateButton(option.isEdiDisplay(), "EDInfo-Screen", slot, optionSettingsInv));
        }else if (slot == headerSlot){
            option.setHeader(updateButton(option.isHeader(), "EDInfo-Header", slot, optionSettingsInv));
        }else if (slot == footerSlot){
            option.setFooter(updateButton(option.isFooter(), "EDInfo-Footer", slot, optionSettingsInv));
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
        }else if (slot == keyFontSettings){
            optionManager.getKeyFontSettingsInvController().refresh(option, option.getKeyFontData());
            optionManager.getPlayer().openInventory(optionManager.getKeyFontSettingsInvController().getInventory());
        }else if (slot == separatorFontSettings){
            optionManager.getSeparatorFontSettingsInvController().refresh(option, option.getSeparatorFontData());
            optionManager.getPlayer().getPlayer().openInventory(optionManager.getSeparatorFontSettingsInvController().getInventory());
        }else if (slot == valueFontSettings){
            optionManager.getValueFontSettingsInvController().refresh(option, option.getValueFontData());
            optionManager.getPlayer().openInventory(optionManager.getValueFontSettingsInvController().getInventory());
        }else if (slot == changeKeyDisplayName){
            ComponentBuilder componentBuilder = new ComponentBuilder(Main.getPrefix(true));
            TextComponent clickable = new TextComponent(ChatColor.AQUA + "Click here to change key-display-name for option\n        " + ChatColor.GOLD + option.getIdentifier());
            clickable.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/edi-change-key-display-name " + option.getIdentifier() + " "));
            clickable.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "After clicking, the chat will be opened for you so you can enter the new name!").create()));
            componentBuilder.append(clickable);
            optionManager.getPlayer().spigot().sendMessage(componentBuilder.create());
        }
    }
}
