package at.theduggy.edi;

import at.theduggy.edi.settings.OptionManager;
import at.theduggy.edi.rendering.RenderManager;
import org.bukkit.entity.Player;

public class EDIManager {

    private final OptionManager optionManager;
    private final Player player;
    private final RenderManager renderManager;


    public EDIManager(Player player){
        this.player = player;
        this.optionManager = new OptionManager(this);
        this.renderManager = new RenderManager(this);
    }

    public Player getPlayer() {
        return player;
    }

    public OptionManager getOptionManager() {
        return optionManager;
    }

    public RenderManager getRenderManager() {
        return renderManager;
    }
}
