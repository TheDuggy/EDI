package at.theduggy.edi.settings;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.settings.invControllers.OptionSettingsInvController;
import at.theduggy.edi.settings.invControllers.SettingsInvController;
import at.theduggy.edi.settings.options.*;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class OptionManager{

    private ArrayList<Option> displayIndexList = new ArrayList<>();
    private final OptionSettingsInvController optionSettingsInvController;
    private final SettingsInvController settingsInvController;

    //basic on-tob options
    private boolean displayEnabled = true;
    private boolean headerEnabled = false;
    private boolean footerEnabled = false;
    private HashMap<String, Option> options = new HashMap<>();

    private final EDIManager ediManager;

    public OptionManager(EDIManager ediManager){
        this.ediManager = ediManager;
        optionSettingsInvController = new OptionSettingsInvController(this);
        settingsInvController = new SettingsInvController(this);
    }

    //This method is used to show the inv to the player and update it
    public void showSettingsInv(){
        settingsInvController.refresh();
        ediManager.getPlayer().openInventory(settingsInvController.getSettingsInv());
    }
    public boolean isDisplayEnabled(){
        return displayEnabled;
    }

    public boolean isHeaderEnabled() {
        return headerEnabled;
    }

    public boolean isFooterEnabled() {
        return footerEnabled;
    }
    public boolean compareSettingsInv(Inventory toCompare){
        return toCompare.equals(settingsInvController.getSettingsInv());
    }

    public void setDisplayEnabled(boolean displayEnabled) {
        this.displayEnabled = displayEnabled;
    }

    public void setHeaderEnabled(boolean headerEnabled) {
        this.headerEnabled = headerEnabled;
    }

    public void setFooterEnabled(boolean footerEnabled) {
        this.footerEnabled = footerEnabled;
    }

    public boolean compareDeepOptionIv(Inventory inventory){
        if (optionSettingsInvController ==null){
            return false;
        }else {
            return inventory.equals(optionSettingsInvController.getOptionSettingsInv());
        }
    }

    public HashMap<String, Option> getRegisteredOptions() {
        return options;
    }

    public void setOptions(HashMap<String, Option> options) {
        this.options = options;
    }

    public List<Option> getDisplayIndexList(){
        return displayIndexList;
    }

    public void setDisplayIndexList(ArrayList<Option> displayIndexList) {
        this.displayIndexList = displayIndexList;
    }

    public OptionSettingsInvController getOptionSettingsInvController() {
        return optionSettingsInvController;
    }

    public SettingsInvController getSettingsInvController() {
        return settingsInvController;
    }

    public EDIManager getEdiManager() {
        return ediManager;
    }
}
