package ru.mail.im.botapi.fetcher.event;

import com.google.gson.annotations.SerializedName;

public abstract class Event<T> {

    @SerializedName("eventId")
    long eventId;

    @SerializedName("payload")
    T eventData;

    @SerializedName("type")
    private String type;

    public abstract <IN, OUT> OUT accept(EventVisitor<IN, OUT> visitor, IN in);

    public <R> R withData(NonNullCall<R, T> call) {
        if (eventData != null) {
            return call.call(eventData);
        }
        return null;
    }

    public interface NonNullCall<R, D> {
        R call(D data);
    }

    public long getEventId() {
        return eventId;
    }

    public String getType() {
        return type;
    }
}