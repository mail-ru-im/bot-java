package ru.mail.im.botapi.fetcher;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import ru.mail.im.botapi.fetcher.event.Event;
import ru.mail.im.botapi.fetcher.event.ImEvent;
import ru.mail.im.botapi.fetcher.event.MyInfoEvent;
import ru.mail.im.botapi.fetcher.event.TypingEvent;
import ru.mail.im.botapi.fetcher.event.UnsupportedEvent;

import java.lang.reflect.Type;

class EventDeserializer implements JsonDeserializer<Event> {

    @Override
    public Event deserialize(final JsonElement json,
                             final Type typeOfT,
                             final JsonDeserializationContext context) throws JsonParseException {
        final String type = extractEventType(json);
        if (type == null) {
            return new UnsupportedEvent("");
        }
        switch (type) {
            case "im":
                return context.deserialize(json, ImEvent.class);
            case "myInfo":
                return context.deserialize(json, MyInfoEvent.class);
            case "typing":
                return context.deserialize(json, TypingEvent.class);
            default:
                return new UnsupportedEvent(type);
        }
    }

    private static String extractEventType(final JsonElement json) {
        if (!json.isJsonObject()) {
            return null;
        }
        JsonObject object = json.getAsJsonObject();
        if (!object.has("type") || !object.get("type").isJsonPrimitive()) {
            return null;
        }
        JsonPrimitive jsonType = object.getAsJsonPrimitive("type");
        if (!jsonType.isString()) {
            return null;
        }
        return jsonType.getAsString();
    }
}
