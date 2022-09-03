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
