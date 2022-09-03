/*
EDI: Extended Debug Info
Copyright (C) 2022  Georg Kollegger(TheDuggy/CoderTheDuggy)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package at.theduggy.edi.rendering.renderer;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.rendering.OrganisedScore;
import at.theduggy.edi.settings.options.Option;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.ArrayList;
import java.util.Collections;

public class ScoreboardRenderer {

    private final EDIManager ediManager;
    private final Objective objective;
    private final Scoreboard scoreboard;

    private boolean hidden;
    private final ArrayList<OrganisedScore> organisedScores = new ArrayList<>();
    private final ArrayList<OrganisedScore> tempOrganisedScores = new ArrayList<>();

    public ScoreboardRenderer(EDIManager ediManager){
        this.scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
        this.objective = scoreboard.registerNewObjective("edi-display", "dummy", ChatColor.DARK_GRAY + "└" + ChatColor.GOLD + "" +ChatColor.BOLD + "EDInfo"+ ChatColor.DARK_GRAY + "┘");
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
        this.ediManager = ediManager;
        this.hidden = ediManager.getOptionManager().isDisplayEnabled();
    }

    public void render(){
        if (ediManager.getOptionManager().isDisplayEnabled()){
            if (hidden){
                show(); //Shows the objective again if it was disabled!
            }
            if (organisedScores.size()==0){
                System.out.println(ediManager.getOptionManager().getRegisteredOptions());
                for (Option o : ediManager.getOptionManager().getDisplayIndexList()){
                    OrganisedScore organisedScore = new OrganisedScore(ediManager, o, (o.isShowKeys()?o.getKeyFontData().format(o.getDisplayName()) + o.getSeparatorFontData().format(":") + " ":"") + o.getValueFontData().format(o.getValue(ediManager.getPlayer())), o.getDisplayIndex());
                    organisedScores.add(organisedScore);
                    if (o.isEdiDisplay()){
                        organisedScore.render();
                        tempOrganisedScores.add(organisedScore);
                    }
                }

                ArrayList<Integer> lengths = new ArrayList<>();
                tempOrganisedScores.forEach(organisedScore -> lengths.add(organisedScore.getLength()));
                double longestMsgLength = Collections.max(lengths);
                for (OrganisedScore organisedScore : tempOrganisedScores){
                    if (!organisedScore.getOption().isShowKeys()){
                        if (organisedScore.getLength()!=longestMsgLength) {
                            int spacesToCenter = (int) (Math.round((longestMsgLength / 2.0) - Math.round(organisedScore.getLength()/2.0))/3.0);
                            organisedScore.update(" ".repeat(spacesToCenter) + organisedScore.getValue());
                        }
                    }
                }

                for (OrganisedScore score : tempOrganisedScores){
                    score.setScore(score.getDisplayIndex());
                }
                ediManager.getPlayer().setScoreboard(scoreboard);
            }else {
                tempOrganisedScores.clear();
                for (OrganisedScore organisedScore : organisedScores){
                    if (organisedScore.getOption().isEdiDisplay()){
                        Option o = organisedScore.getOption();

                        organisedScore.update((o.isShowKeys()?o.getKeyFontData().format(o.getDisplayName()) + o.getSeparatorFontData().format(":") + " ":"") + o.getValueFontData().format(o.getValue(ediManager.getPlayer())));
                        tempOrganisedScores.add(organisedScore);
                    }else {
                        if (organisedScore.isRendered()){
                            organisedScore.remove();
                        }
                    }
                }
                ArrayList<Integer> lengths = new ArrayList<>();
                tempOrganisedScores.forEach(organisedScore -> lengths.add(organisedScore.getLength()));
                double longestMsgLength = Collections.max(lengths);
                for (OrganisedScore organisedScore : tempOrganisedScores){
                    if (!organisedScore.getOption().isShowKeys()){
                        if (organisedScore.getLength()!=longestMsgLength) {
                            int spacesToCenter = (int) (Math.round((longestMsgLength / 2.0) - Math.round(organisedScore.getLength()/2.0))/3.0);
                            organisedScore.update(" ".repeat(spacesToCenter) + organisedScore.getValue());
                        }
                    }
                }
                for (OrganisedScore score : tempOrganisedScores){
                    score.setScore(score.getDisplayIndex());
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

    public Scoreboard getScoreboard() {
        return scoreboard;
    }

}
