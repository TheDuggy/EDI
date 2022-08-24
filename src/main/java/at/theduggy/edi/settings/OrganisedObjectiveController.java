package at.theduggy.edi.settings;

import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;

import java.util.ArrayList;
import java.util.HashMap;

public class OrganisedObjectiveController {

    private Objective objective;
    private ArrayList<Score> scores = new ArrayList<>();

    public OrganisedObjectiveController(Objective objective){
        this.objective = objective;
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

    }

    public void update(){
        if (scores.size()==0){
            Score heading = objective.getScore(ChatColor.GOLD + "EDInfo");
            heading.setScore(0);
            scores.add(heading);
            Score separator = objective.getScore(ChatColor.GREEN + "------------------------");
            separator.setScore(1);
            scores.add(separator);

        }
    }

}
