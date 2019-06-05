package ru.mail.im.botapi.fetcher.event;

import com.google.gson.annotations.SerializedName;

import javax.annotation.Nullable;

public class ImEvent extends Event<ImEvent.Data> {

    @Override
    public <IN, OUT> OUT accept(final EventVisitor<IN, OUT> visitor, final IN in) {
        return visitor.visitIm(this, in);
    }

    @Nullable
    public String getSenderId() {
        return withSource(source -> source.aimId);
    }

    @Nullable
    public String getSenderName() {
        return withSource(source -> source.friendly);
    }

    @Nullable
    public String getText() {
        return withData(data -> data.message);
    }

    private <R> R withSource(NonNullCall<R, Source> call) {
        return withData(data -> data.source != null ? call.call(data.source) : null);
    }

    static class Data {
        @SerializedName("message")
        private String message;

        @SerializedName("source")
        private Source source;
    }

    private static class Source {
        @SerializedName("aimId")
        private String aimId;

        @SerializedName("friendly")
        private String friendly;

        @SerializedName("buddyIcon")
        private String avatarUrl;
    }
}
