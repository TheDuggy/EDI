package at.theduggy.edi.rendering.renderer;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.Main;
import at.theduggy.edi.rendering.EDIUpdateData;
import at.theduggy.edi.settings.OptionManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;

public class FooterRenderer{
    private EDIManager ediManager;
    private boolean footerRendered = false;
    private final EDIUpdateData ediUpdateData;
    private String oldFooter = null;

    public FooterRenderer(EDIManager ediManager) {
        this.ediManager = ediManager;
        this.ediUpdateData = new EDIUpdateData(ediManager);
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

            if (optionManager.getCords().isFooter()){
                footer.append((optionManager.getCords().isShowKeys()?"\nCords: " : "\n") + ediUpdateData.getCordsValue() + "\n");
            }
            if (optionManager.getBiome().isFooter()){
                footer.append((optionManager.getBiome().isShowKeys()?"\nBiome: " : "\n") + ediUpdateData.getBiomeValue() + "\n");
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
