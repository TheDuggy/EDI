package at.theduggy.edi.settings;

public class Option {

    private boolean showKeys;
    private boolean header;
    private boolean footer;
    private boolean ediDisplay;


    public Option(boolean header, boolean footer, boolean edi_display, boolean showKeys) {
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



}
