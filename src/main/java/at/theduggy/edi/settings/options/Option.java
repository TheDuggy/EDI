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
    private final String optionIdentifier;
    private final String optionInfo;
    private final String optionDisplayName;

    private int displayIndex;

    private int invSlot;


    public Option(String optionDisplayName , String optionInfo ,String optionIdentifier) {
        this.keyFontData = new FontData(ChatColor.GRAY, false, false, false);
        this.separatorFontData = new FontData(ChatColor.GRAY, false, false, false);
        this.valueFontData = new FontData(ChatColor.GOLD, false, false, false);
        this.optionInfo = optionInfo;
        this.optionIdentifier = optionIdentifier;
        this.optionDisplayName = optionDisplayName;
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
        return optionIdentifier;
    }

    public String getDisplayName() {
        return optionDisplayName;
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

}
