package ru.mail.im.botapi.json;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import ru.mail.im.botapi.entity.ChatType;

import java.lang.reflect.Type;

public class ChatTypeGsonDeserializer implements JsonDeserializer<ChatType> {

    @Override
    public ChatType deserialize(final JsonElement json,
                                final Type typeOfT,
                                final JsonDeserializationContext context) throws JsonParseException {
        if (json.isJsonPrimitive() && json.getAsJsonPrimitive().isString()) {
            String value = json.getAsString();
            return ChatType.fromApiValue(value);
        }
        return null;
    }
}
