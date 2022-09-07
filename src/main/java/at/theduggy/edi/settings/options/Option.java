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
package at.theduggy.edi.settings.options;

import at.theduggy.edi.settings.invControllers.fontSettingsInvController.FontData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public abstract class Option {

    private FontData separatorFontData;
    private FontData keyFontData;
    private FontData valueFontData;
    private boolean showKeys;
    private boolean header;
    private boolean footer;
    private boolean ediDisplay;
    private final String optionIdentifierName;
    private final String optionInfo;
    private String optionKeyDisplayName;

    private final String displayName;

    private int displayIndex;

    private int invSlot;


    public Option(String displayName, String optionInfo , String optionIdentifierName) {
        this.displayName = displayName;
        this.keyFontData = new FontData(ChatColor.GRAY, false, false, false);
        this.separatorFontData = new FontData(ChatColor.GRAY, false, false, false);
        this.valueFontData = new FontData(ChatColor.GOLD, false, false, false);
        this.optionInfo = optionInfo;
        this.optionIdentifierName = "included:" + optionIdentifierName;
        this.optionKeyDisplayName = displayName;
        this.header = false;
        this.footer = false;
        this.ediDisplay = true;
        this.showKeys = true;
    }

    public boolean isHeader() {
        return header;
    }

    public boolean isFooter() {
        return footer;
    }

    public boolean isEdiDisplay() {
        return ediDisplay;
    }

    public boolean isShowKeys() {
        return showKeys;
    }

    public void setHeader(boolean header) {
        this.header = header;
    }

    public void setFooter(boolean footer) {
        this.footer = footer;
    }

    public void setEdiDisplay(boolean ediDisplay) {
        this.ediDisplay = ediDisplay;
    }

    public void setShowKeys(boolean showKeys) {
        this.showKeys = showKeys;
    }

    public String getIdentifier() {
        return optionIdentifierName;
    }

    public String getKeyDisplayName() {
        return optionKeyDisplayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getInvSlot() {
        return invSlot;
    }

    public void setInvSlot(int slot){
        this.invSlot = slot;
    }

    public String getInfo() {
        return optionInfo;
    }

    public abstract String getValue(Player player);

    public void setDisplayIndex(int displayIndex) {
        this.displayIndex = displayIndex;
    }

    public int getDisplayIndex() {
        return displayIndex;
    }

    public FontData getSeparatorFontData() {
        return separatorFontData;
    }

    public FontData getKeyFontData() {
        return keyFontData;
    }

    public FontData getValueFontData() {
        return valueFontData;
    }

    public void setSeparatorFontData(FontData separatorFontData) {
        this.separatorFontData = separatorFontData;
    }

    public void setKeyFontData(FontData keyFontData) {
        this.keyFontData = keyFontData;
    }

    public void setValueFontData(FontData valueFontData) {
        this.valueFontData = valueFontData;
    }

    public void setKeyDisplayName(String optionKeyDisplayName) {
        this.optionKeyDisplayName = optionKeyDisplayName;
    }
}
