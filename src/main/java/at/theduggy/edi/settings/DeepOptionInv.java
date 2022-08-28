package at.theduggy.edi.settings;

import at.theduggy.edi.settings.options.Option;
import org.bukkit.inventory.Inventory;

public class DeepOptionInv {

    private Inventory deepOptionInv;
    private Option option;

    public DeepOptionInv(Inventory deepOptionInv, Option option) {
        this.deepOptionInv = deepOptionInv;
        this.option = option;
    }

    public Inventory getDeepOptionInv() {
        return deepOptionInv;
    }

    public Option getOption() {
        return option;
    }


}
