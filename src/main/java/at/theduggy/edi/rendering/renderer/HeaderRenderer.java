package at.theduggy.edi.rendering.renderer;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.Main;
import at.theduggy.edi.settings.OptionManager;
import at.theduggy.edi.settings.options.Option;
import org.bukkit.ChatColor;

public class HeaderRenderer{
    private final EDIManager ediManager;
    private boolean headerRendered = false;

    public HeaderRenderer(EDIManager ediManager) {
        this.ediManager = ediManager;
    }

    public void render(){
        OptionManager optionManager = ediManager.getOptionManager();
        if (optionManager.isHeaderEnabled()){
            headerRendered = true;
            StringBuilder header = new StringBuilder();
            header.append(Main.logo + ChatColor.RESET);

            for (int i = ediManager.getOptionManager().getDisplayIndexList().size()-1;i>=0;i--){
                Option o = ediManager.getOptionManager().getDisplayIndexList().get(i);
                if (o.isHeader()){
                    header.append((o.isShowKeys()?"\n" + o.getKeyFontData().format(o.getName()  )+ o.getSeparatorFontData().format(": ") : "\n") + o.getValueFontData().format(o.getValue(ediManager.getPlayer())));
                }
            }
            ediManager.getPlayer().setPlayerListHeader(header.toString());
        }else {
            if (headerRendered){
                headerRendered = false;
                ediManager.getPlayer().setPlayerListHeader(null);
            }
        }
    }

    public void reset(){
        System.out.println("awd");
        ediManager.getPlayer().setPlayerListFooter(null);
    }

}
