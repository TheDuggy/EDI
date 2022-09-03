/*
EDI: Extended Debug Info
Copyright (C) 2022  Georg Kollegger(TheDuggy/CoderTheDuggy)

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package at.theduggy.edi;

import at.theduggy.edi.settings.OptionManager;
import at.theduggy.edi.rendering.RenderManager;
import org.bukkit.entity.Player;

public class EDIManager {

    private final OptionManager optionManager;
    private Player player;
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

    public void setPlayer(Player player) {
        this.player = player;
    }
}
