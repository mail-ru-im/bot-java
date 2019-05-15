package ru.mail.im.botapi.response;

import com.google.gson.annotations.SerializedName;

public class MessageResponse extends ApiResponse {

    @SerializedName("msgId")
    private long msgId;

    public long getMsgId() {
        return msgId;
    }
}
