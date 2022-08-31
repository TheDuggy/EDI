package at.theduggy.edi.storage;

import at.theduggy.edi.settings.OptionManager;

import java.util.HashMap;

public class SettingsStorageData {

    public HashMap<String, OptionStorageData> options = new HashMap<>();
    public boolean header;
    public boolean footer;
    public boolean ediDisplay;

    public SettingsStorageData(OptionManager optionManager){
        header = optionManager.isHeaderEnabled();
        footer = optionManager.isFooterEnabled();
        ediDisplay = optionManager.isDisplayEnabled();
        for (String optionIdentifier : optionManager.getRegisteredOptions().keySet()){
            options.put(optionIdentifier, new OptionStorageData(optionManager.getRegisteredOptions().get(optionIdentifier)));
        }
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
}
