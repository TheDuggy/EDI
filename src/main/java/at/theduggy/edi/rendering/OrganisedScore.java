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

package at.theduggy.edi.rendering;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.settings.OptionManager;
import at.theduggy.edi.settings.options.Option;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

public class OrganisedScore {

    private final Scoreboard scoreboard;
    private String value;
    private boolean rendered;
    private final Objective objective;
    private int msgLength = 0;
    private final OptionManager optionManager;
    private Score tmpScore;
    private final Option option;

    public OrganisedScore(EDIManager ediManager, Option option, String value, int index) {
        optionManager = ediManager.getOptionManager();
        this.option = option;
        this.scoreboard = ediManager.getRenderManager().getScoreboardRenderer().getScoreboard();
        this.value = value;
        char[] chars = value.toCharArray();
        for (char c : chars){
            msgLength +=DefaultFontInfo.getDefaultFontInfo(c).getLength();
        }
        objective = scoreboard.getObjective("edi-display");
    }

    public void render(){
        rendered = true;
        tmpScore = objective.getScore(value);
        tmpScore.setScore(getDisplayIndex());
    }

    public void update(String value){
        rendered = true;
        scoreboard.resetScores(this.value);
        this.value = value;
        char[] chars = value.toCharArray();
        msgLength = 0;
        boolean previousCode = false;
        boolean bold = false;
        for (char aChar : chars) {
            if (aChar != '§') {
                if (previousCode&&aChar == 'l') {
                    bold = true;
                } else {
                    if (bold) {
                        msgLength += DefaultFontInfo.getDefaultFontInfo(aChar).getBoldLength();
                    } else {
                        msgLength += DefaultFontInfo.getDefaultFontInfo(aChar).getLength();
                    }
                }
            } else {
                bold = false;
                previousCode = true;
            }
        }
        tmpScore = objective.getScore(value);
        tmpScore.setScore(getDisplayIndex());

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

    public int getLength() {
        return msgLength;
    }

    public String getValue() {
        return value;
    }

    public Option getOption() {
        return option;
    }

    public int getDisplayIndex(){
        if (option!=null) {
            return this.option.getDisplayIndex();
        }else {
            return optionManager.getDisplayIndexList().size()+1;
        }
    }

}
