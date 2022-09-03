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