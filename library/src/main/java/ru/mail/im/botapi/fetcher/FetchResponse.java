package ru.mail.im.botapi.fetcher;

import com.google.gson.annotations.SerializedName;
import ru.mail.im.botapi.fetcher.event.Event;

import java.util.Collections;
import java.util.List;

// TODO: ProGuard
class FetchResponse {

    @SerializedName("response")
    private Response response;

    String getNextUrl() {
        if (response == null) {
            return null;
        }
        if (response.data == null) {
            return null;
        }
        return response.data.nextUrl;
    }

    long getTimeToNextFetch() {
        if (response == null) {
            return -1;
        }
        if (response.data == null) {
            return -1;
        }
        return response.data.timeToNextFetch;
    }

    List<Event> getEvents() {
        if (response == null) {
            return Collections.emptyList();
        }
        if (response.data == null) {
            return Collections.emptyList();
        }
        return response.data.events;
    }

    private static class Response {

        @SerializedName("statusCode")
        private int statusCode;

        @SerializedName("statusText")
        private String statusText;

        @SerializedName("data")
        private Data data;
    }

    private static class Data {

        @SerializedName("pollTime")
        private long pollTime;

        @SerializedName("ts")
        private long timestamp;

        @SerializedName("fetchBaseURL")
        private String nextUrl;

        @SerializedName("timeToNextFetch")
        private long timeToNextFetch;

        @SerializedName("events")
        private List<Event> events = Collections.emptyList();
    }
}
