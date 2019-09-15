package ru.mail.im.botapi.fetcher.event;

import ru.mail.im.botapi.fetcher.Chat;
import ru.mail.im.botapi.fetcher.User;

public class PinnedMessageEvent extends Event<PinnedMessageEvent.Data> {
    @Override
    public <IN, OUT> OUT accept(EventVisitor<IN, OUT> visitor, IN in) {
        return visitor.visitPinnedMessage(this, in);
    }

    public long getMsgId() {
        return eventData.msgId;
    }

    public long getTimestamp() {
        return eventData.timestamp;
    }

    public String getText() {
        return eventData.text;
    }

    public Chat getChat() {
        return eventData.chat;
    }

    public User getFrom() {
        return eventData.from;
    }

    static class Data {
        private long msgId;
        private long timestamp;
        private String text;
        private Chat chat;
        private User from;
    }
}
