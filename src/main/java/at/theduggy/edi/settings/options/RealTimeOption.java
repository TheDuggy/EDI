package at.theduggy.edi.settings.options;

import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class RealTimeOption extends Option{
    public RealTimeOption(String optionDisplayName, String optionInfo, String optionIdentifier) {
        super(optionDisplayName, optionInfo, optionIdentifier);
    }

    @Override
    public String getValue(Player player) {
        return LocalTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("HH:mm"));
    }
}
