package at.theduggy.edi.rendering.renderer;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.Main;
import at.theduggy.edi.settings.OptionManager;
import at.theduggy.edi.settings.options.Option;
import org.bukkit.ChatColor;

public class FooterRenderer{
    private final EDIManager ediManager;
    private boolean footerRendered = false;
    private String oldFooter = null;

    public FooterRenderer(EDIManager ediManager) {
        this.ediManager = ediManager;
    }

    public void render(){
        OptionManager optionManager = ediManager.getOptionManager();
        if (optionManager.isFooterEnabled()){
            if (!footerRendered){
                oldFooter = ediManager.getPlayer().getPlayerListFooter();
            }
            footerRendered = true;
            StringBuilder footer = new StringBuilder();

            if (!ediManager.getOptionManager().isHeaderEnabled()){
                footer.append(Main.logo + ChatColor.RESET);
            }
            for (Option o : optionManager.getRegisteredOptions().values()){
                if (o.isFooter()){
                    footer.append((o.isShowKeys()?"\n" + o.getName() + ": " : "\n") + o.getValue(ediManager.getPlayer()) + "\n");
                }
            }
            ediManager.getPlayer().setPlayerListFooter(footer.toString());
        }else {
            if (footerRendered){
                footerRendered = false;
                ediManager.getPlayer().setPlayerListFooter(oldFooter);
            }
        }
    }

    public void reset(){
        ediManager.getPlayer().setPlayerListFooter(oldFooter);
    }

}
