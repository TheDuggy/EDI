package at.theduggy.edi.settings.rendering;

import at.theduggy.edi.EDIManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;

public class ObjectiveManager {

    private Objective objective;
    private Scoreboard scoreboard;
    private final ArrayList<OrganisedScore> organisedScores = new ArrayList<>();
    private final ArrayList<OrganisedScore> tempOrganisedScores = new ArrayList<>();
    private EDIManager ediManager;

    public ObjectiveManager(EDIManager ediManager){
        this.scoreboard = ediManager.getPlayer().getScoreboard()!=null?ediManager.getPlayer().getScoreboard() : Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("edi-display", "dummy", ChatColor.GOLD + "EDInfo");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.ediManager = ediManager;
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    }

    public void update(){
        ArrayList<String> objectiveNames = new ArrayList<>();
        scoreboard.getObjectives().forEach(objectiveToGetName -> objectiveNames.add(objectiveToGetName.getName()));
        if (ediManager.getOptionManager().isDisplayEnabled()){
            if (!objectiveNames.contains("edi-display")){
                ediManager.getPlayer().sendMessage("TEST");
                this.objective = scoreboard.registerNewObjective("edi-display", "dummy", ChatColor.GOLD + "EDInfo");
                objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            }
            ediManager.getPlayer().setScoreboard(scoreboard);
            if (organisedScores.size()==0){
                Score separator = objective.getScore(ChatColor.GREEN + "------------------------");
                separator.setScore(2);
                Location loc = ediManager.getPlayer().getLocation();

                OrganisedScore cords = new OrganisedScore(ediManager, "Cords: " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ(), 1);
                organisedScores.add(cords);
                if (ediManager.getOptionManager().isCordsEnabled()){
                    cords.render();
                    tempOrganisedScores.add(cords);
                }

                OrganisedScore biom = new OrganisedScore(ediManager, "Biom: " + ediManager.getPlayer().getWorld().getBiome(loc.getBlockX(), loc.getBlockZ()), 0);
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
                Location loc = ediManager.getPlayer().getLocation();
                if (ediManager.getOptionManager().isCordsEnabled()){
                    organisedScores.get(0).update("Cords: " + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ());
                    tempOrganisedScores.add(organisedScores.get(0));
                }else {
                    if (organisedScores.get(0).isRendered()){
                        organisedScores.get(0).remove();
                    }
                }

                if (ediManager.getOptionManager().isBiomesEnabled()){
                    organisedScores.get(1).update("Biom: " + ediManager.getPlayer().getWorld().getBiome(loc.getBlockX(), loc.getBlockZ()));
                    tempOrganisedScores.add(organisedScores.get(1));
                }else {
                    if (organisedScores.get(1).isRendered()){
                        organisedScores.get(1).remove();
                    }
                }

                for (int i = 0,counter = tempOrganisedScores.size()-1;i<tempOrganisedScores.size();i++,counter--){
                    tempOrganisedScores.get(i).setScore(counter);
                }
                ediManager.getPlayer().setScoreboard(scoreboard);
            }
        }else {
            if (!ediManager.getOptionManager().isDisplayEnabled()){
                if (objectiveNames.contains("edi-display")){
                    unregister();
                }
            }
        }
    }

    public void unregister(){
        objective.unregister();
    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }
}
