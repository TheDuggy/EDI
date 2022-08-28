package at.theduggy.edi.rendering;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.rendering.renderer.FooterRenderer;
import at.theduggy.edi.rendering.renderer.ScoreboardRenderer;
import at.theduggy.edi.settings.Option;
import jdk.jshell.spi.SPIResolutionException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.scoreboard.*;
import at.theduggy.edi.GlobalEDIController.OptionBackend;

import java.util.ArrayList;
import java.util.Collections;

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
