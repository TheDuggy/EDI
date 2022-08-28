package at.theduggy.edi.settings.options;

import org.bukkit.entity.Player;

public abstract class Option {

    private boolean showKeys;
    private boolean header;
    private boolean footer;
    private boolean ediDisplay;
    private final String optionName;
    private final String optionInfo;
    private int invSlot;


    public Option(String optionName, String optionInfo, boolean header, boolean footer, boolean edi_display, boolean showKeys) {
        this.optionInfo = optionInfo;
        this.optionName = optionName;
        this.header = header;
        this.footer = footer;
        this.ediDisplay = edi_display;
        this.showKeys = showKeys;
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

    public String getName() {
        return optionName;
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



}
