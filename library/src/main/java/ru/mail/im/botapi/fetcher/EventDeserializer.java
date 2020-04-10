package ru.mail.im.botapi.fetcher;

import com.google.gson.*;
import ru.mail.im.botapi.fetcher.event.*;

import javax.annotation.Nonnull;
import java.lang.reflect.Type;

class EventDeserializer implements JsonDeserializer<Event> {

    @Override
    public Event deserialize(final JsonElement json,
                             final Type typeOfT,
                             final JsonDeserializationContext context) throws JsonParseException {
        switch (extractEventType(json)) {
            case "newMessage":
                return context.deserialize(json, NewMessageEvent.class);
            case "editedMessage":
                return context.deserialize(json, EditedMessageEvent.class);
            case "deletedMessage":
                return context.deserialize(json, DeletedMessageEvent.class);
            case "pinnedMessage":
                return context.deserialize(json, PinnedMessageEvent.class);
            case "unpinnedMessage":
                return context.deserialize(json, UnpinnedMessageEvent.class);
            case "newChatMembers":
                return context.deserialize(json, NewChatMembersEvent.class);
            case "leftChatMembers":
                return context.deserialize(json, LeftChatMembersEvent.class);
            case "callbackQuery":
                return context.deserialize(json, CallbackQueryEvent.class);
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
