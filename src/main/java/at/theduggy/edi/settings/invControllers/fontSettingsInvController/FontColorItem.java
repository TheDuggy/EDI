package at.theduggy.edi.settings.invControllers.fontSettingsInvController;

import org.apache.commons.lang.ArrayUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FontColorItem {

    private String baseName;
    private ChatColor selectedColor;
    private ArrayList<ChatColor> chatColors;

    private boolean singleColor;
    private boolean enabled;

    private ItemStack itemStack;

    public FontColorItem(Material type, ChatColor baseColor, ChatColor ... chatColors){
        String baseName = transformColorToString(baseColor);
        this.baseName=baseName;
        this.chatColors = new ArrayList<>(List.of((ChatColor[]) ArrayUtils.addAll(chatColors, new ChatColor[]{baseColor})));
        this.singleColor = this.chatColors.size()==1;
        this.itemStack = new ItemStack(type);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(baseColor + baseName);
        ArrayList<String> lore = new ArrayList<>();
        if (!singleColor){
            for (ChatColor chatColor : this.chatColors) {
                String toAdd = "";

                if (enabled) {
                    if (chatColor == selectedColor) {
                        toAdd = ChatColor.GOLD + "" + ChatColor.BOLD + ">" + chatColor + transformColorToString(chatColor);
                    } else {
                        toAdd = " " + chatColor + transformColorToString(chatColor);
                    }
                }else {
                    toAdd = " " + chatColor + transformColorToString(chatColor);
                }
                lore.add(toAdd);
            }
        }else {
            String colorName = "";
            if (this.chatColors.get(0) == ChatColor.LIGHT_PURPLE){
                colorName = "Pink";
            }else if (this.chatColors.get(0) == ChatColor.DARK_PURPLE){
                colorName = "Magenta";
            }else {
                colorName = transformColorToString(this.chatColors.get(0));
            }
            itemMeta.setDisplayName(this.chatColors.get(0) + colorName);
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
    }

    private String transformColorToString(ChatColor color){
        String s = color.name().toLowerCase();
        s = s.replace('_',' ');
        char[] chars = s.toCharArray();
        chars[0] = Character.toUpperCase(chars[0]);
        s = new String(chars);
        return s;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public ChatColor select(ChatColor selectedColor){
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ArrayList<String> lore = new ArrayList<>();
        if (!singleColor){
            for (ChatColor chatColor:chatColors){
                if (chatColor == selectedColor){
                    lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + ">" + chatColor + transformColorToString(chatColor));
                }else {
                    lore.add(" " + chatColor + transformColorToString(chatColor));
                }
            }
        }
        this.selectedColor = selectedColor;
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return selectedColor;
    }

    public ItemStack unselect(){
        ItemMeta itemMeta = itemStack.getItemMeta();
        ArrayList<String> lore = new ArrayList<>();
        if (!singleColor){
            for (ChatColor chatColor:chatColors){
                lore.add(" " + chatColor + transformColorToString(chatColor));
            }
        }
        itemMeta.setLore(lore);
        if (itemMeta.hasEnchant(Enchantment.KNOCKBACK)){
            itemMeta.removeEnchant(Enchantment.KNOCKBACK);
        }
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public ArrayList<ChatColor> getChatColors() {
        return chatColors;
    }

    public ChatColor getNextChatColorAndRender(){
        ChatColor color;
        if (chatColors.indexOf(selectedColor)==chatColors.size()-1){
            color = chatColors.get(0);
        }else{
            color = chatColors.get(chatColors.indexOf(selectedColor)+1);
        }

        this.selectedColor = color;
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.KNOCKBACK, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        ArrayList<String> lore = new ArrayList<>();
        if (!singleColor){
            for (ChatColor chatColor:chatColors){
                if (chatColor == selectedColor){
                    lore.add(ChatColor.GOLD + "" + ChatColor.BOLD + ">" + chatColor + transformColorToString(chatColor));
                }else {
                    lore.add(" " + chatColor + transformColorToString(chatColor));
                }
            }
        }
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);

        return color;
    }
}
