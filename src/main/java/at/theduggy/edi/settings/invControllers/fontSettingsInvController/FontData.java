package at.theduggy.edi.settings.invControllers.fontSettingsInvController;

import org.bukkit.ChatColor;

public class FontData {

    private ChatColor color;
    private boolean bold;
    private boolean italic;
    private boolean underlined;

    public FontData(ChatColor color, boolean bold, boolean italic, boolean underlined) {
        this.underlined = underlined;
        this.color = color;
        this.bold = bold;
        this.italic = italic;
    }

    public ChatColor getColor() {
        return color;
    }

    public boolean isBold() {
        return bold;
    }

    public boolean isItalic(){
        return italic;
    }

    public boolean isUnderlined() {
        return underlined;
    }

    public void setItalic(boolean italic) {
        this.italic = italic;
    }

    public void setUnderlined(boolean underlined) {
        this.underlined = underlined;
    }

    public void setColor(ChatColor color) {
        this.color = color;
    }

    public void setBold(boolean bold) {
        this.bold = bold;
    }

    public String format(String s) {
        return color + "" + (bold?ChatColor.BOLD:"") + (italic?ChatColor.ITALIC:"") + (underlined?ChatColor.UNDERLINE:"")  + s;
    }
}
