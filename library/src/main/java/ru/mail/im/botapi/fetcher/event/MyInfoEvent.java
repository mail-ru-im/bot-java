package ru.mail.im.botapi.fetcher.event;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

public class MyInfoEvent extends Event<MyInfoEvent.Data> {

    @Override
    public <IN, OUT> OUT accept(final EventVisitor<IN, OUT> visitor, final IN in) {
        return visitor.visitMyInfo(this, in);
    }

    @Nullable
    public String getId() {
        return withData(data -> data.aimId);
    }

    @Nullable
    public String getName() {
        return withData(data -> data.friendly);
    }

    @Nullable
    public String getNick() {
        return withData(data -> data.nick);
    }

    class Data {
        @SerializedName("aimId")
        private String aimId;

        @SerializedName("friendly")
        private String friendly;

        @SerializedName("nick")
        private String nick;
    }
}
