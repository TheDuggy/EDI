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
import at.theduggy.edi.rendering.renderer.FooterRenderer;
import at.theduggy.edi.rendering.renderer.HeaderRenderer;
import at.theduggy.edi.rendering.renderer.ScoreboardRenderer;

public class RenderManager {

    private final EDIManager ediManager;
    private final ScoreboardRenderer scoreboardRenderer;
    private final FooterRenderer footerRenderer;
    private final HeaderRenderer headerRenderer;


    public RenderManager(EDIManager ediManager){
        this.ediManager = ediManager;
        this.scoreboardRenderer = new ScoreboardRenderer(ediManager);
        this.footerRenderer = new FooterRenderer(ediManager);
        this.headerRenderer = new HeaderRenderer(ediManager);
    }

    public void update(){
        scoreboardRenderer.render();
        footerRenderer.render();
        headerRenderer.render();
    }

    public ScoreboardRenderer getScoreboardRenderer() {
        return scoreboardRenderer;
    }

    public FooterRenderer getFooterRenderer() {
        return footerRenderer;
    }

    public HeaderRenderer getHeaderRenderer() {
        return headerRenderer;
    }
}
