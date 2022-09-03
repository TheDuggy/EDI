/*
EDI: Extended Debug Info
Copyright (C) 2022  Georg Kollegger(TheDuggy/CoderTheDuggy)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package at.theduggy.edi.settings.invControllers;

import org.bukkit.ChatColor;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public abstract class InvController {

    public abstract Inventory getInventory();

    public abstract void handleClick(int slot, int BUTTON_TYPE);

    public boolean updateButton(boolean enabled, String displayName, int slot, Inventory inv){
        ItemStack itemStack = inv.getItem(slot);
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
