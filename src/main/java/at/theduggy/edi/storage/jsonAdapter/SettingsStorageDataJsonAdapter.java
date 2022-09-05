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

package at.theduggy.edi.storage.jsonAdapter;

import at.theduggy.edi.storage.dataObj.OptionStorageData;
import at.theduggy.edi.storage.dataObj.SettingsStorageData;
import com.google.common.reflect.TypeToken;
import com.google.gson.*;

import java.lang.reflect.Type;
import java.util.HashMap;

public class SettingsStorageDataJsonAdapter implements JsonSerializer<SettingsStorageData>, JsonDeserializer<SettingsStorageData> {
    @Override
    public SettingsStorageData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        SettingsStorageData settingsStorageData = new SettingsStorageData(false, false, false);
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.keySet().contains("ediDisplay")){
            settingsStorageData.setEdiDisplay(jsonObject.get("ediDisplay").getAsBoolean());
        }

        if (jsonObject.keySet().contains("header")){
            settingsStorageData.setHeader(jsonObject.get("header").getAsBoolean());
        }

        if (jsonObject.keySet().contains("footer")){
            settingsStorageData.setHeader(jsonObject.get("footer").getAsBoolean());
        }
        return settingsStorageData;
    }

    @Override
    public JsonElement serialize(SettingsStorageData settingsStorageData, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        if (settingsStorageData.isEdiDisplay()){
            jsonObject.addProperty("ediDisplay", true);
        }

        if (settingsStorageData.isHeader()){
            jsonObject.addProperty("header", true);
        }

        if (settingsStorageData.isFooter()){
            jsonObject.addProperty("footer", true);
        }

        jsonObject.add("registeredOptions", context.serialize(settingsStorageData.getOptionsData(), new TypeToken<HashMap<String, OptionStorageData>>(){}.getType()));
        return jsonObject;
    }
}
