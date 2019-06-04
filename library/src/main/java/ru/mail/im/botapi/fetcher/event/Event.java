package ru.mail.im.botapi.fetcher.event;

import com.google.gson.annotations.SerializedName;

public abstract class Event<T> {

    @SerializedName("eventData")
    protected T eventData;
}