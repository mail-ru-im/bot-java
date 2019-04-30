package ru.mail.im.botapi.response;

import com.google.gson.annotations.SerializedName;

public class MessageResponse extends ApiResponse {

    @SerializedName("msgId")
    private String msgId;

    public String getMsgId() {
        return msgId;
    }
}
