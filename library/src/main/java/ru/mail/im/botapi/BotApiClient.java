package ru.mail.im.botapi;

import okhttp3.OkHttpClient;
import ru.mail.im.botapi.fetcher.Fetcher;
import ru.mail.im.botapi.fetcher.OnEventFetchListener;
import ru.mail.im.botapi.util.ListenerDescriptor;
import ru.mail.im.botapi.util.ListenerList;

import javax.annotation.Nonnull;
import java.util.concurrent.atomic.AtomicBoolean;

public class BotApiClient {

    private final String baseUrl;
    private final String token;
    private final AtomicBoolean started = new AtomicBoolean();
    private final ListenerList<OnEventFetchListener> fetchListenerList = new ListenerList<>(OnEventFetchListener.class);

    private Fetcher fetcher;
    private Api api;

    public BotApiClient(@Nonnull String baseUrl, @Nonnull final String token) {
        this.baseUrl = baseUrl;
        this.token = token;
    }

    public void start() {
        if (started.compareAndSet(false, true)) {
            startInternal();
        }
    }

    public void stop() {
        if (started.compareAndSet(true, false)) {
            stopInternal();
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

        fetcher = new Fetcher(httpClient, events -> fetchListenerList.asListener().onEventFetch(events));
        fetcher.start("https://botapi.icq.net/fetchEvents?aimsid=" + token);
    }

    private void stopInternal() {
        fetcher.stop();
    }

}
