package at.theduggy.edi.settings;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;

public class SettingsInv {

    private boolean show;
    private boolean showCords;

    private final Inventory inventory;

    public SettingsInv(){
        this.inventory = Bukkit.createInventory(null, 54,"EDI Settings");
    }

    public void show(Player player){
        addSettingsOption(10,showCords, ChatColor.GOLD + "Cords",     ChatColor.DARK_GRAY + "" + ChatColor.ITALIC + "Shows you your current coordinates!");
        addShowButton();
        player.openInventory(inventory);
    }

    public Inventory getInventory() {
        return inventory;
    }

    private void addSettingsOption(int slot,boolean enabled,String name, String info){
        ItemStack yes = new ItemStack(Material.GREEN_CONCRETE);
        ItemMeta yesMeta = yes.getItemMeta();
        yesMeta.setDisplayName(ChatColor.GREEN + "Enable");
        ItemStack no = new ItemStack(Material.RED_CONCRETE);
        ItemMeta noMeta = no.getItemMeta();
        noMeta.setDisplayName(ChatColor.RED + "Disable");
        ItemStack optionInfo = new ItemStack(Material.SIGN);
        ItemMeta optionInfoMeta = optionInfo.getItemMeta();
        optionInfoMeta.setDisplayName(name);
        optionInfoMeta.setLore(new ArrayList<>(Arrays.asList(info)));
        optionInfo.setItemMeta(optionInfoMeta);
        if (enabled){
            yes.addUnsafeEnchantment(Enchantment.KNOCKBACK,1);
            //yesMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            System.out.println(yesMeta.getEnchants());
        }else {
            no.addUnsafeEnchantment(Enchantment.KNOCKBACK,1);
            //noMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            System.out.println(noMeta.getEnchants());
        }

        yes.setItemMeta(yesMeta);
        no.setItemMeta(noMeta);
        inventory.setItem(slot, optionInfo);
        inventory.setItem(slot+1, yes);
        inventory.setItem(slot+2, no);
    }

    public void addShowButton(){
        ItemStack button = new ItemStack(show?Material.GREEN_STAINED_GLASS_PANE:Material.RED_STAINED_GLASS_PANE);
        ItemMeta meta = button.getItemMeta();
        meta.setDisplayName(show?ChatColor.GREEN + "Enabled":ChatColor.RED + "Disabled");
        meta.setLore(Arrays.asList(show?ChatColor.DARK_GRAY + "EDI is shown!":ChatColor.DARK_GRAY + "EDI is hidden!"));
        button.setItemMeta(meta);
        inventory.setItem(4, button);
    }

    public void onClick(int slot, Player player){
        switch (slot){
            case 4:
                show = !show;
                show(player);

            case 11 : //turn show-cords on
                if (!showCords){
                    showCords = true;
                }
                show(player);
                break;
            case 12: //turn show-cords off
                if (showCords){
                    showCords = false;
                }
                show(player);
                break;
        }
    }


}
