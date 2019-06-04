package ru.mail.im.botapi.fetcher.event;

import com.google.gson.annotations.SerializedName;

public class ImEvent extends Event<ImEvent.Data> {

    static class Data {
        @SerializedName("message")
        private String message;
    }
}
