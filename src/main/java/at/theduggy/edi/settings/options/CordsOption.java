package at.theduggy.edi.settings.options;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CordsOption extends Option{

    public CordsOption(String optionName, String optionInfo, boolean header, boolean footer, boolean edi_display, boolean showKeys) {
        super(optionName, optionInfo, header, footer, edi_display, showKeys);
    }

    @Override
    public String getValue(Player player) {
        Location loc = player.getLocation();
        return loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();
    }
}
