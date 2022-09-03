package at.theduggy.edi;

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
}
