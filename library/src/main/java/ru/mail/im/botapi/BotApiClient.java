package ru.mail.im.botapi;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import ru.mail.im.botapi.api.Api;
import ru.mail.im.botapi.api.BotApi;
import ru.mail.im.botapi.api.Chats;
import ru.mail.im.botapi.api.Messages;
import ru.mail.im.botapi.api.Self;
import ru.mail.im.botapi.fetcher.BackoffFetcher;
import ru.mail.im.botapi.fetcher.Fetcher;
import ru.mail.im.botapi.fetcher.OnEventFetchListener;
import ru.mail.im.botapi.util.ListenerDescriptor;
import ru.mail.im.botapi.util.ListenerList;

import javax.annotation.Nonnull;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class BotApiClient {

    private static final String ICQ_BOT_API_URL = "https://api.icq.net";

    private final long lastEventId;
    private final long pollTime;

    private final AtomicBoolean started = new AtomicBoolean();
    private final ListenerList<OnEventFetchListener> fetchListenerList;

    private final Api api;
    private Fetcher fetcher;

    public BotApiClient(@Nonnull final String token) {
        this(ICQ_BOT_API_URL, token);
    }

    public BotApiClient(
        final String apiUrl,
        final String token
    ) {
        this(apiUrl, token, 0, 60);
    }

    public BotApiClient(
        final String token,
        final long lastEventId,
        final long pollTime
    ) {
        this(ICQ_BOT_API_URL, token, lastEventId, pollTime);
    }

    public BotApiClient(
        final String apiBaseUrl,
        final String token,
        final long lastEventId,
        final long pollTime
    ) {
        this(new Gson(), apiBaseUrl, token, lastEventId, pollTime);
    }

    public BotApiClient(
        final Gson gson,
        final String apiBaseUrl,
        final String token,
        final long lastEventId,
        final long pollTime
    ) {
        this(
            gson,
            new OkHttpClient.Builder().readTimeout(pollTime, TimeUnit.SECONDS).build(),
            new ListenerList<>(OnEventFetchListener.class),
            apiBaseUrl,
            token,
            lastEventId,
            pollTime
        );
    }

    public BotApiClient(
        final Gson gson,
        final OkHttpClient httpClient,
        final ListenerList<OnEventFetchListener> fetchListenerList,
        final String apiBaseUrl,
        final String token,
        final long lastEventId,
        final long pollTime
    ) {
        this(
            new BotApi(gson, httpClient, apiBaseUrl + "/bot/v1/", token),
            new BackoffFetcher(
                httpClient,
                events -> fetchListenerList.asListener().onEventFetch(events),
                apiBaseUrl + "/bot/v1/events/get?token=" + token
            ),
            fetchListenerList,
            lastEventId,
            pollTime
        );
    }

    public BotApiClient(
        final Api api,
        final Fetcher fetcher,
        final ListenerList<OnEventFetchListener> fetchListenerList,
        final long lastEventId,
        final long pollTime
    ) {
        this.api = api;
        this.fetcher = fetcher;
        this.fetchListenerList = fetchListenerList;
        this.lastEventId = lastEventId;
        this.pollTime = pollTime;
    }

    public void start() {
        if (started.compareAndSet(false, true)) {
            startInternal();
            BotLogger.i("bot successfully started");
        } else {
            BotLogger.i("bot already started");
        }
    }

    public void stop() {
        if (started.compareAndSet(true, false)) {
            stopInternal();
            BotLogger.i("bot successfully stopped");
        } else {
            BotLogger.i("bot is not running");
        }
    }

    public ListenerDescriptor addOnEventFetchListener(final OnEventFetchListener listener) {
        return fetchListenerList.add(listener);
    }

    public Messages messages() {
        return api.messages();
    }

    public Self self() {
        return api.self();
    }

    public Chats chats() {
        return api.chats();
    }

    private void startInternal() {
        fetcher.start(lastEventId, pollTime);
    }

    private void stopInternal() {
        fetcher.stop();
    }

}
