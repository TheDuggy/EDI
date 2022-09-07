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
package at.theduggy.edi.storage.dataObj;

import at.theduggy.edi.settings.invControllers.fontSettingsInvController.FontData;
import at.theduggy.edi.settings.options.Option;

public class OptionStorageData {

    private boolean showKeys;
    private boolean header;
    private boolean footer;
    private boolean ediDisplay;

    private int displayIndex;

    private String keyDisplayName;
    private FontData keyFontData;

    private FontData separatorFontData;

    private FontData valueFontData;

    public OptionStorageData(Option option){
        this.showKeys = option.isShowKeys();
        this.header = option.isHeader();
        this.footer = option.isFooter();
        this.ediDisplay = option.isEdiDisplay();
        this.displayIndex = option.getDisplayIndex();
        this.keyFontData = option.getKeyFontData();
        this.separatorFontData = option.getSeparatorFontData();
        this.valueFontData = option.getValueFontData();
        this.keyDisplayName = option.getKeyDisplayName();
    }

    public OptionStorageData(boolean showKeys, boolean header, boolean footer, boolean ediDisplay) {
        this.showKeys = showKeys;
        this.header = header;
        this.footer = footer;
        this.ediDisplay = ediDisplay;
    }

    public void applyDataToOption(Option option){
        System.out.println(header);
        option.setShowKeys(showKeys);
        option.setHeader(header);
        option.setFooter(footer);
        option.setEdiDisplay(ediDisplay);
        option.setDisplayIndex(displayIndex);
        option.setKeyFontData(keyFontData);
        option.setSeparatorFontData(separatorFontData);
        option.setValueFontData(valueFontData);
        option.setKeyDisplayName(keyDisplayName);
    }

    public FontData getKeyFontData() {
        return keyFontData;
    }

    public FontData getSeparatorFontData() {
        return separatorFontData;
    }

    public FontData getValueFontData() {
        return valueFontData;
    }

    public boolean isShowKeys() {
        return showKeys;
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

    public int getDisplayIndex() {
        return displayIndex;
    }

    public String getKeyDisplayName() {
        return keyDisplayName;
    }

    public void setShowKeys(boolean showKeys) {
        this.showKeys = showKeys;
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

    public void setDisplayIndex(int displayIndex) {
        this.displayIndex = displayIndex;
    }

    public void setKeyDisplayName(String keyDisplayName) {
        this.keyDisplayName = keyDisplayName;
    }

    public void setKeyFontData(FontData keyFontData) {
        this.keyFontData = keyFontData;
    }

    public void setSeparatorFontData(FontData separatorFontData) {
        this.separatorFontData = separatorFontData;
    }

    public void setValueFontData(FontData valueFontData) {
        this.valueFontData = valueFontData;
    }
}
