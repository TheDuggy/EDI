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

import at.theduggy.edi.settings.OptionManager;

import java.util.HashMap;

public class SettingsStorageData {

    public HashMap<String, OptionStorageData> options = new HashMap<>();
    private boolean header;
    private boolean footer;
    private boolean ediDisplay;

    public SettingsStorageData(OptionManager optionManager){
        header = optionManager.isHeaderEnabled();
        footer = optionManager.isFooterEnabled();
        ediDisplay = optionManager.isDisplayEnabled();
        for (String optionIdentifier : optionManager.getRegisteredOptions().keySet()){
            options.put(optionIdentifier, new OptionStorageData(optionManager.getRegisteredOptions().get(optionIdentifier)));
        }
    }

    public SettingsStorageData(boolean header, boolean footer, boolean ediDisplay) {
        this.header = header;
        this.footer = footer;
        this.ediDisplay = ediDisplay;
    }

    public HashMap<String, OptionStorageData> getOptionsData() {
        return options;
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

    public void setOptions(HashMap<String, OptionStorageData> options) {
        this.options = options;
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
}
