package ru.mail.im.botapi.fetcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import ru.mail.im.botapi.fetcher.event.Event;
import ru.mail.im.botapi.fetcher.event.ImEvent;
import ru.mail.im.botapi.fetcher.event.UnsupportedEvent;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Fetcher {

    private String nextUrl;

    private OnEventFetchListener listener;

    private Gson gson;

    private final OkHttpClient client;

    private final AtomicBoolean isRunning = new AtomicBoolean();

    public Fetcher(final OkHttpClient client, final OnEventFetchListener listener) {
        this.client = client;
        this.listener = listener;
    }

    public void start(final String startUrl) {
        if (isRunning.compareAndSet(false, true)) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Event.class, new EventDeserializer())
                    .create();
            new Thread(() -> {
                nextUrl = startUrl;
                while (isRunning.get()) {
                    try {
                        fetchNext();
                    } catch (IOException e) {
                        // TODO handle
                    }
                }
            }).start();
        }
    }

    public void stop() {
        isRunning.set(false);
    }

    private void fetchNext() throws IOException {
        final Request httpRequest = new Request.Builder()
                .url(nextUrl)
                .build();
        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            if (!httpResponse.isSuccessful()) {
                throw new IOException("Bad HTTP status " + httpResponse.code());
            }
            final ResponseBody body = httpResponse.body();
            if (body == null) {
                throw new NullPointerException("Response body is null");
            }
            final FetchResponse fetchResponse = gson.fromJson(body.charStream(), FetchResponse.class);
            handleFetchResponse(fetchResponse);
        }
    }

    private void handleFetchResponse(final FetchResponse fetchResponse) {
        handleEvents(fetchResponse.getEvents());

        final String nextUrl = fetchResponse.getNextUrl();
        if (nextUrl != null) {
            this.nextUrl = nextUrl;
        }

        if (fetchResponse.getTimeToNextFetch() > 0) {
            sleep(fetchResponse.getTimeToNextFetch());
        }
    }

    private void handleEvents(final List<Event> events) {
        if (isRunning.get()) {
            listener.onEventFetch(events);
        }
    }

    private void sleep(final long ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            // TODO handle
        }
    }

    private static class EventDeserializer implements JsonDeserializer<Event> {

        @Override
        public Event deserialize(final JsonElement json,
                                 final Type typeOfT,
                                 final JsonDeserializationContext context) throws JsonParseException {
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

            switch (jsonType.getAsString()) {
                case "im":
                    return context.deserialize(json, ImEvent.class);
                default:
                    return new UnsupportedEvent(jsonType.getAsString());
            }
        }
    }
}
