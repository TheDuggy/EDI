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

import org.bukkit.World;
import org.bukkit.entity.Player;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class IngameTimeOption extends Option{
    public IngameTimeOption(String optionDisplayName, String optionInfo, String optionIdentifier) {
        super(optionDisplayName, optionInfo, optionIdentifier);
    }

    @Override
    public String getValue(Player player) {
        long seconds_realtime;
        if (player.getWorld().getTime()<18000){
            seconds_realtime = Math.round(((player.getWorld().getTime()/1000.0)+6.0)*3600.0);
        }else {
            seconds_realtime = Math.round(((player.getWorld().getTime()-18000.0)/1000.0)*3600.0);
        }
        String time_symbol = "";
        if (seconds_realtime>25200&&seconds_realtime<61200){
            time_symbol = "☀";
        }else {
            time_symbol = "☽";
        }
        LocalTime ingameTime = LocalTime.ofSecondOfDay(seconds_realtime);
        return DateTimeFormatter.ofPattern("HH:mm").format(ingameTime) + time_symbol;
    }
}
