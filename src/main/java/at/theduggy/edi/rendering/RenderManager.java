package at.theduggy.edi.rendering;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.rendering.renderer.FooterRenderer;
import at.theduggy.edi.rendering.renderer.ScoreboardRenderer;

public class RenderManager {

    private final EDIManager ediManager;
    private final ScoreboardRenderer scoreboardRenderer;
    private final FooterRenderer footerRenderer;


    public RenderManager(EDIManager ediManager){
        this.ediManager = ediManager;
        this.scoreboardRenderer = new ScoreboardRenderer(ediManager);
        this.footerRenderer = new FooterRenderer(ediManager);
    }

    public void update(){
        scoreboardRenderer.render();
        footerRenderer.render();
    }

    public ScoreboardRenderer getScoreboardRenderer() {
        return scoreboardRenderer;
    }

    public FooterRenderer getFooterRenderer() {
        return footerRenderer;
    }
}
