package at.theduggy.edi.settings.options;

import org.bukkit.entity.Player;

public class BiomeOption extends Option{

    public BiomeOption(String optionName, String optionInfo, boolean header, boolean footer, boolean edi_display, boolean showKeys) {
        super(optionName, optionInfo, header, footer, edi_display, showKeys);
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
