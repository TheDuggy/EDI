/*
EDI: Extended Debug Info
Copyright (C) 2022  Georg Kollegger(TheDuggy/CoderTheDuggy)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
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
