package ru.mail.im.botapi.fetcher;

import ru.mail.im.botapi.fetcher.event.Event;

import java.util.List;

public interface OnEventFetchListener {
    void onEventFetch(final List<Event> events);
}
