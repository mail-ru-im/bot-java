package ru.mail.im.botapi.sample;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import ru.mail.im.botapi.entity.ChatType;
import ru.mail.im.botapi.response.ApiResponse;

import java.lang.reflect.Type;

public class ResponsePrinter implements OnRequestExecuteListener {

    private final Gson gson = new GsonBuilder()
            .disableHtmlEscaping()
            .registerTypeAdapter(ChatType.class, new ChatTypeSerializer())
            .create();

    @Override
    public void onRequestExecute(final ApiResponse response) {
        System.out.println(gson.toJson(response));
    }

    private class ChatTypeSerializer implements JsonSerializer<ChatType> {
        @Override
        public JsonElement serialize(final ChatType src, final Type typeOfSrc, final JsonSerializationContext context) {
            return new JsonPrimitive(src.getApiValue());
        }
    }
}
