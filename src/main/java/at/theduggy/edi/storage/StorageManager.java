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
package at.theduggy.edi.storage;

import at.theduggy.edi.EDIManager;
import at.theduggy.edi.Main;
import at.theduggy.edi.settings.invControllers.fontSettingsInvController.FontData;
import at.theduggy.edi.settings.options.Option;
import at.theduggy.edi.storage.dataObj.OptionStorageData;
import at.theduggy.edi.storage.dataObj.SettingsStorageData;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class StorageManager implements Listener {

    public File DATA_FILE = new File(Main.getPlugin(Main.class).getDataFolder() + "/settings.data");

    private Gson gson;
    private Gson getGson(){
        if (gson == null){
            GsonBuilder builder = new  GsonBuilder().setPrettyPrinting();
            builder.registerTypeAdapter(FontData.class, new FontDataTypeAdapter());
            gson = builder.create();
            return gson;
        }else {
            return gson;
        }
    }

    public  StorageManager() throws IOException {
        if (Files.exists(DATA_FILE.toPath())){
            BufferedReader bufferedReader = new BufferedReader(new FileReader(DATA_FILE, StandardCharsets.UTF_8));
            HashMap<String, SettingsStorageData> dataOfPlayers = getGson().fromJson(bufferedReader,  new TypeToken<HashMap<String, SettingsStorageData>>(){}.getType());
            for (String player : dataOfPlayers.keySet()){
                EDIManager ediManager = new EDIManager(null);
                SettingsStorageData storageData = dataOfPlayers.get(player);
                ediManager.getOptionManager().setDisplayEnabled(storageData.isEdiDisplay());
                ediManager.getOptionManager().setFooterEnabled(storageData.isFooter());
                ediManager.getOptionManager().setHeaderEnabled(storageData.isHeader());
                for (String option_identifier : storageData.getOptionsData().keySet()){
                    Option option = ediManager.getOptionManager().getRegisteredOptions().get(option_identifier);
                    OptionStorageData optionStorageData = dataOfPlayers.get(player).getOptionsData().get(option_identifier);
                    optionStorageData.applyDataToStorage(option);
                }
                Collections.sort(ediManager.getOptionManager().getDisplayIndexList(), Comparator.comparing(Option::getDisplayIndex));
                Main.getEdiPlayerData().put(UUID.fromString(player), ediManager);
            }
            bufferedReader.close();
        }else {
            Files.createFile(DATA_FILE.toPath());
        }
        new BukkitRunnable(){
            @Override
            public void run() {
                try {
                    save();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }.runTaskTimer(Main.getPlugin(Main.class),0, 60);
    }

    public void registerUser(Player player){
        Main.getEdiPlayerData().put(player.getUniqueId(), new EDIManager(player));
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player player = e.getPlayer();
        if (!Main.getEdiPlayerData().containsKey(player.getUniqueId())){
            registerUser(player);
        }else {
            Main.getEdiPlayerData().get(player.getUniqueId()).setPlayer(player);
        }
    }

    public void save() throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(DATA_FILE, StandardCharsets.UTF_8));
        HashMap<String, SettingsStorageData> storageData = new HashMap<>();
        for (UUID k : Main.getEdiPlayerData().keySet()){
            SettingsStorageData settingsStorageData = new SettingsStorageData(Main.getEdiPlayerData().get(k).getOptionManager());
            storageData.put(k.toString(), settingsStorageData);
        }
        bw.write(getGson().toJson(storageData));
        bw.close();
    }
}
