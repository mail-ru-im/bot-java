package ru.mail.im.botapi;

import okhttp3.OkHttpClient;
import ru.mail.im.botapi.api.Api;
import ru.mail.im.botapi.api.Chats;
import ru.mail.im.botapi.api.Messages;
import ru.mail.im.botapi.api.Self;
import ru.mail.im.botapi.fetcher.Fetcher;
import ru.mail.im.botapi.fetcher.OnEventFetchListener;
import ru.mail.im.botapi.util.ListenerDescriptor;
import ru.mail.im.botapi.util.ListenerList;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicBoolean;

public class BotApiClient {

    private static final String BOT_API_URL = "https://u.icq.net/rapi/botapi";

    private final String baseUrl;
    private final String token;
    private final long lastEventId;
    private final long pollTime;

    private final AtomicBoolean started = new AtomicBoolean();
    private final ListenerList<OnEventFetchListener> fetchListenerList = new ListenerList<>(OnEventFetchListener.class);

    private Fetcher fetcher;
    private Api api;

    public BotApiClient(@Nonnull String token) {
        this(BOT_API_URL, token, 0, 60);
    }

    public BotApiClient(@Nonnull String token, long lastEventId, long pollTime) {
        this(BOT_API_URL, token, lastEventId, pollTime);
    }

    public BotApiClient(@Nonnull String baseUrl, @Nonnull final String token,
                        long lastEventId, long pollTime) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.lastEventId = lastEventId;
        this.pollTime = pollTime;
    }

    public void start() {
        if (started.compareAndSet(false, true)) {
            startInternal();
            BotLogger.i("bot successfully started token:" + token);
        } else {
            BotLogger.i("bot already started token:" + token);
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
        final OkHttpClient httpClient = new OkHttpClient();
        api = new Api(httpClient, baseUrl, token);

        fetcher = new Fetcher(httpClient,
                events -> fetchListenerList.asListener().onEventFetch(events),
                "https://api.icq.net/bot/v1/events/get?token=" + token
        );
        fetcher.start(lastEventId, pollTime);
    }

    private void stopInternal() {
        fetcher.stop();
    }

}
