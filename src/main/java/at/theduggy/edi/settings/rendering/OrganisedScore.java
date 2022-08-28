package at.theduggy.edi.settings.rendering;

import at.theduggy.edi.EDIManager;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;

public class OrganisedScore {

    private Scoreboard scoreboard;
    private EDIManager ediManager;
    private String value;
    private final int index;
    private boolean rendered;
    private final Objective objective;
    private Score tmpScore;

    public OrganisedScore(EDIManager ediManager, String value, int index) {
        this.scoreboard = ediManager.getOrganisedObjectiveManager().getScoreboard();
        this.ediManager = ediManager;
        this.value = value;
        this.index = index;
        objective = scoreboard.getObjective("edi-display");
    }

    public void render(){
        rendered = true;
        tmpScore = objective.getScore(value);
        tmpScore.setScore(index);
    }

    public void update(String value){
        rendered = true;
        scoreboard.resetScores(this.value);
        this.value = value;
        tmpScore = objective.getScore(value);
        tmpScore.setScore(index);

    }

    public void setScore(int score){
        tmpScore.setScore(score);
    }

    public boolean isRendered(){
        return rendered;
    }

    public void remove(){
        scoreboard.resetScores(value);
    }

}
