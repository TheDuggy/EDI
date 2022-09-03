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
