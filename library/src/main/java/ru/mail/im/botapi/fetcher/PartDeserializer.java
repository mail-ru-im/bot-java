package ru.mail.im.botapi.fetcher;

import com.google.gson.*;
import ru.mail.im.botapi.fetcher.event.parts.*;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;

public class PartDeserializer implements JsonDeserializer<Part> {
    @Override
    public Part deserialize(JsonElement json,
                            Type typeOfT,
                            JsonDeserializationContext context) throws JsonParseException {
        switch (extractPartType(json)) {
            case "sticker":
                return context.deserialize(extractPayload(json), Sticker.class);
            case "mention":
                return context.deserialize(extractPayload(json), Mention.class);
            case "voice":
                return context.deserialize(extractPayload(json), Voice.class);
            case "file":
                return context.deserialize(extractPayload(json), File.class);
            case "forward":
                return context.deserialize(extractPayload(json), Forward.class);
            case "reply":
                return context.deserialize(extractPayload(json), Reply.class);
        }
        return null;
    }

    @Nonnull
    private static String extractPartType(final JsonElement json) {
        if (!json.isJsonObject()) {
            return "";
        }
        JsonObject object = json.getAsJsonObject();
        if (!object.has("type") || !object.get("type").isJsonPrimitive()) {
            return "";
        }
        JsonPrimitive jsonType = object.getAsJsonPrimitive("type");
        if (!jsonType.isString()) {
            return "";
        }
        final String type = jsonType.getAsString();
        return type == null ? "" : type;
    }

    private static JsonElement extractPayload(final JsonElement json) {
        if (!json.isJsonObject()) {
            return null;
        }
        JsonObject object = json.getAsJsonObject();
        if (!object.has("payload")) {
            return null;
        }
        return object.getAsJsonObject("payload");
    }
}
