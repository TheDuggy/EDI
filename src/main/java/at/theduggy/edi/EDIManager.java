package at.theduggy.edi;

import at.theduggy.edi.settings.OptionManager;
import at.theduggy.edi.settings.rendering.ObjectiveManager;
import org.bukkit.entity.Player;

public class EDIManager {

    private final OptionManager optionManager;
    private final Player player;
    private final ObjectiveManager organisedObjectiveManager;


    public EDIManager(Player player){
        this.player = player;
        this.organisedObjectiveManager = new ObjectiveManager(this);
        this.optionManager = new OptionManager(this);
    }

    public Player getPlayer() {
        return player;
    }

    public OptionManager getOptionManager() {
        return optionManager;
    }

    public ObjectiveManager getOrganisedObjectiveManager() {
        return organisedObjectiveManager;
    }
}
