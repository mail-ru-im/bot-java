package ru.mail.im.botapi.response;

import com.google.gson.annotations.SerializedName;

public class MessageResponse extends ApiResponse {

    @SerializedName("msgId")
    private long msgId;

    @SerializedName("fileId")
    private String fileId;

    public long getMsgId() {
        return msgId;
    }

    public String getFileId() {
        return fileId;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "msgId=" + msgId + '\'' +
                ", fileId=" + fileId +
                '}';
    }
}
