package at.theduggy.edi.settings.invControllers;

import at.theduggy.edi.Main;
import at.theduggy.edi.settings.OptionManager;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;

public class SettingsInvController extends InvController{

    private Inventory settingsInv;
    private final OptionManager optionManager;

    public SettingsInvController(OptionManager optionManager){
        this.optionManager = optionManager;
        registerOption(new CordsOption("Cords", "Shows your current cords"));
        registerOption(new BiomeOption("Biome","Shows your current biome"));
        registerOption(new RealTimeOption("Real-time", "Shows you the current real-time"));
        registerOption(new IngameTimeOption("Ingame-time", "Shows you the current ingame-time"));
        registerOption(new DateOption("Date", "Shows you the current date"));
        registerOption(new ToolDurabilityOption("Item-Durabillity", "Shows you the durabillity of the item in your hand"));
    }

    private int slot;
    public void registerOption(Option option){
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
            optionManager.getRegisteredOptions().put(new NamespacedKey(Main.getPlugin(Main.class), option.getName() + "_option").toString(), option);
        }else {
            throw new IllegalArgumentException("Options are full!"); //TODO Remove !!!
        }

    }

    @Override
    public void handleClick(int slot, int BUTTON_TYPE){
        final int ediScreen = 4;
        final int footer = 5;
        final int header = 3;
        final int close = 49;
        HashMap<Integer, Option> optionsWithSlots = new HashMap<>();
        optionManager.getRegisteredOptions().forEach((id, option) -> optionsWithSlots.put(option.getInvSlot(), option));
        if (optionsWithSlots.containsKey(slot)) {
            optionManager.getOptionSettingsInvController().refresh(optionsWithSlots.get(slot));
            optionManager.getPlayer().openInventory(optionManager.getOptionSettingsInvController().getInventory());
        }else {
            switch (slot){
                case 3:
                    optionManager.setHeaderEnabled(updateButton(optionManager.isHeaderEnabled(), "EDInfo-Header", slot, settingsInv));
                    break;

                case 4:
                    optionManager.setDisplayEnabled(updateButton(optionManager.isDisplayEnabled(), "EDInfo-Screen", slot, settingsInv));
                    break;

                case 5:
                    optionManager.setFooterEnabled(updateButton(optionManager.isFooterEnabled(), "EDInfo-Footer", slot, settingsInv));
                    break;
                default:
                    optionManager.getPlayer().closeInventory();
            }
        }
    }

    public void refresh(){
        settingsInv = Bukkit.createInventory(null,54,"EDI Settings");
        addFixedButtons(Material.YELLOW_STAINED_GLASS_PANE, "EDInfo-Screen", "Enables/disbales the EDInfo-Screen", optionManager.isDisplayEnabled(), 4);
        addFixedButtons(Material.LEATHER_HELMET, "EDInfo-Header", "Enables/disbales the EDInfo-Header", optionManager.isHeaderEnabled(), 3);
        addFixedButtons(Material.LEATHER_BOOTS, "EDInfo-Footer", "Enables/disbales the EDInfo-Footer", optionManager.isFooterEnabled(), 5);

        //Crate the close-button
        ItemStack close = new ItemStack(Material.BARRIER);
        ItemMeta closeMeta = close.getItemMeta();;
        closeMeta.setDisplayName(ChatColor.DARK_GRAY + "[" + ChatColor.DARK_RED + "Close" + ChatColor.DARK_GRAY + "]");
        close.setItemMeta(closeMeta);
        settingsInv.setItem(49, close);

        for (Option option: optionManager.getRegisteredOptions().values()){
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
            settingsInv.setItem(option.getInvSlot(), sign);
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
        settingsInv.setItem(slot, itemStack);
    }

    public Inventory getInventory() {
        return settingsInv;
    }
}
