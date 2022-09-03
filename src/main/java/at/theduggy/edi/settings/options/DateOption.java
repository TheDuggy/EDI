package at.theduggy.edi.settings.options;

import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateOption extends Option{
    public DateOption(String optionDisplayName, String optionInfo, String optionIdentifier) {
        super(optionDisplayName, optionInfo, optionIdentifier);
    }

    @Override
    public String getValue(Player player) {
        LocalDate localDate = LocalDate.now();
        return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(localDate);
    }
}
