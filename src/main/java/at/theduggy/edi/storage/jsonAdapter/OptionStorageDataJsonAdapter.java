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

import at.theduggy.edi.settings.invControllers.fontSettingsInvController.FontData;
import at.theduggy.edi.storage.dataObj.OptionStorageData;
import com.google.gson.*;

import java.lang.reflect.Type;

public class OptionStorageDataJsonAdapter implements JsonSerializer<OptionStorageData>, JsonDeserializer<OptionStorageData> {

    @Override
    public OptionStorageData deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        OptionStorageData optionStorageData = new OptionStorageData(false, false, false, false);
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.keySet().contains("showKeys")){
            optionStorageData.setShowKeys(jsonObject.get("showKeys").getAsBoolean());
        }

        if (jsonObject.keySet().contains("header")){
            optionStorageData.setHeader(jsonObject.get("header").getAsBoolean());
        }

        if (jsonObject.keySet().contains("footer")){
            optionStorageData.setFooter(jsonObject.get("footer").getAsBoolean());
        }

        if (jsonObject.keySet().contains("ediDisplay")){
            optionStorageData.setEdiDisplay(jsonObject.get("ediDisplay").getAsBoolean());
        }
        optionStorageData.setKeyFontData(context.deserialize(jsonObject.get("keyFontData"), FontData.class));
        optionStorageData.setSeparatorFontData(context.deserialize(jsonObject.get("separatorFontData"), FontData.class));
        optionStorageData.setValueFontData(context.deserialize(jsonObject.get("valueFontData"), FontData.class));
        optionStorageData.setKeyDisplayName(jsonObject.get("keyDisplayName").getAsString());
        optionStorageData.setDisplayIndex(jsonObject.get("displayIndex").getAsInt());
        return optionStorageData;
    }

    @Override
    public JsonElement serialize(OptionStorageData optionStorageData, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        if (optionStorageData.isShowKeys()){
            jsonObject.addProperty("showKeys", true);
        }

        if (optionStorageData.isHeader()){
            jsonObject.addProperty("header", true);
        }

        if (optionStorageData.isFooter()){
            jsonObject.addProperty("footer", true);
        }

        if (optionStorageData.isEdiDisplay()){
            jsonObject.addProperty("ediDisplay", true);
        }

        jsonObject.addProperty("keyDisplayName", optionStorageData.getKeyDisplayName());
        jsonObject.add("keyFontData", context.serialize(optionStorageData.getKeyFontData()));
        jsonObject.add("separatorFontData", context.serialize(optionStorageData.getSeparatorFontData()));
        jsonObject.add("valueFontData", context.serialize(optionStorageData.getValueFontData()));
        jsonObject.addProperty("displayIndex", optionStorageData.getDisplayIndex());
        return jsonObject;
    }
}
