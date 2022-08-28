package at.theduggy.edi.rendering.renderer;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.GlobalEDIController;
import at.theduggy.edi.rendering.DefaultFontInfo;
import at.theduggy.edi.rendering.EDIUpdateData;
import at.theduggy.edi.rendering.OrganisedScore;
import at.theduggy.edi.settings.OptionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardRenderer {

    private final EDIManager ediManager;
    private final EDIUpdateData ediUpdateData;
    private final Objective objective;
    private final Scoreboard scoreboard;

    private boolean hidden;
    private final ArrayList<OrganisedScore> organisedScores = new ArrayList<>();
    private final ArrayList<OrganisedScore> tempOrganisedScores = new ArrayList<>();

    public ScoreboardRenderer(EDIManager ediManager){
        this.ediUpdateData = new EDIUpdateData(ediManager);
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("edi-display", "dummy", ChatColor.DARK_GRAY + "└" + ChatColor.GOLD + "" +ChatColor.BOLD + "EDInfo"+ ChatColor.DARK_GRAY + "┘");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.ediManager = ediManager;
        this.hidden = ediManager.getOptionManager().isDisplayEnabled();
    }

    public void render(){
        OptionManager optionManager = ediManager.getOptionManager();
        if (ediManager.getOptionManager().isDisplayEnabled()){
            if (hidden){
                System.out.println("TEST");
                show(); //Shows the objective again if it was disabled!
            }
            if (organisedScores.size()==0){
                OrganisedScore cords = new OrganisedScore(ediManager, (optionManager.getCords().isShowKeys()?"Cords: " : "") + ediUpdateData.getCordsValue(), GlobalEDIController.OptionBackend.COORDINATES.getDisplayIndex());
                organisedScores.add(cords);
                if (ediManager.getOptionManager().getCords().isEdiDisplay()){
                    cords.render();
                    tempOrganisedScores.add(cords);
                }

                OrganisedScore biom = new OrganisedScore(ediManager, (optionManager.getBiome().isShowKeys()?"Biome: " : "") + ediUpdateData.getBiomeValue(), GlobalEDIController.OptionBackend.BIOMES.getDisplayIndex());
                organisedScores.add(biom);
                if (ediManager.getOptionManager().getBiome().isEdiDisplay()){
                    biom.render();
                    tempOrganisedScores.add(biom);
                }

                OrganisedScore separator = new OrganisedScore(ediManager, calculateSeparator(),0);
                separator.render();
                organisedScores.add(separator);
                separator.setScore(tempOrganisedScores.size());

                for (int i = 0,counter = tempOrganisedScores.size()-1;i<tempOrganisedScores.size();i++,counter--){
                    tempOrganisedScores.get(i).setScore(counter);
                }
                ediManager.getPlayer().setScoreboard(scoreboard);
            }else {
                tempOrganisedScores.clear();
                if (ediManager.getOptionManager().getCords().isEdiDisplay()){
                    organisedScores.get(GlobalEDIController.OptionBackend.COORDINATES.getDisplayIndex()-1).update((optionManager.getCords().isShowKeys()?"Cords: " : "") + ediUpdateData.getCordsValue());
                    tempOrganisedScores.add(organisedScores.get(0));
                }else {
                    if (organisedScores.get(GlobalEDIController.OptionBackend.COORDINATES.getDisplayIndex()-1).isRendered()){
                        organisedScores.get(GlobalEDIController.OptionBackend.COORDINATES.getDisplayIndex()-1).remove();
                    }
                }

                if (ediManager.getOptionManager().getBiome().isEdiDisplay()){
                    organisedScores.get(GlobalEDIController.OptionBackend.BIOMES.getDisplayIndex()-1).update((optionManager.getBiome().isShowKeys()?"Biome: " : "") + ediUpdateData.getBiomeValue());
                    tempOrganisedScores.add(organisedScores.get(1));
                }else {
                    if (organisedScores.get(GlobalEDIController.OptionBackend.BIOMES.getDisplayIndex()-1).isRendered()){
                        organisedScores.get(GlobalEDIController.OptionBackend.BIOMES.getDisplayIndex()-1).remove();
                    }
                }

                organisedScores.get(organisedScores.size()-1).update(calculateSeparator());
                organisedScores.get(organisedScores.size()-1).setScore(tempOrganisedScores.size());
                for (int i = 0,counter = tempOrganisedScores.size()-1;i<tempOrganisedScores.size();i++,counter--){
                    tempOrganisedScores.get(i).setScore(counter);
                }
                ediManager.getPlayer().setScoreboard(scoreboard);
            }
        }else {
            if (!hidden){
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

    private String calculateSeparator(){
        ArrayList<Integer> lengths = new ArrayList<>();
        tempOrganisedScores.forEach(organisedScore ->lengths.add(organisedScore.getLength()));
        String separatorString = "=".repeat((int) Math.ceil((Collections.max(lengths)/ (double) DefaultFontInfo.EQUALS_SIGN.getLength())));
        char[] separatorChars = separatorString.toCharArray();
        for (int i = 1; i<separatorChars.length;i+=2){
            separatorChars[i] = '-';
        }
        if (separatorChars[separatorChars.length-1] =='-'){
            return ChatColor.GREEN + new String(separatorChars) + "=";
        }else {
            return ChatColor.GREEN + new String(separatorChars);
        }

    }

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

}
