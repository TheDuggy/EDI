package at.theduggy.edi.settings.rendering;

import at.theduggy.edi.EDIManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scoreboard.*;
import at.theduggy.edi.settings.GlobalEDIController.Option;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ObjectiveManager {

    private Objective objective;
    private Scoreboard scoreboard;
    private boolean hidden;
    private final ArrayList<OrganisedScore> organisedScores = new ArrayList<>();
    private final ArrayList<OrganisedScore> tempOrganisedScores = new ArrayList<>();
    private final EDIManager ediManager;

    public ObjectiveManager(EDIManager ediManager){
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("edi-display", "dummy", ChatColor.DARK_GRAY + "└" + ChatColor.BOLD + "" +ChatColor.GOLD + "EDInfo"+ ChatColor.DARK_GRAY + "┘");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.ediManager = ediManager;
        System.out.println(ediManager.getOptionManager()!=null);
        this.hidden = false;

    }

    public void update(){
        Location loc = ediManager.getPlayer().getLocation();

        String playerBiomRaw = String.valueOf(ediManager.getPlayer().getWorld().getBiome(loc.getBlockX(), loc.getBlockZ()));
        String[] playerBiomSplit = playerBiomRaw.split("_");
        StringBuilder playerBiom = new StringBuilder();
        for (int i = 0;i<playerBiomSplit.length;i++){
            playerBiomSplit[i] = playerBiomSplit[i].toLowerCase();
            char[] chars = playerBiomSplit[i].toCharArray();
            chars[0] = Character.toUpperCase(chars[0]);
            playerBiom.append(i!=playerBiomSplit.length-1? new String(chars) + " ": new String(chars));
        }
        
        if (ediManager.getOptionManager().isDisplayEnabled()){
            if (hidden){
                System.out.println("TEST");
                show(); //Shows the objective again if it was disabled!
            }
            ediManager.getPlayer().setScoreboard(scoreboard);
            if (organisedScores.size()==0){
                Score separator = objective.getScore(ChatColor.GREEN + "=-=-=-=-=-=-=-=");
                separator.setScore(2);

                OrganisedScore cords = new OrganisedScore(ediManager, "Cords: " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ(), Option.COORDINATES.getDisplayIndex());
                organisedScores.add(cords);
                if (ediManager.getOptionManager().isCordsEnabled()){
                    cords.render();
                    tempOrganisedScores.add(cords);
                }


                OrganisedScore biom = new OrganisedScore(ediManager, "Biom: " + playerBiom, Option.BIOMES.getDisplayIndex());
                organisedScores.add(biom);
                if (ediManager.getOptionManager().isBiomesEnabled()){
                    biom.render();
                    tempOrganisedScores.add(biom);
                }

                for (int i = 0,counter = tempOrganisedScores.size()-1;i<tempOrganisedScores.size();i++,counter--){
                    tempOrganisedScores.get(i).setScore(counter);
                }
                ediManager.getPlayer().setScoreboard(scoreboard);
            }else {
                tempOrganisedScores.clear();
                if (ediManager.getOptionManager().isCordsEnabled()){
                    organisedScores.get(Option.COORDINATES.getDisplayIndex()-1).update("Cords: " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
                    tempOrganisedScores.add(organisedScores.get(0));
                }else {
                    if (organisedScores.get(Option.COORDINATES.getDisplayIndex()-1).isRendered()){
                        organisedScores.get(Option.COORDINATES.getDisplayIndex()-1).remove();
                    }
                }

                if (ediManager.getOptionManager().isBiomesEnabled()){
                    organisedScores.get(Option.BIOMES.getDisplayIndex()-1).update("Biom: " + playerBiom);
                    tempOrganisedScores.add(organisedScores.get(1));
                }else {
                    if (organisedScores.get(Option.BIOMES.getDisplayIndex()-1).isRendered()){
                        organisedScores.get(Option.BIOMES.getDisplayIndex()-1).remove();
                    }
                }

                for (int i = 0,counter = tempOrganisedScores.size()-1;i<tempOrganisedScores.size();i++,counter--){
                    tempOrganisedScores.get(i).setScore(counter);
                }
                ediManager.getPlayer().setScoreboard(scoreboard);
            }
        }else {
            if (!ediManager.getOptionManager().isDisplayEnabled()){
                hide();
            }
        }
    }

    public void unregister(){
        objective.unregister();
    }

    private void hide(){
        hidden = true;
        this.objective.setDisplaySlot(null);
    }

    private void show(){
        hidden = false;
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
