/*
 * EDI: Extended Debug Info
 * Copyright (C) 2022  Georg Kollegger(TheDuggy/CoderTheDuggy)
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package at.theduggy.edi.settings.options.server;

import at.theduggy.edi.settings.options.Option;
import org.bukkit.entity.Player;

import java.net.InetAddress;
import java.net.InetSocketAddress;

public class IpAddressOption extends Option {

    public IpAddressOption(String displayName, String optionInfo, String optionIdentifierName) {
        super(displayName, optionInfo, optionIdentifierName, false);
    }

    @Override
    public String getValue(Player player) {
        String value = "---";
        try {
            InetSocketAddress inetAddress = player.getAddress();
            value = inetAddress.getHostName();
        }catch (Exception ignored){}
        return value;
    }
}
