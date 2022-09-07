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
import at.theduggy.edi.settings.options.*;
import at.theduggy.edi.settings.options.durablility.*;
import at.theduggy.edi.settings.options.server.IpAddressOption;
import at.theduggy.edi.settings.options.server.PingOption;
import at.theduggy.edi.settings.options.time.DateOption;
import at.theduggy.edi.settings.options.time.IngameTimeOption;
import at.theduggy.edi.settings.options.time.RealTimeOption;
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
import java.util.Comparator;
import java.util.HashMap;

public class SettingsInvController extends InvController{

    private Inventory settingsInv;
    private final OptionManager optionManager;

    public SettingsInvController(OptionManager optionManager){
        this.optionManager = optionManager;
        registerOption(new CordsOption("Cords", "Shows your current cords", "cords"));
        registerOption(new BiomeOption("Biome","Shows your current biome", "biome"));
        registerOption(new RealTimeOption("Real-time", "Shows you the current real-time", "real_world_time"));
        registerOption(new IngameTimeOption("Ingame-time", "Shows you the current ingame-time", "ingame_world_time"));
        registerOption(new DateOption("Date", "Shows you the current date", "current_date"));
        registerOption(new ToolDurabilityOption("Item-Dur", "Shows you the durability of the item in your hand", "current_item_durability"));
        registerOption(new HelmetDurabilityOption("Helmet-Dur", "Shows you the durability of the helmet you wear", "current_helmet_durability"));
        registerOption(new ChestplateDurabilityOption("Chestplate-Dur", "Shows you the durability of the chestplate you wear", "current_chestplate_durability"));
        registerOption(new LeggingsDurability("Leggings-Dur", "Shows you the durability of the leggings you wear", "current_leggings_durability"));
        registerOption(new BootsDurabilityOption("Boots-Dur","Shows you the durability of the leggings you wear", "current_boots_durability"));
        registerOption(new IpAddressOption("Your IP", "Shows your IP-Address over witch you are connected to the server", "player_ip_address"));
        registerOption(new PingOption("Ping", "Shows your current ping", "player_ping"));
    }

    private int slot;
    public void registerOption(Option option){
        if (!Main.getConfigManager().getBlackListedOptions().contains(option.getIdentifier())){
            if (optionManager.getRegisteredOptions().size()==0){
                slot = 10;
            }else if (Arrays.asList(37,39,41).contains(slot)){
                slot = slot-25;
            }else if (slot<43){
                slot += 9;
            }
            if (slot > 0){
                if (optionManager.getDisplayIndexList().size()==0){
                    option.setDisplayIndex(1);
                }else {
                    option.setDisplayIndex(optionManager.getDisplayIndexList().get(optionManager.getDisplayIndexList().size()-1).getDisplayIndex()+1);
                }
                optionManager.getDisplayIndexList().add(option);
                Collections.sort(optionManager.getDisplayIndexList(), Comparator.comparing(Option::getDisplayIndex));
                option.setInvSlot(slot);
                optionManager.getRegisteredOptions().put(option.getIdentifier(), option);
            }
        }
    }

    @Override
    public void handleClick(int slot, int BUTTON_TYPE){
        HashMap<Integer, Option> optionsWithSlots = new HashMap<>();
        optionManager.getRegisteredOptions().forEach((id, option) -> optionsWithSlots.put(option.getInvSlot(), option));
        if (optionsWithSlots.containsKey(slot)) {
            optionManager.getOptionSettingsInvController().refresh(optionsWithSlots.get(slot));
            optionManager.getPlayer().openInventory(optionManager.getOptionSettingsInvController().getInventory());
        }else {
            switch (slot){
                case 3:
                    if (Main.getConfigManager().infoHeaderEnabled()){
                        optionManager.setHeaderEnabled(updateButton(optionManager.isHeaderEnabled(), "EDInfo-Header", slot, settingsInv));
                    }
                    break;

                case 4:
                    if (Main.getConfigManager().ediDisplayEnabled()){
                        optionManager.setDisplayEnabled(updateButton(optionManager.isDisplayEnabled(), "EDInfo-Screen", slot, settingsInv));
                    }
                    break;

                case 5:
                    if (Main.getConfigManager().infoFooterEnabled()){
                        optionManager.setFooterEnabled(updateButton(optionManager.isFooterEnabled(), "EDInfo-Footer", slot, settingsInv));
                    }
                    break;
                case 49:
                    optionManager.getPlayer().closeInventory();
            }
        }
    }

    public void refresh(){
        settingsInv = Bukkit.createInventory(null,54,"EDI Settings");
        if (Main.getConfigManager().ediDisplayEnabled()){
            addFixedButtons(Material.ORANGE_STAINED_GLASS_PANE, "EDInfo-Screen", "Enables/disbales the EDInfo-Screen", optionManager.isDisplayEnabled(), 4, false);
        }else {
            addFixedButtons(Material.RED_STAINED_GLASS_PANE, "EDInfo-Screen", "", optionManager.isDisplayEnabled(), 4, true);
        }
        if (Main.getConfigManager().infoHeaderEnabled()){
            addFixedButtons(Material.LEATHER_HELMET, "EDInfo-Header", "Enables/disbales the EDInfo-Header", optionManager.isHeaderEnabled(), 3, false);
        }else {
            addFixedButtons(Material.RED_STAINED_GLASS_PANE, "EDInfo-Header", "", optionManager.isHeaderEnabled(), 3, true);
        }
        if (Main.getConfigManager().infoFooterEnabled()){
            addFixedButtons(Material.LEATHER_BOOTS, "EDInfo-Footer", "Enables/disbales the EDInfo-Footer", optionManager.isFooterEnabled(), 5, false);
        }else {
            addFixedButtons(Material.RED_STAINED_GLASS_PANE, "EDInfo-Footer", "Enables/disbales the EDInfo-Footer", optionManager.isFooterEnabled(), 5, true);
        }



        //Create the close-button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();;
        closeMeta.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Close" + ChatColor.DARK_GRAY + "]");
        close.setItemMeta(closeMeta);
        settingsInv.setItem(49, close);

        for (Option option: optionManager.getRegisteredOptions().values()){
            //Create all options on the gui
            ItemStack sign = new ItemStack(Material.OAK_SIGN);
            ItemMeta itemMeta = sign.getItemMeta();
            itemMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC +option.getInfo()));
            itemMeta.setDisplayName(ChatColor.GOLD + option.getDisplayName());
            if (option.isFooter()||option.isHeader()||option.isEdiDisplay()){
                itemMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }
            sign.setItemMeta(itemMeta);
            settingsInv.setItem(option.getInvSlot(), sign);
        }
    }

    public void addFixedButtons(Material type, String displayName, String info, boolean enabled, int slot, boolean locked){
        ItemStack itemStack = new ItemStack(type);
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!locked){
            itemMeta.setLore(Arrays.asList( ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + info));
            if (enabled){
                itemMeta.setDisplayName(ChatColor.GREEN + displayName);
                itemMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
                itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }else {
                itemMeta.setDisplayName(ChatColor.RED + displayName);
            }
            itemStack.setItemMeta(itemMeta);
        }else {
            itemMeta.setDisplayName(ChatColor.DARK_RED + displayName);
            itemMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Locked by server-admin"));
            itemStack.setItemMeta(itemMeta);
        }
        settingsInv.setItem(slot, itemStack);
    }

    public Inventory getInventory() {
        return settingsInv;
    }
}
