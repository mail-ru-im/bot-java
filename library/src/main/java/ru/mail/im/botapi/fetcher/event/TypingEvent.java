package ru.mail.im.botapi.fetcher.event;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

public class TypingEvent extends Event<TypingEvent.Data> {

    @Override
    public <IN, OUT> OUT accept(final EventVisitor<IN, OUT> visitor, final IN in) {
        return visitor.visitTyping(this, in);
    }

    @Nullable
    public String getTypistId() {
        return withData(data -> data.aimId);
    }

    @Nullable
    public String getStatus() {
        return withData(data -> data.status);
    }

    class Data {
        @SerializedName("aimId")
        private String aimId;

        // TODO replace with Enum if the field will remains in new fetch protocol
        @SerializedName("typingStatus")
        private String status;
    }
}
