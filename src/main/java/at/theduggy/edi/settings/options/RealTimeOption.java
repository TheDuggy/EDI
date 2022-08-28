package at.theduggy.edi.settings.options;

import org.bukkit.entity.Player;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class RealTimeOption extends Option{
    public RealTimeOption(String optionName, String optionInfo, boolean header, boolean footer, boolean edi_display, boolean showKeys) {
        super(optionName, optionInfo, header, footer, edi_display, showKeys);
    }

    @Override
    public String getValue(Player player) {
        return DateTimeFormatter.ofPattern("HH:mm").format(LocalDate.now(ZoneId.systemDefault()));
    }
}
