package at.theduggy.edi.storage;

import at.theduggy.edi.settings.invControllers.fontSettingsInvController.FontData;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.ChatColor;

import java.io.IOException;

public class FontDataTypeAdapter extends TypeAdapter<FontData> {
    @Override
    public void write(JsonWriter out, FontData value) throws IOException {
        out.beginObject();
        out.name("bold").value(value.isBold());
        out.name("italic").value(value.isItalic());
        out.name("underlined").value(value.isUnderlined());
        String colorString = value.getColor().name();
        if (value.getColor() == ChatColor.DARK_PURPLE){
            colorString = "MAGENTA";
        }else if (value.getColor() == ChatColor.LIGHT_PURPLE){
            colorString = "PINK";
        }
        out.name("color").value(colorString);
        out.endObject();
    }

    @Override
    public FontData read(JsonReader in) throws IOException {
        FontData fontData = new FontData(null,false, false, false);
        String fieldName = "";
        in.beginObject();
        while (in.hasNext()){
            if (in.peek()== JsonToken.NAME){
                fieldName=in.nextName();
                switch (fieldName) {
                    case "color":
                        String colorString = in.nextString();
                        if (colorString.equals("PINK")) {
                            colorString = "LIGHT_PURPLE";
                        } else if (colorString.equals("MAGENTA")) {
                            colorString = "DARK_PURPLE";
                        }
                        fontData.setColor(ChatColor.valueOf(colorString));
                        break;
                    case "bold":
                        fontData.setBold(in.nextBoolean());
                        break;
                    case "italic":
                        fontData.setItalic(in.nextBoolean());
                        break;
                    case "underlined":
                        fontData.setUnderlined(in.nextBoolean());
                        break;
                }
            }
        }
        in.endObject();
        return fontData;
    }
}