package at.theduggy.edi.storage;

import at.theduggy.edi.settings.options.Option;

public class OptionStorageData {

    private boolean showKeys;
    private boolean header;
    private boolean footer;
    private boolean ediDisplay;

    private int displayIndex;

    public OptionStorageData(Option option){
        this.showKeys = option.isShowKeys();
        this.header = option.isHeader();
        this.footer = option.isFooter();
        this.ediDisplay = option.isEdiDisplay();
        this.displayIndex = option.getDisplayIndex();
    }

    public void applyDataToStorage(Option option){
        option.setShowKeys(showKeys);
        option.setHeader(header);
        option.setFooter(footer);
        option.setEdiDisplay(ediDisplay);
        option.setDisplayIndex(displayIndex);
    }

}
