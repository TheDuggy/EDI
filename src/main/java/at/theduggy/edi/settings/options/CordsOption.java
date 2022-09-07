/*
 * EDI: Extended Debug Info
 * Copyright (C) 2022  Georg Kollegger(TheDuggy/CoderTheDuggy)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package at.theduggy.edi.settings.options;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class CordsOption extends Option{

    public CordsOption(String optionDisplayName, String optionInfo, String optionIdentifier) {
        super(optionDisplayName, optionInfo, optionIdentifier, true);
    }

    @Override
    public String getValue(Player player) {
        Location loc = player.getLocation();
        return loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();
    }
}
