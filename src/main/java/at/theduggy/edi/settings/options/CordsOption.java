package at.theduggy.edi.settings.options;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CordsOption extends Option{

    public CordsOption(String optionName, String optionInfo) {
        super(optionName, optionInfo);
    }

    @Override
    public String getValue(Player player) {
        Location loc = player.getLocation();
        return loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();
    }
}
