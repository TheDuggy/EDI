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
package at.theduggy.edi.settings;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.settings.invControllers.OptionSettingsInvController;
import at.theduggy.edi.settings.invControllers.SettingsInvController;
import at.theduggy.edi.settings.invControllers.fontSettingsInvController.FontSettingsInvController;
import at.theduggy.edi.settings.options.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.*;

public class OptionManager{

    private ArrayList<Option> displayIndexList = new ArrayList<>();
    private final OptionSettingsInvController optionSettingsInvController;
    private final SettingsInvController settingsInvController;
    private final FontSettingsInvController keyFontSettingsInvController;
    private final FontSettingsInvController separatorFontSettingsInvController;
    private final FontSettingsInvController valueFontSettingsInvController;
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
        keyFontSettingsInvController = new FontSettingsInvController(this, "key");
        separatorFontSettingsInvController = new FontSettingsInvController(this, "separator");
        valueFontSettingsInvController = new FontSettingsInvController(this, "value");
    }

    //This method is used to show the inv to the player and update it
    public void showSettingsInv(){
        settingsInvController.refresh();
        ediManager.getPlayer().openInventory(settingsInvController.getInventory());
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

    public void setDisplayEnabled(boolean displayEnabled) {
        this.displayEnabled = displayEnabled;
    }

    public void setHeaderEnabled(boolean headerEnabled) {
        this.headerEnabled = headerEnabled;
    }

    public void setFooterEnabled(boolean footerEnabled) {
        this.footerEnabled = footerEnabled;
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

    public FontSettingsInvController getKeyFontSettingsInvController() {
        return keyFontSettingsInvController;
    }

    public FontSettingsInvController getSeparatorFontSettingsInvController() {
        return separatorFontSettingsInvController;
    }

    public FontSettingsInvController getValueFontSettingsInvController() {
        return valueFontSettingsInvController;
    }

    public Player getPlayer(){
        return ediManager.getPlayer();
    }
}
