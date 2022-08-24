package at.theduggy.edi;

import at.theduggy.edi.settings.GlobalOptionController;
import at.theduggy.edi.settings.OptionManager;

import java.util.UUID;

public class EDIController {

    public OptionManager optionManager;

    public EDIController(UUID player){
        this.optionManager = new OptionManager(player);
    }

}
