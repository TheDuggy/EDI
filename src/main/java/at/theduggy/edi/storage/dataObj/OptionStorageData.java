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
import com.google.gson.annotations.Expose;

import java.util.ArrayList;

public class OptionStorageData {

    private boolean showKeys;
    private boolean header;
    private boolean footer;
    private boolean ediDisplay;

    private int displayIndex;


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
    }

    public void applyDataToStorage(Option option){
        option.setShowKeys(showKeys);
        option.setHeader(header);
        option.setFooter(footer);
        option.setEdiDisplay(ediDisplay);
        option.setDisplayIndex(displayIndex);
        option.setKeyFontData(keyFontData);
        option.setSeparatorFontData(separatorFontData);
        option.setValueFontData(valueFontData);
    }

    //TODO Remove!!!!!!

    public FontData getKeyFontData() {
        return keyFontData;
    }

    public FontData getSeparatorFontData() {
        return separatorFontData;
    }

    public FontData getValueFontData() {
        return valueFontData;
    }
}
