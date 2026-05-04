package ru.mail.im.botapi.fetcher;

import com.google.gson.annotations.SerializedName;
import ru.mail.im.botapi.fetcher.event.Event;
import ru.mail.im.botapi.response.ApiResponse;

import java.util.List;

public class FetchResponse extends ApiResponse {

    @SerializedName("events")
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    @Override
    public String toString() {
        return "FetchResponse{" +
                "events=" + events +
                '}';
    }
}
