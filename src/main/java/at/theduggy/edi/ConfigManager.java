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
package at.theduggy.edi;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;

public class ConfigManager {

    private final FileConfiguration config;

    public ConfigManager(FileConfiguration fileConfiguration){
        this.config = fileConfiguration;
    }

    public Long getUpdateCycleCount(){
        return config.getLong("update_cycle_count");
    }

    public ArrayList<String> getBlackListedOptions(){
        return new ArrayList<>(config.getStringList("blacklisted_options"));
    }

    public Long getSaveCycleCount(){
        return config.getLong("storage_save_cycle");
    }

    public boolean ediDisplayEnabled(){
        return config.getBoolean("ediDisplayEnabled");
    }

    public boolean infoHeaderEnabled(){
        return config.getBoolean("infoHeaderEnabled");
    }

    public boolean infoFooterEnabled(){
        return config.getBoolean("infoFooterEnabled");
    }

    public boolean displayConfigOnStartup(){
        return config.getBoolean("show_config_values_on_startup");
    }

    public void sendConfigValues(){
        Bukkit.getLogger().warning("------------------EDI------------------");
        Bukkit.getLogger().warning("update_cycle_count : " + getUpdateCycleCount());
        Bukkit.getLogger().warning("storage_save_cycle : " + getSaveCycleCount());
        Bukkit.getLogger().warning("ediDisplayEnabled  : " + ediDisplayEnabled());
        Bukkit.getLogger().warning("infoHeaderEnabled  : " + infoHeaderEnabled());
        Bukkit.getLogger().warning("infoFooterEnabled  : " + infoFooterEnabled());
        ArrayList<String> blacklistedOptions = getBlackListedOptions();
        Bukkit.getLogger().warning("blacklistedOptions : " + (blacklistedOptions.size()>0?blacklistedOptions.get(0):""));
        for (int i = 1;i<blacklistedOptions.size();i++){
            Bukkit.getLogger().warning(" ".repeat(21) + blacklistedOptions.get(i));
        }
    }
}
