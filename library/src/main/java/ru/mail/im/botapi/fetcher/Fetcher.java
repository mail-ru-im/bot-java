package ru.mail.im.botapi.fetcher;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import ru.mail.im.botapi.BotLogger;
import ru.mail.im.botapi.fetcher.event.Event;
import ru.mail.im.botapi.fetcher.event.parts.Part;
import ru.mail.im.botapi.util.IOBackoff;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Fetcher {

    private String baseUrl;

    private String nextUrl;

    private long pollTime;

    private long lastEventId;

    private OnEventFetchListener listener;

    private Gson gson;

    private final OkHttpClient client;

    private final AtomicBoolean isRunning = new AtomicBoolean();

    private final IOBackoff backoff = IOBackoff.newBuilder()
            .startTime(100)
            .maxTime(10_000)
            .factor(2)
            .build();

    private final ExecutorService executor;

    public Fetcher(final OkHttpClient client,
                   final OnEventFetchListener listener,
                   final String baseUrl) {
        this(client, listener, baseUrl, Executors.newSingleThreadExecutor());
    }

    public Fetcher(final OkHttpClient client,
                   final OnEventFetchListener listener,
                   final String baseUrl,
                   final ExecutorService executor) {
        this.client = client;
        this.listener = listener;
        this.baseUrl = baseUrl;
        this.executor = executor;
    }

    public void start(final long lastEventId, final long pollTime) {
        this.lastEventId = lastEventId;
        this.pollTime = pollTime;

        if (isRunning.compareAndSet(false, true)) {
            gson = new GsonBuilder()
                    .registerTypeAdapter(Event.class, new EventDeserializer())
                    .registerTypeAdapter(Part.class, new PartDeserializer())
                    .create();
            executor.execute(() -> {
                nextUrl = getNextUrl();
                while (isRunning.get()) {
                    backoff.execute(this::fetchNext);
                }
            });
        }
    }

    private String getNextUrl() {
        return String.format("%s&lastEventId=%d&pollTime=%d", baseUrl, lastEventId, pollTime);
    }

    public void stop() {
        isRunning.set(false);
        executor.shutdown();
        BotLogger.i("fetcher stopped");
    }

    private void fetchNext() throws IOException {
        final Request httpRequest = new Request.Builder()
                .url(nextUrl)
                .build();
        BotLogger.i("fetch next url:" + nextUrl);
        try (Response httpResponse = client.newCall(httpRequest).execute()) {
            if (!httpResponse.isSuccessful()) {
                throw new IOException("Bad HTTP status " + httpResponse.code());
            }
            final ResponseBody body = httpResponse.body();
            if (body == null) {
                throw new NullPointerException("Response body is null");
            }
            String json = body.string();
            final FetchResponse fetchResponse = gson.fromJson(json, FetchResponse.class);
            handleFetchResponse(fetchResponse);
            BotLogger.i("new events fetched:" + json);
        }
    }

    private void handleFetchResponse(final FetchResponse fetchResponse) {
        handleEvents(fetchResponse.getEvents());

        if (fetchResponse.getEvents() != null && !fetchResponse.getEvents().isEmpty()) {
            int size = fetchResponse.getEvents().size();
            lastEventId = fetchResponse.getEvents().get(size - 1).getEventId();
        }

        this.nextUrl = getNextUrl();
    }

    private void handleEvents(final List<Event> events) {
        if (isRunning.get()) {
            listener.onEventFetch(events);
        }
    }
}
