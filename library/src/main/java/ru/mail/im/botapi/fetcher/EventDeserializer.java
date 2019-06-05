package ru.mail.im.botapi.fetcher;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import ru.mail.im.botapi.fetcher.event.BuddyListEvent;
import ru.mail.im.botapi.fetcher.event.Event;
import ru.mail.im.botapi.fetcher.event.ImEvent;
import ru.mail.im.botapi.fetcher.event.MyInfoEvent;
import ru.mail.im.botapi.fetcher.event.ServiceEvent;
import ru.mail.im.botapi.fetcher.event.TypingEvent;
import ru.mail.im.botapi.fetcher.event.UnknownEvent;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;

class EventDeserializer implements JsonDeserializer<Event> {

    @Override
    public Event deserialize(final JsonElement json,
                             final Type typeOfT,
                             final JsonDeserializationContext context) throws JsonParseException {
        switch (extractEventType(json)) {
            case "im":
                return context.deserialize(json, ImEvent.class);
            case "myInfo":
                return context.deserialize(json, MyInfoEvent.class);
            case "typing":
                return context.deserialize(json, TypingEvent.class);
            case "buddylist":
                return context.deserialize(json, BuddyListEvent.class);
            case "service":
                return context.deserialize(json, ServiceEvent.class);
            default:
                return new UnknownEvent(json.toString());
        }
    }

    @Nonnull
    private static String extractEventType(final JsonElement json) {
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
}
