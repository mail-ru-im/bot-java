package ru.mail.im.botapi.fetcher;

import com.google.gson.annotations.SerializedName;
import ru.mail.im.botapi.fetcher.event.Event;

import java.util.List;

class FetchResponse {

    @SerializedName("events")
    private List<Event> events;

    @SerializedName("ok")
    private boolean ok;

    public List<Event> getEvents() {
        return events;
    }

    public boolean isOk() {
        return ok;
    }
}
