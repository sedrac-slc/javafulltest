package gest.config;

import java.lang.reflect.Type;
import java.time.LocalDateTime;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import gest.util.ConstantDateFormat;

public class DeserializerLocalDateTime implements JsonDeserializer<LocalDateTime> {

    @Override
    public LocalDateTime deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        String dateString = json.getAsJsonPrimitive().getAsString();
        return LocalDateTime.parse(dateString, ConstantDateFormat.FORMAT_PARSE_OBJECT);
    }

}
