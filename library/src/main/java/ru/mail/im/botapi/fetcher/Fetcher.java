package ru.mail.im.botapi.fetcher;

public interface Fetcher {
    void start(final long lastEventId, final long pollTime);
    void stop();
}
