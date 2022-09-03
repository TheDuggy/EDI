package at.theduggy.edi.rendering.renderer;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.Main;
import at.theduggy.edi.settings.OptionManager;
import at.theduggy.edi.settings.options.Option;
import org.bukkit.ChatColor;

public class FooterRenderer{
    private final EDIManager ediManager;
    private boolean footerRendered = false;

    public FooterRenderer(EDIManager ediManager) {
        this.ediManager = ediManager;
    }

    public void render(){
        OptionManager optionManager = ediManager.getOptionManager();
        if (optionManager.isFooterEnabled()){
            footerRendered = true;
            StringBuilder footer = new StringBuilder();

            boolean logoAppended = false;
            if (!ediManager.getOptionManager().isHeaderEnabled()){
                footer.append(Main.logo + ChatColor.RESET);
                logoAppended = true;
            }
            for (int i = ediManager.getOptionManager().getDisplayIndexList().size()-1;i>=0;i--){
                Option o = ediManager.getOptionManager().getDisplayIndexList().get(i);
                if (o.isFooter()){
                    footer.append((o.isShowKeys()?"\n" + o.getKeyFontData().format(o.getDisplayName()) + o.getKeyFontData().format(": ") : (logoAppended?"\n":"")) + o.getValueFontData().format(o.getValue(ediManager.getPlayer())));
                }
            }
            ediManager.getPlayer().setPlayerListFooter(footer.toString());
        }else {
            if (footerRendered){
                footerRendered = false;
                ediManager.getPlayer().setPlayerListFooter(null);
            }
        }
    }

    public void reset(){
        ediManager.getPlayer().setPlayerListFooter(null);
    }

}
