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
package at.theduggy.edi.settings.options;


import org.bukkit.inventory.meta.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ToolDurabilityOption extends Option{
    public ToolDurabilityOption(String optionDisplayName, String optionInfo, String optionIdentifier) {
        super(optionDisplayName, optionInfo, optionIdentifier);
    }

    @Override
    public String getValue(Player player) {
        String result = "---";
        ItemStack tool = player.getInventory().getItemInMainHand();
        if (tool.getItemMeta() instanceof Damageable&&tool.getType().getMaxDurability()>0){
            ItemMeta toolMeta = tool.getItemMeta();
            int durability = tool.getType().getMaxDurability()-((Damageable) toolMeta).getDamage();
            double percent = 100-(((org.bukkit.inventory.meta.Damageable) toolMeta).getDamage()*100.0)/tool.getType().getMaxDurability();
            result = durability + "/" + tool.getType().getMaxDurability() + " (" + String.format("%.2f", percent) + "%)";
        }
        return result;
    }
}
