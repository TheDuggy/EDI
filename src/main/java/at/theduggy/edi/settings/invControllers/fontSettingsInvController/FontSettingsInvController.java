package at.theduggy.edi.settings.invControllers.fontSettingsInvController;

import at.theduggy.edi.settings.OptionManager;
import at.theduggy.edi.settings.invControllers.InvController;
import at.theduggy.edi.settings.options.Option;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.HashMap;

public class FontSettingsInvController extends InvController {

    private final OptionManager optionManager;
    private Option option;
    private FontData fontData;
    private Inventory fontSettingsInv;
    private String settingsType;
    private HashMap<Integer, FontColorItem> fontColorItems  = new HashMap<>();
    public FontSettingsInvController(OptionManager optionManager, String settingsType){
        this.settingsType = settingsType;
        this.optionManager = optionManager;
    }

    @Override
    public Inventory getInventory(){
        return fontSettingsInv;
    }

    public void refresh(Option option, FontData fontData){
        this.option = option;
        this.fontData = fontData;
        fontColorItems.clear();
        fontSettingsInv = Bukkit.createInventory(null, 36, "[Edi-font-settings] " + option.getDisplayName());
        ItemStack infoBook = new ItemStack(Material.LEGACY_BOOK_AND_QUILL);
        ItemStack back = new ItemStack(Material.ARROW);
        ItemMeta backMeta = back.getItemMeta();
        ItemMeta infoBookMeta = infoBook.getItemMeta();
        if (settingsType.equals("key")){
            infoBookMeta.setDisplayName(ChatColor.GOLD + "Key-font-settings for " + option.getDisplayName());
            infoBookMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Font-settings for the key:", ChatColor.GREEN + "Key" + ChatColor.DARK_GRAY + ": Value"));
        }else if (settingsType.equals("separator")){
            infoBookMeta.setDisplayName(ChatColor.GOLD + "Separator-font-settings for " + option.getDisplayName());
            infoBookMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Font-settings for the separator:", ChatColor.DARK_GRAY + "Key" + ChatColor.GREEN + "" + ChatColor.BOLD + ":" + ChatColor.DARK_GRAY + "Value"));
        }else if (settingsType.equals("value")){
            infoBookMeta.setDisplayName(ChatColor.GOLD + "Value-font-settings for " + option.getDisplayName());
            infoBookMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Font-settings for the value:", ChatColor.DARK_GRAY + "Key: " + ChatColor.GREEN + "Value"));
        }
        backMeta.setDisplayName( ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Go back" + ChatColor.DARK_GRAY + "]");
        backMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Go back to EDI-Option-Settings"));
        infoBook.setItemMeta(infoBookMeta);
        back.setItemMeta(backMeta);
        FontColorItem red = new FontColorItem(Material.RED_STAINED_GLASS_PANE, ChatColor.RED, ChatColor.DARK_RED);
        fontColorItems.put(10,red);
        FontColorItem gold = new FontColorItem(Material.ORANGE_STAINED_GLASS_PANE,  ChatColor.GOLD);
        fontColorItems.put(11,gold);
        FontColorItem yellow = new FontColorItem(Material.YELLOW_STAINED_GLASS_PANE, ChatColor.YELLOW);
        fontColorItems.put(12,yellow);
        FontColorItem green = new FontColorItem(Material.LIME_STAINED_GLASS_PANE, ChatColor.GREEN, ChatColor.DARK_GREEN);
        fontColorItems.put(13,green);
        FontColorItem blue = new FontColorItem(Material.BLUE_STAINED_GLASS_PANE, ChatColor.BLUE, ChatColor.DARK_BLUE, ChatColor.AQUA, ChatColor.DARK_AQUA);
        fontColorItems.put(14,blue);
        FontColorItem magenta = new FontColorItem(Material.MAGENTA_STAINED_GLASS_PANE, ChatColor.DARK_PURPLE);
        fontColorItems.put(15, magenta);
        FontColorItem pink = new FontColorItem(Material.PINK_STAINED_GLASS_PANE, ChatColor.LIGHT_PURPLE);
        fontColorItems.put(16, pink);
        FontColorItem darkGray = new FontColorItem(Material.BLACK_STAINED_GLASS_PANE, ChatColor.DARK_GRAY);
        fontColorItems.put(21,darkGray);
        FontColorItem lightGray = new FontColorItem(Material.GRAY_STAINED_GLASS_PANE, ChatColor.GRAY);
        fontColorItems.put(22, lightGray);
        FontColorItem white = new FontColorItem(Material.WHITE_STAINED_GLASS_PANE, ChatColor.WHITE);
        fontColorItems.put(23,white);
        ItemStack bold = new ItemStack(Material.ANVIL);
        ItemMeta boldMeta = bold.getItemMeta();
        ItemStack italic = new ItemStack(Material.STICK);
        ItemMeta italicMeta = italic.getItemMeta();
        ItemStack underlined = new ItemStack(Material.ACACIA_SLAB);
        ItemMeta underlinedMeta = underlined.getItemMeta();
        if (fontData.isBold()){
            boldMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            boldMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        boldMeta.setDisplayName(ChatColor.AQUA + "Bold");
        boldMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Makes the " + settingsType  + ChatColor.GOLD + " " + ChatColor.BOLD + "BOLD" + ChatColor.RESET));
        bold.setItemMeta(boldMeta);
        if (fontData.isItalic()){
            italicMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            italicMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        italicMeta.setDisplayName(ChatColor.AQUA + "Italic");
        italicMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "Makes the " + settingsType  + ChatColor.GOLD + " " + ChatColor.ITALIC + "ITALIC" + ChatColor.RESET));
        italic.setItemMeta(italicMeta);

        if (fontData.isUnderlined()){
            underlinedMeta.addEnchant(Enchantment.KNOCKBACK,1,true);
            underlinedMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        underlinedMeta.setDisplayName(ChatColor.AQUA + "Underlined");
        underlinedMeta.setLore(Arrays.asList(ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Makes the " + settingsType  + ChatColor.GOLD + " " + ChatColor.UNDERLINE + "UNDERLINED" + ChatColor.RESET));
        underlined.setItemMeta(underlinedMeta);

        HashMap<ChatColor, FontColorItem> reversedFontColorItemMap = new HashMap<>();
        fontColorItems.forEach((integer, fontColorItem) -> fontColorItem.getChatColors().forEach(chatColor -> reversedFontColorItemMap.put(chatColor, fontColorItem)));
        reversedFontColorItemMap.get(fontData.getColor()).select(fontData.getColor());
        for (Integer k: fontColorItems.keySet()){
            fontSettingsInv.setItem(k, fontColorItems.get(k).getItemStack());
        }
        fontSettingsInv.setItem(33, bold);
        fontSettingsInv.setItem(4, infoBook);
        fontSettingsInv.setItem(35,back);
        fontSettingsInv.setItem(29, italic);
        fontSettingsInv.setItem(31, underlined);
    }

    @Override
    public void handleClick(int slot, int BUTTON_TYPE){
        if (slot == 35){ //Return to the option-settings-inv
            optionManager.getOptionSettingsInvController().refresh(option);
            optionManager.getPlayer().openInventory(optionManager.getOptionSettingsInvController().getInventory());
        }else if (fontColorItems.containsKey(slot)){
            for (Integer k:fontColorItems.keySet()){
                fontSettingsInv.setItem(k,fontColorItems.get(k).unselect());
            }
            ChatColor newColor = fontColorItems.get(slot).getNextChatColorAndRender();
            fontSettingsInv.setItem(slot, fontColorItems.get(slot).getItemStack());
            fontData.setColor(newColor);
        }else if (slot == 33){
            fontData.setBold(updateButton(fontData.isBold(), ChatColor.AQUA + "Bold", 33, fontSettingsInv));
        }else if (slot == 29){
            fontData.setItalic(updateButton(fontData.isItalic(), ChatColor.AQUA + "Italic", 29, fontSettingsInv));
        }else if (slot == 31){
            fontData.setUnderlined(updateButton(fontData.isUnderlined(), ChatColor.AQUA + "Underlined", 31, fontSettingsInv));
        }
    }
}
