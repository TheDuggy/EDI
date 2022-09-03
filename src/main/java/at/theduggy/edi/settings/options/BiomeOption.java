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
package at.theduggy.edi.settings.options;

import org.bukkit.entity.Player;

public class BiomeOption extends Option{

    public BiomeOption(String optionDisplayName, String optionInfo, String optionIdentifier) {
        super(optionDisplayName, optionInfo, optionIdentifier);
    }

    @Override
    public String getValue(Player player) {
        String playerBiomRaw = String.valueOf(player.getWorld().getBiome(player.getLocation().getBlockX(), player.getLocation().getBlockZ()));
        String[] playerBiomSplit = playerBiomRaw.split("_");
        StringBuilder playerBiom = new StringBuilder();
        for (int i = 0;i<playerBiomSplit.length;i++){
            playerBiomSplit[i] = playerBiomSplit[i].toLowerCase();
            char[] chars = playerBiomSplit[i].toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            playerBiom.append(i!=playerBiomSplit.length-1? new String(chars) + " ": new String(chars));
        }
        return playerBiom.toString();
    }
}
