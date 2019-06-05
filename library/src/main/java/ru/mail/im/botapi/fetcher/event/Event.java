package ru.mail.im.botapi.fetcher.event;

import com.google.gson.annotations.SerializedName;

public abstract class Event<T> {

    @SerializedName("eventData")
    T eventData;

    @SerializedName("seqNum")
    private long seqNum;

    public abstract <IN, OUT> OUT accept(EventVisitor<IN, OUT> visitor, IN in);

    public long getSeqNum() {
        return seqNum;
    }

    <R> R withData(NonNullCall<R, T> call) {
        if (eventData != null) {
            return call.call(eventData);
        }
        return null;
    }

    interface NonNullCall<R, D> {
        R call(D data);
    }
}