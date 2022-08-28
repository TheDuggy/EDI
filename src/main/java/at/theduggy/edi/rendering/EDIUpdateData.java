package at.theduggy.edi.rendering;

import at.theduggy.edi.EDIManager;
import org.bukkit.Location;

public class EDIUpdateData {

    private final EDIManager ediManager;

    public EDIUpdateData(EDIManager ediManager){
        this.ediManager = ediManager;
    }

    public String getBiomeValue(){
        String playerBiomRaw = String.valueOf(ediManager.getPlayer().getWorld().getBiome(ediManager.getPlayer().getLocation().getBlockX(), ediManager.getPlayer().getLocation().getBlockZ()));
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

    public String getCordsValue(){
        Location loc = ediManager.getPlayer().getLocation();
        return  loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ();
    }

}
